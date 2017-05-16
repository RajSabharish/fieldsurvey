package com.google.maps.android.utils.demo;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
        final EditText jointtypeText = (EditText) this.findViewById(R.id.joint_type_text);
        ownerSpinner = (Spinner)this.findViewById(R.id.owner_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,owners);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerSpinner.setAdapter(adapter);

        try {
            GeoJsonLayer eqpandpit_layer = new GeoJsonLayer(getMap(), R.raw.equipmentandpit, this);
            final GeoJsonLayer eqp_layer = new GeoJsonLayer(getMap(), R.raw.equipment_all, this);
            GeoJsonLayer pit_layer = new GeoJsonLayer(getMap(), R.raw.pit, this);
            final List<String> tmplist=new ArrayList<String>();
            tmplist.clear();
            for (GeoJsonFeature feature : pit_layer.getFeatures()) {
                if(feature.getProperty("ID").equals(pitId)){
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
                    for (GeoJsonFeature feature_1 : eqpandpit_layer.getFeatures()) {
                        if(feature.getProperty("ID").equals(feature_1.getProperty("ID"))){
                            tmplist.add(feature_1.getProperty("EQUIPMENT_ID"));
                        }
                    }
                }

            }

            if(tmplist.size()>0) {
                eqpSpinner = (Spinner) this.findViewById(R.id.joint_id_spinner);
                ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tmplist);
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                eqpSpinner.setAdapter(adp);

                eqpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String joint_type_selected = "";
                        int eqp_pos = eqpSpinner.getSelectedItemPosition();
                        String eqp_selected = tmplist.get(eqp_pos);
                        for (GeoJsonFeature feature : eqp_layer.getFeatures()) {
                            if (feature.getProperty("EQUIPMENT_ID").equals(eqp_selected)) {
                                joint_type_selected = feature.getProperty("TYPE");
                            }
                        }
                        jointtypeText.setText(joint_type_selected);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
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
