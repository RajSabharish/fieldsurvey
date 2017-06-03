package com.google.maps.android.utils.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.utils.demo.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlternateDucts extends BaseDemoActivity {
    private final static String mLogTag = "ShowList";
    public static String trench_id,current_pits_Utilization,currentduct_size;
    public static String alternate_pits,alternate_pits_size,alternate_pits_Utilization;

    @Override
    protected void startDemo() {
        retrieveFileFromResource();
    }

    public void retrieveFileFromResource() {
        ImageButton home_button = (ImageButton) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent_book = new Intent(AlternateDucts.this,MainActivity.class);
                startActivity(newIntent_book);
            }
        });
        setContentView(R.layout.activity_alternate_ducts);
        Bundle scrollingdata = getIntent().getExtras();
        String id = scrollingdata.getString("id_key");
        System.out.println(id + "id");
        try {
            System.out.println("before calling the tryfunction");
            GeoJsonLayer layer_tls_duct = new GeoJsonLayer(getMap(), R.raw.tls_duct, this);
            System.out.println(layer_tls_duct + "####layer_tls_trench###");
            FindTrench(layer_tls_duct, id);
        } catch (IOException e) {
            Log.e(mLogTag, "GeoJSON file could not be read");
        } catch (JSONException e) {
            Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
        }
    }

    public void FindTrench(final GeoJsonLayer layer, String id) {
        System.out.println(id + "idcamefinally");
        for (GeoJsonFeature feature : layer.getFeatures()) {
            if (id.equals(feature.getProperty("ID"))) {
                System.out.println("inside the loop");
                trench_id = feature.getProperty("TRENCH_ID");
                currentduct_size = feature.getProperty("SIZE");
                current_pits_Utilization = feature.getProperty("PERCENTAGE_FULL");
                System.out.println(trench_id + "trench_idcame");
                alternate_ducts(layer, trench_id, id);
            } /*else {
                Toast.makeText(AlternateDucts.this, "Alternative Pit id not found", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    public void alternate_ducts(final GeoJsonLayer layer, String trench_id, String id) {
        List<String> myList = new ArrayList<String>();
        for (GeoJsonFeature feature : layer.getFeatures()) {
            if (trench_id.equals(feature.getProperty("TRENCH_ID"))) {
                alternate_pits = feature.getProperty("ID");
                myList.add(alternate_pits);
                myList.remove(id);
            }
        }
        Checksize((ArrayList<String>) myList, layer);
    }

    public void Checksize(ArrayList<String> myList, final GeoJsonLayer layer) {
        List<String> SizedList = new ArrayList<String>();
        for (int i = 0; i < myList.size(); i++) {
            for (GeoJsonFeature feature : layer.getFeatures()) {
                if (myList.get(i).equals(feature.getProperty("ID"))) {
                    alternate_pits_size = feature.getProperty("SIZE");
                    if (Integer.valueOf(currentduct_size) <= Integer.valueOf(alternate_pits_size))
                    {
                        SizedList.add(myList.get(i));

                    }
                }
            }
        }
        CheckUtilization((ArrayList<String>) SizedList,layer);
    }

    public void CheckUtilization(ArrayList<String> SizedList,final GeoJsonLayer layer)
    {
        List<String> UtilizationList = new ArrayList<String>();
        for (int i = 0; i < SizedList.size(); i++)
        {
            for (GeoJsonFeature feature : layer.getFeatures()) {
                if (SizedList.get(i).equals(feature.getProperty("ID"))) {
                    alternate_pits_Utilization = feature.getProperty("PERCENTAGE_FULL");
                    if (Float.valueOf(alternate_pits_Utilization) <= 40.0)
                    {
                        UtilizationList.add(SizedList.get(i));
                    }
                }
            }
        }
        CheckMaterial((ArrayList<String>) UtilizationList,layer);
    }
    public void CheckMaterial(ArrayList<String>UtilizationList,final GeoJsonLayer layer)
    {
        List<String> MaterialList = new ArrayList<String>();
        for (int i = 0; i < UtilizationList.size(); i++)
        {
            System.out.println(UtilizationList.get(i));
        }
        setList((ArrayList<String>) UtilizationList);
    }
    public void setList(ArrayList<String> UtilizationList)
    {
        ListAdapter DuctAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,UtilizationList);
        ListView DuctsList = (ListView) findViewById(R.id.DuctListView);
        DuctsList.setAdapter(DuctAdapter);
    }

}