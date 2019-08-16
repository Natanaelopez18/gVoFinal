package com.example.elvoluntariado.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elvoluntariado.R;

import java.text.SimpleDateFormat;

public class infoProjectActivity extends AppCompatActivity {


    private TextView nameProject;
    private TextView desc;
    private TextView fechaInicio;
    private TextView fechaFin;
    private TextView cupos;
    int id;
    String nameP;
    String descP;
    String fechaInicioP;
    String fechaFinP;
    String cuposP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_project);
        reciveInfo();
    }

    private void reciveInfo() {

        nameProject = findViewById(R.id.txt_name_proyecto);
        desc = findViewById(R.id.txt_desc);
        fechaInicio = findViewById(R.id.txt_fechaInicio);
        fechaFin = findViewById(R.id.txt_fechaFin);
        cupos = findViewById(R.id.txt_cupos);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            nameP = bundle.getString("name");
            descP = bundle.getString("descr");
            fechaInicioP = bundle.getString("fechaInicio");
            fechaFinP = bundle.getString("fechaFin");
            cuposP = bundle.getString("cupos");
            id = bundle.getInt("id");
            nameProject.setText(nameP);
            desc.setText(descP);
            fechaInicio.setText(fechaInicioP);
            fechaFin.setText(fechaFinP);
            cupos.setText(cuposP);

        }

    }
}
