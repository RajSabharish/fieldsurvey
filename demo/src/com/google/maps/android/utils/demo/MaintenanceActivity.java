package com.google.maps.android.utils.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

public class MaintenanceActivity extends BaseDemoActivity {
private static boolean upkeepclickstate,remediationclickstate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        upkeepclickstate =extras.getBoolean("upkeepclickstate");
        remediationclickstate =extras.getBoolean("remediationclickstate");

    }

    @Override
    protected void startDemo() {
        loadmap();
    }

    private void loadmap(){
        LatLng initial_coordinate = new LatLng(-37.74499146, 144.79864884);
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,15);
        getMap().animateCamera(initial_location);
    }
}
