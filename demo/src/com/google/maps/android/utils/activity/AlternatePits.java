package com.google.maps.android.utils.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.utils.demo.R;

import android.content.Context;
/**
 * Created by varada.vamsi on 19/5/2017.
 */

public class AlternatePits extends BaseDemoActivity {
    private TextView mTextView;
    private final static String mLogTag = "AlterPits";
    public static String pitid, Longitude1, Latitude1, Longitude2, Latitude2;
    public static String coordinates, pitlocation;
    Double nearByLatitude, Originallongitude, Originallatitude, nearByLongitude,finalLong,finalLat;
    JSONArray parentArray;

    @Override
    protected void startDemo() {
        retrieveFileFromResource();
    }

    public void retrieveFileFromResource() {
        ImageButton home_button = (ImageButton) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent_book = new Intent(AlternatePits.this,MainActivity.class);
                startActivity(newIntent_book);
            }
        });


        Bundle scrollingdata = getIntent().getExtras();
        pitid = scrollingdata.getString("pitId");
        pitlocation = scrollingdata.getString("pit_position");
        Context context = null;
        try {
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.pit)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null; ) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONObject parentObject = new JSONObject(tokener);
            parentArray = parentObject.getJSONArray("features");
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject c = parentArray.getJSONObject(i);
                JSONObject properties = c.getJSONObject("properties");
                String ID = properties.getString("ID");
                if (ID.equals(pitid)) {
                    JSONObject geometry = c.getJSONObject("geometry");
                    coordinates = geometry.getString("coordinates");
                    System.out.println("inside the alternate pits" + coordinates + "IDcame" + ID);
                    finddistance(coordinates);
                }

               /* Float value = distFrom(-37.743732,144.79243596,-33.8291,151.248);
                System.out.println(value+"distancefound");*/
            }

        } catch (IOException e) {
            Log.e(mLogTag, "GeoJSON file could not be read");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void finddistance(String currcoordinates) throws JSONException {
        List<String> Coordinateslist = new ArrayList<String>();
        String[] part1 = currcoordinates.split(",");
        String Long1 = part1[0];
        String Lat1 = part1[1];
        Longitude1 = Long1.replaceAll("[\\[\\]]", "");
        Originallongitude = Double.parseDouble(Longitude1);
        Latitude1 = Lat1.replaceAll("[\\[\\]]", "");
        Originallatitude = Double.parseDouble(Latitude1);

        System.out.println(Originallongitude + "Latitude" + Originallatitude);
        for (int i = 0; i < parentArray.length(); i++) {
            JSONObject c = parentArray.getJSONObject(i);
            JSONObject geometry = c.getJSONObject("geometry");
            coordinates = geometry.getString("coordinates");
            Coordinateslist.add(coordinates);
        }

        List<String> NearbyCoordinates = new ArrayList<String>();
        for (int i = 0; i < Coordinateslist.size(); i++) {

            String[] parts = Coordinateslist.get(i).split(",");
            String Long2 = parts[0];
            String Lat2 = parts[1];
            Longitude2 = Long2.replaceAll("[\\[\\]]", "");
            nearByLongitude = Double.parseDouble(Longitude2);
            Latitude2 = Lat2.replaceAll("[\\[\\]]", "");
            nearByLatitude = Double.parseDouble(Latitude2);
            float distance = distFrom(Originallatitude, Originallongitude, nearByLatitude, nearByLongitude);
            if (distance <= 50.0) {
                NearbyCoordinates.add(Coordinateslist.get(i));
            }
        }
        setList((ArrayList<String>) NearbyCoordinates);

    }

    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public void setList(ArrayList<String> NearbyCoordinates) {
        for (int i = 0; i < NearbyCoordinates.size(); i++) {
            String[] parts = NearbyCoordinates.get(i).split(",");
            String Long2 = parts[0];
            String Lat2 = parts[1];
            String longitude=Long2.replaceAll("[\\[\\]]", "");
            String latitude=Lat2.replaceAll("[\\[\\]]", "");
            finalLong =Double.parseDouble( longitude);
            finalLat = Double.parseDouble(latitude);
            drawMap(finalLat,finalLong);
        }
        System.out.println("before calling");
    }

    private void drawMap(Double lat,Double Long) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pit);
        System.out.println("inside the draw map function"+lat+Long);
        LatLng sydney = new LatLng(lat, Long);
        System.out.println("inside the draw map function"+sydney);
        mMap.addMarker(
                new MarkerOptions()
                        .icon(icon)
                        .position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(sydney,19);
        getMap().animateCamera(initial_location);
    }

}

