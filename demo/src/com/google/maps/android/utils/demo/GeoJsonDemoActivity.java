package com.google.maps.android.utils.demo;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GeoJsonDemoActivity extends BaseDemoActivity {

    private final static String mLogTag = "GeoJsonDemo";
    public static String inspect_id;
    public static List<String> pit_ids = new ArrayList<String>();

    protected int getLayoutId() {
        return R.layout.geojson_demo;
    }

    @Override
    protected void startDemo()
    {
        retrieveFileFromResource();
    }


    private void retrieveFileFromResource()
    {
        String start_node_tmp,end_node_tmp;

        try {
            GeoJsonLayer layer_tls_trench = new GeoJsonLayer(getMap(), R.raw.cable_duct_trench, this);
            GeoJsonLayer layer_temp_pit = new GeoJsonLayer(getMap(),R.raw.pit,this);
            for (GeoJsonFeature feature : layer_tls_trench.getFeatures()) {
                start_node_tmp=feature.getProperty("START_NODE_ID");
                end_node_tmp=feature.getProperty("END_NODE_ID");
                addtolist(start_node_tmp);
                addtolist(end_node_tmp);
            }
            for (GeoJsonFeature feature:layer_temp_pit.getFeatures()){
                for (int i=0;i<pit_ids.size();i++){
                    if(feature.getProperty("ID").equals(pit_ids.get(i))){
                        layer_tls_trench.addFeature(feature);
                    }
                }
            }
            addGeoJsonLayerToMap(layer_tls_trench);


        } catch (IOException e) {
            Log.e(mLogTag, "GeoJSON file could not be read");
        } catch (JSONException e) {
            Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
        }
    }

    public void addtolist(String id){
        int flag=0;
        for (int i=0;i<pit_ids.size();i++){
            if(pit_ids.get(i).equals(id)){
                flag = 1;
            }
        }
        if (flag==0){
            pit_ids.add(id);
        }
    }


    private void addColorsToMarkers(GeoJsonLayer layer)
    {

        for (GeoJsonFeature feature : layer.getFeatures())
        {

                Bitmap pit_im = BitmapFactory.decodeResource(getResources(), R.drawable.telstra_pit);
                GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
                pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(pit_im));
                pointStyle.setTitle("Pit");
                pointStyle.setSnippet(feature.getProperty("ID"));

                feature.setPointStyle(pointStyle);

        }
    }

    private void addColorsToTrenches(GeoJsonLayer layer)
    {

        for (GeoJsonFeature feature : layer.getFeatures())
        {

            GeoJsonLineStringStyle lineStringStyle = new GeoJsonLineStringStyle();
            lineStringStyle.setColor(Color.GREEN);
            lineStringStyle.setClickable(true);
            feature.setLineStringStyle(lineStringStyle);

        }
    }

  private void highlightselected(String ID, GeoJsonLayer layer)
  {

      for (GeoJsonFeature feature : layer.getFeatures())
      {

          GeoJsonLineStringStyle lineStringStyle = new GeoJsonLineStringStyle();
          lineStringStyle.setColor(Color.GREEN);
          lineStringStyle.setClickable(true);
          feature.setLineStringStyle(lineStringStyle);

          if (ID.equals(feature.getProperty("TRENCH_ID")))
          {
              lineStringStyle.setColor(Color.RED);
              lineStringStyle.setClickable(true);
              feature.setLineStringStyle(lineStringStyle);
          }


      }

  }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
               case DialogInterface.BUTTON_POSITIVE:
                    Ipsum.DuctId.clear();
                    Ipsum.Details.clear();
                    int flag=0;
                    try {
                        GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.cable_duct_trench, getApplicationContext());
                        GeoJsonLayer layer1 = new GeoJsonLayer(getMap(), R.raw.cable, getApplicationContext());
                        for (GeoJsonFeature feature : layer.getFeatures()) {
                            if(feature.hasProperty("TRENCH_ID"))
                            {
                                if(feature.getProperty("TRENCH_ID").equals(inspect_id))
                                 {
                                    if(searchductid(feature.getProperty("Duct_ID")))
                                    {
                                        int count=0;
                                        Ipsum.DuctId.add(feature.getProperty("Duct_ID"));
                                        Ipsum.Details.add(feature.getProperty("LENGTH"));
                                        Ipsum.Details.add(feature.getProperty("SIZE"));
                                        Ipsum.Details.add(feature.getProperty("MATERIAL"));
                                        Ipsum.Details.add(feature.getProperty("PERCENTAGE_FULL"));
                                        Ipsum.Details.add(feature.getProperty("MAX_MANDREL"));
                                        Ipsum.Details.add(feature.getProperty("DUCT_CODE"));
                                        Ipsum.Details.add(feature.getProperty("OWNER"));
                                        for (GeoJsonFeature feature1 : layer.getFeatures()) {
                                            if (feature.getProperty("Duct_ID").equals(feature1.getProperty("Duct_ID"))){
                                                count++;
                                                Ipsum.CableIDs.add(feature1.getProperty("CABLE_ID"));
                                                for (GeoJsonFeature feature2 : layer1.getFeatures()) {
                                                    if (feature1.getProperty("CABLE_ID").equals(feature2.getProperty("ID"))){
                                                        Ipsum.CableIDs.add(feature2.getProperty("TYPE"));
                                                    }
                                                }
                                            }
                                        }
                                        Ipsum.CableCount.add(Integer.toString(count));
                                        flag = 1;
                                    }
                                }

                            }
                        }


                        if (flag==1) {

                            startActivity(new Intent(getApplicationContext(), TrenchSurveyActivity.class));
                        }  else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(GeoJsonDemoActivity.this);
                            alert.setTitle("Error");
                            alert.setMessage("No Duct found for this Trench");
                            alert.setPositiveButton("OK",null);
                            alert.show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    DialogInterface.OnClickListener pit_dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public boolean searchductid(String id){
        for (int i=0;i<Ipsum.DuctId.size();i++){
            if(Ipsum.DuctId.get(i).equals(id)){
                return false;
            }
        }
        return true;
    }



    private void addGeoJsonLayerToMap(final GeoJsonLayer layer)
    {

        addColorsToTrenches(layer);
        addColorsToMarkers(layer);
        layer.addLayerToMap();
        LatLng initial_coordinate = new LatLng(-37.74499146, 144.79864884);
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,15);
        getMap().animateCamera(initial_location);
        layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {

            @Override
            public void onFeatureClick(Feature feature)
            {
               // Toast.makeText(GeoJsonDemoActivity.this, "Feature clicked: " + feature.getProperty("ID"), Toast.LENGTH_SHORT).show();
                if(feature.hasProperty("TRENCH_ID")) {
                    highlightselected(feature.getProperty("TRENCH_ID"), layer);
                }
                inspect_id = feature.getProperty("TRENCH_ID");

                if (feature.hasProperty("TRENCH_ID"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GeoJsonDemoActivity.this);
                    builder.setMessage("Do you want to survey this Trench").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

                }

            }

            }

        );
        getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(GeoJsonDemoActivity.this,String.valueOf(marker.getPosition())+ String.valueOf(marker.getSnippet()),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(GeoJsonDemoActivity.this);
                builder.setMessage("Do you want to survey this Pit").setPositiveButton("Yes", pit_dialogClickListener).setNegativeButton("No", pit_dialogClickListener).show();

            }
        });

    }

}