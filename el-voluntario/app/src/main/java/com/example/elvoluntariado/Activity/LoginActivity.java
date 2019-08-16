package com.example.elvoluntariado.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class LoginActivity extends AppCompatActivity {
    //Definicion
    Button button;
    Button btnRegistrar;
    Button btnIngresar;
    EditText password;
    EditText username;
    CheckBox recuerdame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Remember.init(getApplicationContext(), "access_token");

        initView();
    }

    private void initView(){



        btnRegistrar = findViewById(R.id.btnRegistrate);
        btnIngresar = findViewById(R.id.loginButton);
        password = findViewById(R.id.etPassword);
        username = findViewById(R.id.etUser);
        recuerdame = findViewById(R.id.recuerdame);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    private void signIn() {


        if (username.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingrese su usuario", Toast.LENGTH_LONG).show();
        } else if(password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingrese una contraseña", Toast.LENGTH_LONG).show();
        } else {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (username.getText().toString().matches(emailPattern))
            {
                loginRequest(username.getText().toString(), password.getText().toString());
                //Toast.makeText(getApplicationContext(),"Correo Valido",Toast.LENGTH_SHORT).show();
            }
            else
            {

                Toast.makeText(getApplicationContext(),"Correo no válido", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void loginRequest(final String email, String password) {
        // instance a user
        final Usuario user = new Usuario();
        user.setEmail(email);
        user.setPassword(password);

        // create call
        Call<AccessToken> call = Api.instance().login(user);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.body() != null) {
                   // Log.i("access_token", response.body().getId ());

                    if(recuerdame.isChecked())
                    {
                        Remember.putString("access_token", response.body().getId(), new Remember.Callback() {
                            @Override
                            public void apply(Boolean success) {
                                if (success){

                                    ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                                    progressDialog.setTitle("Cargando");
                                    progressDialog.setMessage("verificando...");
                                    progressDialog.show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    Remember.putString("email", user.getEmail());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    }
                    else{
                        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setTitle("Cargando");
                        progressDialog.setMessage("verificando...");
                        progressDialog.show();
                        Remember.putString("access_token", response.body().getId());
                        Remember.putString("email", user.getEmail());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }
                else {
                    if (response.body() == null)
                    {
                        Toast.makeText(getApplicationContext(), "Correo y/o contraseña incorrecta",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {



            }
        });
    }


}
