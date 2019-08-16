package com.example.elvoluntariado.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elvoluntariado.Api.Api;
import com.example.elvoluntariado.MainActivity;
import com.example.elvoluntariado.R;
import com.example.elvoluntariado.models.Location;
import com.example.elvoluntariado.models.foundation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tumblr.remember.Remember;

import java.io.File;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFoundationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView image;
    private EditText nombre,direc,tel,encargado;
    private Button send,cancel;
    private ImageButton places;
    private StorageReference storageReference;
    private final String folder_Root="DevH&R/";
    private final String Route_image=folder_Root+"myPhotos";
    final int Cod_Select = 10;
    final int Cod_Photo = 20;
    String path;
    Uri photoLink;
    GoogleApiClient googleApiClient;
    foundation fundacion;
    private double l,n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_foundation);
        init();
    }
    private void init() {
        nombre = findViewById(R.id.et_name_si);
        direc = findViewById(R.id.et_address_si);
        tel = findViewById(R.id.et_phone_si);
        encargado = findViewById(R.id.et_person_si);
        image = findViewById(R.id.pictures);

        send = findViewById(R.id.btn_Send_si);
        cancel = findViewById(R.id.btn_Cancel_si);
        storageReference = FirebaseStorage.getInstance().getReference();
        places = findViewById(R.id.places);

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,  this)
                .build();

        fundacion = new foundation();

        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    displayPlacePicker();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void displayPlacePicker() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(CreateFoundationActivity.this), PLACE_PICKER_REQUEST);
    }

    private void sendInfo() {

        foundation i = new foundation();
        i.setName(nombre.getText().toString());
        i.setAddress(direc.getText().toString());
        i.setPhoneNumber(tel.getText().toString());
        i.setPerson(encargado.getText().toString());
        i.setLat(l);
        i.setLng(n);
        i.setUrl(photoLink.toString());

        Call<foundation> sendInfo = Api.instance().SendInfo(i, Remember.getString("access_token", ""));
        sendInfo.enqueue(new Callback<foundation>() {
            @Override
            public void onResponse(Call<foundation> call, Response<foundation> response) {
                if (response.body() != null){

                    final Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"An error occur while send info ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<foundation> call, Throwable t) {
                Log.e("Error", "Error to connect to the API", t);
            }
        });
    }

    public void message(View view) {
        final CharSequence[] options = {"Take Photo", "Upload Photo", "Cancel"};
        final AlertDialog.Builder alertOptions = new AlertDialog.Builder(CreateFoundationActivity.this);

        alertOptions.setTitle("Select an Option");

        alertOptions.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialogInterface, int i) {

                if (options[i].equals("Take Photo")) {

                    takePhoto();

                } else {

                    if (options[i].equals("Upload Photo")) {

                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        intent.setType("image/*");

                        startActivityForResult(intent.createChooser(intent, "Select an app"), Cod_Select);

                    } else {

                        dialogInterface.dismiss();

                    }
                }
            }

        });

        alertOptions.show();

    }

    private void takePhoto() {
        File fileImage = new File(Environment.getExternalStorageDirectory(), Route_image);
        boolean isCreated = fileImage.exists();
        String nameImage = "";
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (!isCreated) {
            isCreated = fileImage.mkdirs();
        }

        if (isCreated) {
            nameImage = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory() + File.separator + Route_image + File.separator + nameImage;
        File image = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
        startActivityForResult(intent,Cod_Photo);

    }


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case Cod_Select:
                    final Uri uri = data.getData();
                    final StorageReference filePath;

                    assert uri != null;
                    filePath = storageReference.child(Route_image).child(Objects.requireNonNull(uri.getLastPathSegment()));

                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    photoLink = uri;
                                    Glide.with(getApplicationContext())
                                            .load(photoLink)
                                            .into(image);
                                    Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                    });




                case Cod_Photo:

                    MediaScannerConnection.scanFile(this, new String[]{path}, null,

                            new MediaScannerConnection.OnScanCompletedListener() {

                                @Override
                                public void onScanCompleted(String s, Uri uri) {

                                    Log.i("Ruta de almacenamiento","Path: "+path);


                                }

                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    image.setImageBitmap(bitmap);

                    break;
            }

            if (requestCode == 1) {

                Place place = PlacePicker.getPlace(CreateFoundationActivity.this, data);

                //Get the location and set in the object of type location
                try {
                    l = place.getLatLng().latitude;
                    n = place.getLatLng().longitude;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
