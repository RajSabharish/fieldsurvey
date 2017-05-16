package com.google.maps.android.utils.demo;


import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;


/**
 * Created by raj.a.natarajan on 5/10/2017.
 */

public class PitSurveyActivity extends BaseDemoActivity{

    private static final String[]owners = {"NBN", "TELSTRA"};
    private Spinner ownerSpinner,eqpSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_survey);
        Bundle bundle = getIntent().getExtras();
        String pitId = bundle.getString("pitid");
        EditText pitIdText = (EditText) this.findViewById(R.id.structure_id_text);
        EditText sizeText = (EditText) this.findViewById(R.id.structure_size_text);
        EditText materialText = (EditText) this.findViewById(R.id.structure_material_text);
        EditText occupancyText = (EditText) this.findViewById(R.id.structure_occupancy_text);
        EditText jointtypeText = (EditText) this.findViewById(R.id.joint_type_text);
        ownerSpinner = (Spinner)this.findViewById(R.id.owner_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,owners);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerSpinner.setAdapter(adapter);

        try {
            GeoJsonLayer eqpandpit_layer = new GeoJsonLayer(getMap(), R.raw.equipmentandpit, this);
            GeoJsonLayer eqp_layer = new GeoJsonLayer(getMap(), R.raw.equipment, this);

            for (GeoJsonFeature feature : eqpandpit_layer.getFeatures()) {
                if(feature.getProperty("PIT_ID").equals(pitId)){
                    pitIdText.setText(pitId);
                    pitIdText.setKeyListener(null);
                    sizeText.setText(feature.getProperty("Size"));
                    materialText.setText(feature.getProperty("MATERIAL"));
                    occupancyText.setText(feature.getProperty("SPACE_OCCUPANCY"));
                    if ((feature.getProperty("OWNER").equals("NBN"))){
                        ownerSpinner.setSelection(0);
                    }
                    else
                    {
                        ownerSpinner.setSelection(1);
                    }


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void startDemo() {

    }


}
