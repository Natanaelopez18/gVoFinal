package com.example.elvoluntariado.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elvoluntariado.Api.Api;
import com.example.elvoluntariado.MainActivity;
import com.example.elvoluntariado.R;
import com.example.elvoluntariado.models.AccessToken;
import com.example.elvoluntariado.models.Usuario;
import com.tumblr.remember.Remember;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signUp, back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initViews();
    }

    private void initViews() {
        email =  findViewById(R.id.email);
        password =  findViewById(R.id.contraseña);
        signUp =  findViewById(R.id.btnRegistrar);
        back = findViewById(R.id.btnCancel);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loginRequest(String email, String password) {
        // instance a user
        final Usuario user = new Usuario();
        user.setEmail(email);
        user.setPassword(password);

        // create call
        Call<AccessToken> call = Api.instance().login(user);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, final Response<AccessToken> response) {
                if (response.body() != null) {

                    Log.i("access_token", response.body().getId());
                    Remember.putString("access_token", response.body().getId(), new Remember.Callback() {
                        @Override
                        public void apply(Boolean success) {
                            if (success) {
                                ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                                progressDialog.setTitle("Cargando");
                                progressDialog.setMessage("verificando...");
                                progressDialog.show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Remember.init(getApplicationContext(),"email");
                                Remember.putString("email", user.getEmail());
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else {
                    if (response.body() == null)
                    {
                        Toast.makeText(getApplicationContext(), "Correo y/o contraseña ya registrados",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {


            }
        });
    }

    private void signUp() {

       if(password.getText().toString().isEmpty()) {
            // Toast.makeText(getApplicationContext(), "Digite una contraseña", Toast.LENGTH_LONG).show();
        }
        else  if(email.getText().toString().isEmpty()) {
            // Toast.makeText(getApplicationContext(), "Digite una contraseña", Toast.LENGTH_LONG).show();
        } {
            // instance a user
            Usuario user = new Usuario ();
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());

            // create call

            Call<Usuario> call = Api.instance().signUp(user);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.body() != null) {
                        loginRequest(email.getText().toString(), password.getText().toString());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        }
    }

}
