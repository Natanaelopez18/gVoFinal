package com.example.elvoluntariado.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elvoluntariado.AdapterTour.SlideAdapter;
import com.example.elvoluntariado.R;

public class TourActivity extends AppCompatActivity {

    private ViewPager nSlideView;
    private LinearLayout nLinear;

    private TextView[] nDots;

    private SlideAdapter slideAdapter;

    private Button atras;
    private Button siguiente;

    private int nCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        nSlideView = findViewById(R.id.view1);
        nLinear =  findViewById(R.id.line1);
        atras = findViewById(R.id.button);
        siguiente = findViewById(R.id.button3);

        slideAdapter = new SlideAdapter(this);

        nSlideView.setAdapter(slideAdapter);

        addDoctsIndicador(0);

        nSlideView.addOnPageChangeListener(viewListener);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nSlideView.setCurrentItem(nCurrentPage + 1);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nSlideView.setCurrentItem(nCurrentPage - 1);
            }
        });
    }

    public void addDoctsIndicador(int position) {
        nDots = new TextView[3];

        for (int i = 0; i < nDots.length; i++)
        {
            nDots[i] = new TextView(this);
            nDots[i].setText(Html.fromHtml("&#8226;"));
            nDots[i].setTextSize(3);
            nDots[i].setTextColor(getResources().getColor(R.color.colorTransparentwhite));

            nLinear.addView(nDots[i]);
        }

        if(nDots.length > 0)
        {
            nDots[position].setTextColor(getResources().getColor(R.color.colorWhite1));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDoctsIndicador(i);

            nCurrentPage = i;

            String finalizar = "Finalizar";
            if(i == 0)
            {
                siguiente.setEnabled(true);
                atras.setEnabled(false);
                atras.setVisibility(View.INVISIBLE);

                siguiente.setText("Siguiente");

            }
            else if (i == nDots.length -1){

                siguiente.setEnabled(true);
                atras.setEnabled(true);
                atras.setVisibility(View.VISIBLE);

                siguiente.setText("Finalizar");
                atras.setText("Atras");

            }
            else {
                siguiente.setEnabled(true);
                atras.setEnabled(true);
                atras.setVisibility(View.VISIBLE);

                siguiente.setText("Siguiente");
                atras.setText("Atras");
            }
            if (siguiente.getText().toString().equals(finalizar)){

                siguiente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TourActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


}
