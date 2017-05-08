package com.google.maps.android.utils.demo;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
import java.util.Locale;

public class GeoJsonDemoActivity extends BaseDemoActivity {

    private final static String mLogTag = "GeoJsonDemo";
    public static String inspect_id;

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
        try {
            GeoJsonLayer layer_tls_trench = new GeoJsonLayer(getMap(), R.raw.tls_trench_pit, this);
            addGeoJsonLayerToMap(layer_tls_trench);
        } catch (IOException e) {
            Log.e(mLogTag, "GeoJSON file could not be read");
        } catch (JSONException e) {
            Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
        }
    }


    private void addColorsToMarkers(GeoJsonLayer layer)
    {

        for (GeoJsonFeature feature : layer.getFeatures())
        {

                Bitmap pit_im = BitmapFactory.decodeResource(getResources(), R.drawable.pit);
                GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
                pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(pit_im));
                pointStyle.setTitle("Pit : " + feature.getProperty("ID"));
                pointStyle.setSnippet("Size : " + feature.getProperty("SIZE"));
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

          if (ID.equals(feature.getProperty("ID")))
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
                        GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.tls_duct, getApplicationContext());
                        for (GeoJsonFeature feature : layer.getFeatures()) {
                            if(feature.getProperty("TRENCH_ID").equals(inspect_id)){
                                Ipsum.DuctId.add(feature.getProperty("ID"));
                                Ipsum.Details.add(feature.getProperty("A_END_BLOCK"));
                                Ipsum.Details.add(feature.getProperty("SIZE"));
                                Ipsum.Details.add(feature.getProperty("MATERIAL"));
                                Ipsum.Details.add(feature.getProperty("PERCENTAGE_FULL"));
                                Ipsum.Details.add(feature.getProperty("MAX_MANDREL"));
                                Ipsum.Details.add(feature.getProperty("DUCT_CODE"));
                                Ipsum.Details.add(feature.getProperty("OWNER"));
                                flag=1;
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



    private void addGeoJsonLayerToMap(final GeoJsonLayer layer)
    {

        addColorsToTrenches(layer);
        addColorsToMarkers(layer);
        layer.addLayerToMap();
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.74499146, 144.79864884),15));
        // Demonstrate receiving features via GeoJsonLayer clicks.
        layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {

            @Override
            public void onFeatureClick(Feature feature)
            {
                Toast.makeText(GeoJsonDemoActivity.this, "Feature clicked: " + feature.getProperty("ID"), Toast.LENGTH_SHORT).show();
                highlightselected(feature.getProperty("ID"), layer);
                inspect_id = feature.getProperty("ID");
                if (feature.hasProperty("TLS_ID_ROUTE"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GeoJsonDemoActivity.this);
                    builder.setMessage("Do you want to survey this Trench").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

                }

            }

            }

        );

    }

}