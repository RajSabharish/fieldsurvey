package com.google.maps.android.utils.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

public class ConstructionActivity extends BaseDemoActivity {

    protected int getLayoutId() {
        return R.layout.activity_construction;
    }


    @Override
    protected void startDemo() {
        loadmap();
    }

    public void loadmap(){
        LatLng initial_coordinate = new LatLng(-37.74499146, 144.79864884);
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,15);
        getMap().animateCamera(initial_location);
    }
}
