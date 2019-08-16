package com.example.elvoluntariado.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.elvoluntariado.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private String namefound;
    private double lat;
    private double lng;


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), "pk.eyJ1IjoibmF0YW5hZW8iLCJhIjoiY2p5bDN0cm83MDRlZzNpbXJ3ZXowMmI3ZyJ9.Fv4idI9JybtM548IrAGG8g");
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        getLat();
        getLng();
        getName();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                    }
                });
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,lng))
                        .title(namefound));

                CameraPosition position = new CameraPosition.Builder()
                        .zoom(17)
                        .target(new LatLng(lat, lng))
                        .tilt(30)
                        .bearing(180)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);

            }
        });
    }

    private String getName() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            namefound = bundle.getString("name");
        }
        return namefound;
    }

    private double getLat() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            lat = bundle.getDouble("latitud");
        }
        return lat;
    }
    private double getLng() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            lng = bundle.getDouble("longitud");
        }
        return lng;
    }
}
