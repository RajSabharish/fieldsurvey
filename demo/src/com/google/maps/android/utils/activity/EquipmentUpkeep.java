package com.google.maps.android.utils.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.utils.demo.R;

import org.json.JSONException;

import java.io.IOException;

public class EquipmentUpkeep extends BaseDemoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void startDemo() {
        loadmap();
    }

    private void loadmap()
    {
        LatLng initial_coordinate = new LatLng(-37.74403296, 144.79875594);
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate,15);
        getMap().animateCamera(initial_location);
        try
        {
            final GeoJsonLayer layer_temp = new GeoJsonLayer(getMap(), R.raw.temp, this);
            GeoJsonLayer layer_eqp_all = new GeoJsonLayer(getMap(), R.raw.equipment_all, this);

            for (GeoJsonFeature feature : layer_eqp_all.getFeatures())
            {
                layer_temp.addFeature(feature);
            }
            addGeoJsonLayerToMap(layer_temp);
        }
          catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addGeoJsonLayerToMap(final GeoJsonLayer layer)
    {
        addColorsToMarkers(layer);
        addColorsToTrenches(layer);
        layer.addLayerToMap();
    }
    private void addColorsToMarkers(GeoJsonLayer layer)
    {
        ImageButton home_button = (ImageButton) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent_book = new Intent(EquipmentUpkeep.this,MainActivity.class);
                startActivity(newIntent_book);
            }
        });

        for (GeoJsonFeature feature : layer.getFeatures())
        {
            Bitmap pit_im = BitmapFactory.decodeResource(getResources(), R.drawable.pit);
            Bitmap ebr_im = BitmapFactory.decodeResource(getResources(), R.drawable.ebr);
            Bitmap fno_im = BitmapFactory.decodeResource(getResources(), R.drawable.fno);
            Bitmap mpt_im = BitmapFactory.decodeResource(getResources(), R.drawable.mpt);
            Bitmap cjl_im = BitmapFactory.decodeResource(getResources(), R.drawable.cjl);
            Bitmap djl_im = BitmapFactory.decodeResource(getResources(), R.drawable.djl);
            Bitmap odf_im = BitmapFactory.decodeResource(getResources(), R.drawable.fan);
            Bitmap fus_im = BitmapFactory.decodeResource(getResources(), R.drawable.fuse);
            Bitmap alert_im = BitmapFactory.decodeResource(getResources(), R.drawable.incident_note);
            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
            if(feature.hasProperty("EQUIPMENT_ID"))
            {
                System.out.println("going");
                if(feature.getProperty("EQUIPMENT_ID").contains("FNO"))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the FNO");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(fno_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("EBR"))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the EBR");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(ebr_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("MPT"))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the MPT");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(mpt_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("CJL"))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the CJL");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(cjl_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("DJL"))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the DJL");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(djl_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("ODF"))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the ODF");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(odf_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("FUS"))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the FUS");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(fus_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }

                if((feature.getProperty("EQUIPMENT_ID").contains("3KGP-01-02-MPT-001"))&&(feature.getProperty("EQUIPMENT_ID").contains("3KGP-01-01-EBR-002")))
                {
                    System.out.println((feature.getProperty("EQUIPMENT_ID"))+"inside the FNO");
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(alert_im));
                    pointStyle.setTitle("Warning !! Equipment may be submerged in water");
                    pointStyle.setSnippet(feature.getProperty("TYPE"));
                }

            }
            else
            {
                System.out.println("inside the complete else");
                pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(pit_im));
                pointStyle.setTitle("Pit");
                pointStyle.setSnippet(feature.getProperty("ID"));
            }

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

}
