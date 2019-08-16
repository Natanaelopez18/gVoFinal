package com.example.elvoluntariado.AdapterTour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elvoluntariado.R;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }

    //Arrays

    public int [] slide_images = {
            R.drawable.servicio,
            R.drawable.user_icon,
            R.drawable.ubicacion
    };

    public String [] slide_heading = {
      "El Voluntario",
      "Participa",
      "Ubicación"
    };

    public String [] slide_desc = {
            "Aplicación de Fundaciones y Instituciones donde ofrecen voluntariado",
            "En esta Aplicación te presentamos una lista de Fundaciones e Instituciones las cuales brindan voluntariado escoje una y UNETE ",
            "Te Brindamos un acceso a un mapa para enseñarte la ubicación de cada una"
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==  o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.imageView5);
        TextView textView =  view.findViewById(R.id.textView2);
        TextView textView1 =  view.findViewById(R.id.textView3);

        slideImageView.setImageResource(slide_images[position]);
        textView.setText(slide_heading[position]);
        textView1.setText(slide_desc[position]);
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
