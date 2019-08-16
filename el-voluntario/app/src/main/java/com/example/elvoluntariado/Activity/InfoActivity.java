package com.example.elvoluntariado.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elvoluntariado.R;

public class InfoActivity extends AppCompatActivity {

    private ImageView img;
    private TextView name;
    private TextView address;
    private TextView number;
    private TextView person;
    //private Button map, btnLlamar;
    private ImageButton map, btnLlamar;
    int id;
    String namefound;
    String addressF;
    String numberF;
    String personF;
    String numero;
    private double lat;
    private double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        reciveInfo();
    }
    private void reciveInfo() {
        img = findViewById(R.id.img_info);
        name = findViewById(R.id.txt_name_foundation_ia);
        address = findViewById(R.id.txt_address_ia);
        number = findViewById(R.id.txt_phone_ia);
        person = findViewById(R.id.txt_person_ia);
        map = findViewById(R.id.btn_go_map_ia);
        btnLlamar= findViewById(R.id.btnllamar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(getApplicationContext()).load(bundle.getString("image")).into(img);
            namefound = bundle.getString("name");
            addressF = bundle.getString("address");
            numberF = bundle.getString("phone");
            personF = bundle.getString("person");
            id = bundle.getInt("id");
            lat = bundle.getDouble("latitud");
            lng = bundle.getDouble("longitud");
            name.setText(namefound);
            address.setText(addressF);
            number.setText(numberF);
            numero = number.getText().toString();
            person.setText(personF);
        }
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("name",namefound);
                intent.putExtra("latitud",lat);
                intent.putExtra("longitud",lng);
                startActivity(intent);
            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNo = numberF;
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(InfoActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
