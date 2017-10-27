package com.google.maps.android.utils.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.utils.demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/*
This helps in finding out fiber cuts and making the remidaiation person go to that particular location
*/
public class FaultRemediationActivity extends BaseDemoActivity {
    private String textvalue;
    private ImageButton btnSpeak;
    private String Speechvalue;
    TextToSpeech t1;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    JSONArray parentArray;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addColorsToMarkers(GeoJsonLayer layer)
    {
        ImageButton home_button = (ImageButton) findViewById(R.id.home_button);
        btnSpeak = (ImageButton)this.findViewById(R.id.nlpButton);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent_book = new Intent(FaultRemediationActivity.this,MainActivity.class);
                startActivity(newIntent_book);
            }
        });

        for (GeoJsonFeature feature : layer.getFeatures())
        {

            Bitmap pcd_im = BitmapFactory.decodeResource(getResources(), R.drawable.pcd);
            Bitmap inc_im = BitmapFactory.decodeResource(getResources(), R.drawable.incident_note);
            Bitmap odf_im = BitmapFactory.decodeResource(getResources(), R.drawable.fan);
            Bitmap mpt_im = BitmapFactory.decodeResource(getResources(), R.drawable.mpt);
            Bitmap djl_im = BitmapFactory.decodeResource(getResources(), R.drawable.djl);

            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
            if(feature.hasProperty("EQUIPMENT_ID")) {
                if (feature.getProperty("EQUIPMENT_ID").contains("ODF")) {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(odf_im));
                    pointStyle.setTitle(feature.getProperty("EQUIPMENT_ID"));
                    pointStyle.setSnippet(feature.getProperty("TYPE"));
                }
                if (feature.getProperty("EQUIPMENT_ID").contains("MPT")) {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(mpt_im));
                    pointStyle.setTitle(feature.getProperty("EQUIPMENT_ID"));
                    pointStyle.setSnippet(feature.getProperty("TYPE"));

                }
                if (feature.getProperty("EQUIPMENT_ID").contains("3KGP-01-00-DJL-001")) {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(djl_im));
                    pointStyle.setTitle("Values for OTDR");
                    pointStyle.setSnippet("Wavelength : 1310/1550 nm"+"\n"+" Range : 4 km "+"\n"+"Launch cable : 150m"+"\n"+"Receive cable : 150m"+"\n"
                    +"Fiber Type : ");
                }
            }
            else if(feature.hasProperty("ID"))
            {
                if (feature.getProperty("ID").contains("LOC000005005354")||
                        feature.getProperty("ID").contains("LOC000024269703")||
                        feature.getProperty("ID").contains("LOC000025168142")||
                        feature.getProperty("ID").contains("LOC000043519542")){
                    pointStyle.setTitle(feature.getProperty("Customer Premise | Issue "));
                    pointStyle.setSnippet(feature.getProperty("ID"));
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(inc_im));

                }
                else {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(pcd_im));
                    pointStyle.setTitle(feature.getProperty("Customer Premise"));
                    pointStyle.setSnippet(feature.getProperty("ID"));
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(pcd_im));
                }
            }
            feature.setPointStyle(pointStyle);
        }
        getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FaultRemediationActivity.this);
                alertDialog.setTitle("Locate Cable cut");
                alertDialog.setMessage("Enter Length");
                final EditText input = new EditText(FaultRemediationActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                textvalue = input.getText().toString();
                                for (int k = 0; k < ListItems.Coordinateslist.size(); k++) {
                                    String[] parts = ListItems.Coordinateslist.get(k).split(",");
                                    String Long2 = parts[0];
                                    String Lat2 = parts[1];
                                    String Longitude2 = Long2.replaceAll("[\\[\\]]", "");
                                    Double nearByLongitude = Double.parseDouble(Longitude2);
                                    String Latitude2 = Lat2.replaceAll("[\\[\\]]", "");
                                    Double nearByLatitude = Double.parseDouble(Latitude2);
                                    System.out.println(nearByLongitude+"fsdfgsd"+nearByLatitude+"camechett");
                                    float distance = distFrom(nearByLatitude, nearByLongitude);
                                    Integer distance1 = (int)distance;
                                    System.out.println(distance1+"distance"+textvalue);
                                    if (distance1 == Integer.parseInt(textvalue) )
                                    {
                                        System.out.println("going inside the loop");
                                        LatLng cut_loc = new LatLng(nearByLatitude, nearByLongitude);
                                        Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                        getMap().addMarker(new MarkerOptions()
                                                .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                        LatLng initial_coordinate = new LatLng(nearByLatitude, nearByLongitude);
                                        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                        getMap().animateCamera(initial_location);
                                    }
                                    else if (Integer.parseInt(textvalue) == 20 )
                                    {
                                        LatLng cut_loc = new LatLng(-37.74401397, 144.79880292);
                                        Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                        getMap().addMarker(new MarkerOptions()
                                                .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                        LatLng initial_coordinate = new LatLng(-37.74401397, 144.79880292);
                                        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                        getMap().animateCamera(initial_location);

                                    }
                                    else if (Integer.parseInt(textvalue) == 30 )
                                    {
                                        LatLng cut_loc = new LatLng(-37.74423096,144.79878294);
                                        Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                        getMap().addMarker(new MarkerOptions()
                                                .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                        LatLng initial_coordinate = new LatLng(-37.74423096,144.79878294);
                                        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                        getMap().animateCamera(initial_location);

                                    }
                                    else if (Integer.parseInt(textvalue) == 40 )
                                    {
                                        LatLng cut_loc = new LatLng(-37.74420603,144.79878096);
                                        Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                        getMap().addMarker(new MarkerOptions()
                                                .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                        LatLng initial_coordinate = new LatLng(-37.74420603,144.79878096);
                                        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                        getMap().animateCamera(initial_location);

                                    }
                                    else if (Integer.parseInt(textvalue) == 50 )
                                    {
                                        LatLng cut_loc = new LatLng(-37.74421701,144.79885602);
                                        Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                        getMap().addMarker(new MarkerOptions()
                                                .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                        LatLng initial_coordinate = new LatLng(-37.74421701,144.79885602);
                                        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                        getMap().animateCamera(initial_location);

                                    }

                                    else if (Integer.parseInt(textvalue) == 77 )
                                    {
                                        LatLng cut_loc = new LatLng( -37.74403296,144.79875594);
                                        Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                        getMap().addMarker(new MarkerOptions()
                                                .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                        LatLng initial_coordinate = new LatLng(-37.74403296,144.79875594);
                                        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                        getMap().animateCamera(initial_location);

                                    }
                                    else
                                    {
                                        LatLng cut_loc = new LatLng( -37.744182,144.79834698);
                                        Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                        getMap().addMarker(new MarkerOptions()
                                                .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                        LatLng initial_coordinate = new LatLng(-37.744182,144.79834698);
                                        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                        getMap().animateCamera(initial_location);

                                    }
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });
    }

    @Override
    protected void startDemo() {
        loadmap();
    }

    private void loadmap(){
        LatLng initial_coordinate = new LatLng(-37.74424896, 144.79836408);
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,30);
        getMap().animateCamera(initial_location);

        try {
            GeoJsonLayer layer_temp = new GeoJsonLayer(getMap(), R.raw.temp, this);
            GeoJsonLayer layer_fault_cable = new GeoJsonLayer(getMap(), R.raw.fault_cable, this);
            GeoJsonLayer layer_fault_eqp = new GeoJsonLayer(getMap(), R.raw.fault_equipment, this);
            GeoJsonLayer layer_fault_locids = new GeoJsonLayer(getMap(), R.raw.fault_selected_address, this);
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.fault_cable)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null; ) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONObject parentObject = new JSONObject(tokener);
            parentArray = parentObject.getJSONArray("features");

            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject c = parentArray.getJSONObject(i);
                JSONObject properties = c.getJSONObject("geometry");
                String coordinates = properties.getString("coordinates");
                ListItems.Coordinateslist.add(coordinates);



               /* Float value = distFrom(-37.743732,144.79243596,-33.8291,151.248);
                System.out.println(value+"distancefound");*/
            }

            for (GeoJsonFeature feature : layer_fault_cable.getFeatures()) {
                    layer_temp.addFeature(feature);
            }
            for (GeoJsonFeature feature : layer_fault_eqp.getFeatures()) {
                layer_temp.addFeature(feature);
            }
            for (GeoJsonFeature feature : layer_fault_locids.getFeatures()) {
                layer_temp.addFeature(feature);
            }
            addColorsToMarkers(layer_temp);
            layer_temp.addLayerToMap();
            layer_temp.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {

                @Override
                public void onFeatureClick(Feature feature)
                {
                    // Toast.makeText(SurveyActivity.this, "Feature clicked: " + feature.getProperty("ID"), Toast.LENGTH_SHORT).show();
                    if(feature.hasProperty("ID")) {
                        System.out.println(feature.getProperty("coordinates")+"propertygot");
                        if(feature.getProperty("ID").contains("LOC000005005354")||
                                feature.getProperty("ID").contains("LOC000024269703")||
                                feature.getProperty("ID").contains("LOC000025168142")||
                                feature.getProperty("ID").contains("LOC000043519542")){
                            Toast.makeText(FaultRemediationActivity.this, "Run OTDR Trace from DJL", Toast.LENGTH_SHORT).show();
                            LatLng djl_loc = new LatLng(-37.74403296, 144.79875594);
                            CameraUpdate d_location = CameraUpdateFactory.newLatLngZoom(djl_loc,25);
                            getMap().animateCamera(d_location);
                        }
                    }



                }

            });


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static float distFrom(double lat1, double lng1) {
        double earthRadius = 6371000; //meters
        double lat2 = -37.74403296;
        double lng2 = 144.79875594;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(0)+"text got from speech");
                    Speechvalue = result.get(0).toString();
                    if(Speechvalue != null && !Speechvalue.isEmpty()) {
                        String[] words = Speechvalue.split("\\s");
                        for(String w:words){
                            System.out.println(words+"words");
                            if (w.equals("name"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("My name is Intellegent network field assistant Version 1.0", TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                });
                            }
                            if (w.equals("call"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Calling the designer for this area", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(5000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(Intent.ACTION_CALL);
                                            intent.setData(Uri.parse("tel:" + "8489733394"));
                                            FaultRemediationActivity.this.startActivity(intent);
                                        }
                                    }
                                });
                            }
                            if (w.equals("customers"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("9 customers are affected due to the fibre cut", TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                });
                            }
                            if (w.equals("message"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Type the message you would like to send", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(5000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "8489733394"));
                                            intent.putExtra("sms_body", "Hi");
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            if (w.equals("designation"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Network field engineer", TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                });

                            }
                            if (w.equals("upkeep")||w.equals("maintenance"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Upkeep for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(4000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(getApplicationContext(), EquipmentUpkeep.class);
                                            startActivity(intent);
                                        }
                                    }
                                });

                            }
                            if (w.equals("underground")||w.equals("UG")||w.equals("under")||w.equals("ground"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Underground Survey for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(4000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                                            Bundle extras = new Bundle();
                                            extras.putBoolean("ugstate", true);
                                            extras.putBoolean("fiberstate", false);
                                            extras.putBoolean("copperstate", false);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            if (w.equals("fibre"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Fiber Survey for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(4000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                                            Bundle extras = new Bundle();
                                            extras.putBoolean("ugstate", false);
                                            extras.putBoolean("fiberstate", true);
                                            extras.putBoolean("copperstate", false);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            if (w.equals("copper")||w.equals("Copper"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Copper Survey for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(4000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                                            Bundle extras = new Bundle();
                                            extras.putBoolean("ugstate", false);
                                            extras.putBoolean("fiberstate", false);
                                            extras.putBoolean("copperstate", true);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
                break;
            }

        }
    }

}
