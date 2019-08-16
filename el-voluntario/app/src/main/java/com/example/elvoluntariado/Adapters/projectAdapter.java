package com.example.elvoluntariado.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.elvoluntariado.Activity.infoProjectActivity;
import com.example.elvoluntariado.R;
import com.example.elvoluntariado.models.Proyectos;

import java.text.SimpleDateFormat;
import java.util.List;

public class projectAdapter extends RecyclerView.Adapter<projectAdapter.ViewHolder>{

    private Context context;
    private List<Proyectos> proyectos;

    public projectAdapter(Context context, List<Proyectos> proyectos) {
        this.context = context;
        this.proyectos = proyectos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView fname;
        public TextView fdesc;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fname = itemView.findViewById(R.id.txt_project2);
            cardView = itemView.findViewById(R.id.card_view_project);


        }
    }
    @NonNull
    @Override
    public projectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_card_item, viewGroup, false);
        return new projectAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull projectAdapter.ViewHolder viewHolder, final int i) {
        final Proyectos cia = proyectos.get(i);
        viewHolder.fname.setText(cia.getName());
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");



        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, infoProjectActivity.class);
                intent.putExtra("name",proyectos.get(i).getName());
                intent.putExtra("descr",proyectos.get(i).getDescrip());
                intent.putExtra("fechaInicio",String.valueOf(formatter.format(proyectos.get(i).getFechaInicio())));
                intent.putExtra("fechaFin",String.valueOf(formatter.format(proyectos.get(i).getFechaFin())));
                intent.putExtra("cupos",String.valueOf(proyectos.get(i).getCupos()));
               // intent.putExtra("longitud",fundacionesList.get(position).getLng());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return proyectos.size();
    }
}
