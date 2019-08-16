package com.example.elvoluntariado.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.elvoluntariado.Adapters.cardViewsAdapter;
import com.example.elvoluntariado.Adapters.projectAdapter;
import com.example.elvoluntariado.Api.Api;
import com.example.elvoluntariado.R;
import com.example.elvoluntariado.models.Proyectos;
import com.example.elvoluntariado.models.foundation;
import com.tumblr.remember.Remember;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {
    RecyclerView recyclerView;
    private int id;

    Realm realm;
    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.rv_list2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LlenarLista();
    }


    private void LlenarLista() {
        Call<List<Proyectos>> listCall = Api.instance().getProyectos(Remember.getString("access_token", ""));
        listCall.enqueue(new Callback<List<Proyectos>>() {
            @Override
            public void onResponse(Call<List<Proyectos>> call, Response<List<Proyectos>> response) {
                if (response.isSuccessful()) {
                    projectAdapter adapter = new projectAdapter(getContext(), response.body());

                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "An error occur while getting info ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Proyectos>> call, Throwable t) {
                Log.e("Error", "Error to connect to the API", t);
            }
        });




    }

}
