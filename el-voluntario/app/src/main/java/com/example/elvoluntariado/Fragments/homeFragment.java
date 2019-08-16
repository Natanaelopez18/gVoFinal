package com.example.elvoluntariado.Fragments;


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
import com.example.elvoluntariado.Api.Api;
import com.example.elvoluntariado.R;
import com.example.elvoluntariado.models.foundation;
import com.tumblr.remember.Remember;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {

    RecyclerView recyclerView;
    private int id;

    Realm realm;
    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LlenarList();
    }


    private void LlenarList() {
        Call<List<foundation>> listCall = Api.instance().getFoundations(Remember.getString("access_token", ""));
        listCall.enqueue(new Callback<List<foundation>>() {
            @Override
            public void onResponse(Call<List<foundation>> call, Response<List<foundation>> response) {
                if (response.isSuccessful()) {
                    cardViewsAdapter adapter = new cardViewsAdapter(getContext(), response.body());
                    
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "An error occur while getting info ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<foundation>> call, Throwable t) {
                Log.e("Error", "Error to connect to the API", t);
            }
        });




    }

}
