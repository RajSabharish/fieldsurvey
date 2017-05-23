package com.google.maps.android.utils.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MaintenanceActivity extends BaseDemoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addColorsToMarkers(GeoJsonLayer layer)
    {

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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MaintenanceActivity.this);
                alertDialog.setTitle("Locate Cable cut");
                alertDialog.setMessage("Enter Length");

                final EditText input = new EditText(MaintenanceActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LatLng cut_loc = new LatLng(-37.744164, 144.798607);
                                Bitmap cut_im = BitmapFactory.decodeResource(getResources(), R.drawable.icon_defects);
                                getMap().addMarker(new MarkerOptions()
                                        .position(cut_loc).icon(BitmapDescriptorFactory.fromBitmap(cut_im)).title("Fiber cut location"));
                                LatLng initial_coordinate = new LatLng(-37.744164, 144.798607);
                                CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,35);
                                getMap().animateCamera(initial_location);
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


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*

    private void adddetails(){
        JSONArray parentArray;
        try {
            GeoJsonLayer layer_temp = new GeoJsonLayer(getMap(), R.raw.temp, this);
            GeoJsonLayer layer_tls_eqp_main = new GeoJsonLayer(getMap(), R.raw.equipment_all, this);
            for (GeoJsonFeature feature : layer_tls_eqp_main.getFeatures()) {
                String eqp_id = feature.getProperty("EQUIPMENT_ID");
                if (eqp_id.contains("3KGP-01-00-DJL-001")) {
                    layer_temp.addFeature(feature);
                }
            }
            addlayerinmap(layer_temp);


            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.cable)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null; ) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONObject parentObject = new JSONObject(tokener);
            parentArray = parentObject.getJSONArray("features");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addsymbols(GeoJsonLayer layer)
    {

        for (GeoJsonFeature feature : layer.getFeatures())
        {

            Bitmap inc_im = BitmapFactory.decodeResource(getResources(), R.drawable.incident_note);

            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
            pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(inc_im));
            pointStyle.setTitle("Alert");
            pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));

            feature.setPointStyle(pointStyle);

        }
    }


    private void showdetails(String id){
        try {
            String end_point_id="",cable_id="",start_node_id="";
            GeoJsonLayer layer_temp = new GeoJsonLayer(getMap(), R.raw.temp, this);
            GeoJsonLayer layer_eqp = new GeoJsonLayer(getMap(), R.raw.equipment_all, this);
            GeoJsonLayer layer_cable = new GeoJsonLayer(getMap(), R.raw.cable, this);

            for (GeoJsonFeature feature : layer_eqp.getFeatures()) {
                String eqp_id = feature.getProperty("EQUIPMENT_ID");
                if (eqp_id.contains(id)) {
                    end_point_id=feature.getProperty("EQUIPMENT_ID");
                    layer_temp.addFeature(feature);
                }
            }

            for (GeoJsonFeature feature : layer_cable.getFeatures()) {
                if (feature.getProperty("END_NODE_ID").contains(end_point_id)) {
                    cable_id=feature.getProperty("ID");
                    start_node_id=feature.getProperty("START_NODE_ID");
                    layer_temp.addFeature(feature);
                }
            }

            for (GeoJsonFeature feature : layer_eqp.getFeatures()) {
                if (feature.getProperty("EQUIPMENT_ID").contains(start_node_id)) {
                    layer_temp.addFeature(feature);
                }
            }
            layer_temp.addLayerToMap();
            final String finalStart_node_id = start_node_id;
            layer_temp.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {

                @Override
                public void onFeatureClick(Feature feature)
                {
                    if(feature.hasProperty("EQUIPMENT_ID"))
                    {
                        if(feature.getProperty("EQUIPMENT_ID").contains(finalStart_node_id)){

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

    private void addlayerinmap(final GeoJsonLayer layer)
    {
        addsymbols(layer);

        layer.addLayerToMap();

        layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener()
        {
            @Override
            public void onFeatureClick(Feature feature)
            {
               String selected = feature.getProperty("EQUIPMENT_ID");
            }
        });

        getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker) {
                layer.removeLayerFromMap();
                inspect_id = String.valueOf(marker.getSnippet());

            }
        });

    }*/
}
