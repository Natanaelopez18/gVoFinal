package com.example.elvoluntariado.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elvoluntariado.Activity.InfoActivity;
import com.example.elvoluntariado.R;
import com.example.elvoluntariado.models.foundation;

import java.util.ArrayList;
import java.util.List;

public class cardViewsAdapter extends RecyclerView.Adapter<cardViewsAdapter.ViewHolder> {

    private Context context;
    private List<foundation> fundacionesList;

    public cardViewsAdapter(Context context, List<foundation> fundacionesList) {
        this.context = context;
        this.fundacionesList = fundacionesList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView fname;
        public ImageButton share;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            fname = itemView.findViewById(R.id.txt_foundation);
            cardView = itemView.findViewById(R.id.card_view_foundation);
            share = itemView.findViewById(R.id.btn_share);

        }
    }
    @NonNull
    @Override
    public cardViewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_card_item, viewGroup, false);
        return new cardViewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final cardViewsAdapter.ViewHolder viewHolder, final int position) {
        final foundation cia = fundacionesList.get(position);
            viewHolder.fname.setText(cia.getName());
            Glide.with(context).load(fundacionesList.get(position).getUrl()).into(viewHolder.img);


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("image",fundacionesList.get(position).getUrl());
                Glide.with(context).load(fundacionesList.get(position).getUrl()).into(viewHolder.img);
                intent.putExtra("name",fundacionesList.get(position).getName());
                intent.putExtra("address",fundacionesList.get(position).getAddress());
                intent.putExtra("phone",fundacionesList.get(position).getPhoneNumber());
                intent.putExtra("person",fundacionesList.get(position).getPerson());
                intent.putExtra("latitud",fundacionesList.get(position).getLat());
                intent.putExtra("longitud",fundacionesList.get(position).getLng());
                context.startActivity(intent);
            }
        });

        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareSubject = "Voluntariado";
                String shareText = "Los invito a participar en el voluntariado que esta realizando: " + fundacionesList.get(position).getName();
                String shareTitle = "Share product via";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                context.startActivity(Intent.createChooser(shareIntent,shareTitle));
            }
        });
    }

    @Override
    public int getItemCount() {
        return fundacionesList.size();
    }
}
