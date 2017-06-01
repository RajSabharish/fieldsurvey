package com.google.maps.android.utils.activity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.utils.demo.R;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SurveyActivity extends BaseDemoActivity {

    private final static String mLogTag = "GeoJsonDemo";
    public static String inspect_id,pit_position;
    public static List<String> pit_ids = new ArrayList<String>();
    public static Boolean copper_eqp_state, fiber_eqp_state, ug_state;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        copper_eqp_state =extras.getBoolean("copperstate");
        fiber_eqp_state =extras.getBoolean("fiberstate");
        ug_state=extras.getBoolean("ugstate");
        ImageButton information_button = (ImageButton) findViewById(R.id.information_button);

        information_button.setOnClickListener(new View.OnClickListener() {
            AlertDialog dialog;

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                builder.setTitle("Information");
                String sam = "Area Code : 3KGP-01";
                String designer = "Designer : Manoj";
                builder.setMessage(sam + "\n" + designer + "\n");
                builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_CALL);

                        intent.setData(Uri.parse("tel:" + "8489733394"));
                        SurveyActivity.this.startActivity(intent);
                    }

                });

                builder.setNegativeButton("Message", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "8489733394"));
                        intent.putExtra("sms_body", "Hi");
                        startActivity(intent);
                    }

                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.show();
            }
        });

    }

    protected int getLayoutId() {
        return R.layout.activity_survey;
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
            GeoJsonLayer layer_eqp_pit = new GeoJsonLayer(getMap(), R.raw.equipmentandpit, this);
            GeoJsonLayer layer_temp = new GeoJsonLayer(getMap(), R.raw.temp, this);

            if (ug_state==true){
                for (GeoJsonFeature feature : layer_tls_trench.getFeatures()) {
                    start_node_tmp=feature.getProperty("START_NODE_ID");
                    end_node_tmp=feature.getProperty("END_NODE_ID");
                    addtolist(start_node_tmp);
                    addtolist(end_node_tmp);
                    layer_temp.addFeature(feature);
                }
                for (GeoJsonFeature feature:layer_temp_pit.getFeatures()){
                    for (int i=0;i<pit_ids.size();i++){
                        if(feature.getProperty("ID").equals(pit_ids.get(i))){
                            layer_temp.addFeature(feature);
                        }
                    }
                }
            }
            if (copper_eqp_state ==true) {
                for (GeoJsonFeature feature : layer_eqp_pit.getFeatures()) {
                    String eqp_id = feature.getProperty("EQUIPMENT_ID");
                    if (eqp_id.contains("CJL")) {
                        layer_temp.addFeature(feature);
                    }
                }
            }
            if (fiber_eqp_state==true){
                for (GeoJsonFeature feature : layer_eqp_pit.getFeatures()) {
                    String eqp_id = feature.getProperty("EQUIPMENT_ID");
                    if (eqp_id.contains("FNO")||eqp_id.contains("DJL")||eqp_id.contains("MPT")||eqp_id.contains("EBR")||eqp_id.contains("ODF")) {
                        layer_temp.addFeature(feature);
                    }
                }
            }

            addGeoJsonLayerToMap(layer_temp);


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

            Bitmap pit_im = BitmapFactory.decodeResource(getResources(), R.drawable.pit);
            Bitmap ebr_im = BitmapFactory.decodeResource(getResources(), R.drawable.ebr);
            Bitmap fno_im = BitmapFactory.decodeResource(getResources(), R.drawable.fno);
            Bitmap mpt_im = BitmapFactory.decodeResource(getResources(), R.drawable.mpt);
            Bitmap cjl_im = BitmapFactory.decodeResource(getResources(), R.drawable.cjl);
            Bitmap djl_im = BitmapFactory.decodeResource(getResources(), R.drawable.djl);
            Bitmap odf_im = BitmapFactory.decodeResource(getResources(), R.drawable.fan);
            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
            if(feature.hasProperty("EQUIPMENT_ID")){
                if(feature.getProperty("EQUIPMENT_ID").contains("FNO"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(fno_im));
                    pointStyle.setTitle("Pit");
                    pointStyle.setSnippet(feature.getProperty("PIT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("EBR"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(ebr_im));
                    pointStyle.setTitle("Pit");
                    pointStyle.setSnippet(feature.getProperty("PIT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("MPT"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(mpt_im));
                    pointStyle.setTitle("Pit");
                    pointStyle.setSnippet(feature.getProperty("PIT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("CJL"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(cjl_im));
                    pointStyle.setTitle("Pit");
                    pointStyle.setSnippet(feature.getProperty("PIT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("DJL"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(djl_im));
                    pointStyle.setTitle("Pit");
                    pointStyle.setSnippet(feature.getProperty("PIT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("ODF"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(odf_im));
                    pointStyle.setTitle("Pit");
                    pointStyle.setSnippet(feature.getProperty("PIT_ID"));
                }

            }
            else {
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
                    ListItems.DuctId.clear();
                    ListItems.Details.clear();
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
                                        ListItems.DuctId.add(feature.getProperty("Duct_ID"));
                                        ListItems.Details.add(feature.getProperty("LENGTH"));
                                        ListItems.Details.add(feature.getProperty("SIZE"));
                                        ListItems.Details.add(feature.getProperty("MATERIAL"));
                                        ListItems.Details.add(feature.getProperty("PERCENTAGE_FULL"));
                                        ListItems.Details.add(feature.getProperty("MAX_MANDREL"));
                                        ListItems.Details.add(feature.getProperty("DUCT_CODE"));
                                        ListItems.Details.add(feature.getProperty("OWNER"));
                                        for (GeoJsonFeature feature1 : layer.getFeatures()) {
                                            if (feature.getProperty("Duct_ID").equals(feature1.getProperty("Duct_ID"))){
                                                count++;
                                                ListItems.CableIDs.add(feature1.getProperty("CABLE_ID"));
                                                for (GeoJsonFeature feature2 : layer1.getFeatures()) {
                                                    if (feature1.getProperty("CABLE_ID").equals(feature2.getProperty("ID"))){
                                                        ListItems.CableIDs.add(feature2.getProperty("TYPE"));
                                                    }
                                                }
                                            }
                                        }
                                        ListItems.CableCount.add(Integer.toString(count));
                                        flag = 1;
                                    }
                                }

                            }
                        }


                        if (flag==1) {

                            startActivity(new Intent(getApplicationContext(), TrenchSurveyActivity.class));
                        }  else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(SurveyActivity.this);
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
                    Intent i = new Intent(SurveyActivity.this,PitSurveyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("pitid",inspect_id);
                    bundle.putString("pitposition",pit_position);
                    i.putExtras(bundle);
                    startActivity(i);

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };


    public boolean searchductid(String id){
        for (int i = 0; i< ListItems.DuctId.size(); i++){
            if(ListItems.DuctId.get(i).equals(id)){
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
               // Toast.makeText(SurveyActivity.this, "Feature clicked: " + feature.getProperty("ID"), Toast.LENGTH_SHORT).show();
                if(feature.hasProperty("TRENCH_ID")) {
                    highlightselected(feature.getProperty("TRENCH_ID"), layer);
                }
                inspect_id = feature.getProperty("TRENCH_ID");

                if (feature.hasProperty("TRENCH_ID"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                    builder.setMessage("Do you want to survey this Trench").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

                }


            }

            }

        );
        getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(SurveyActivity.this,String.valueOf(marker.getPosition())+ String.valueOf(marker.getSnippet()),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                inspect_id=String.valueOf(marker.getSnippet());
                pit_position = String.valueOf(marker.getPosition());
                builder.setMessage("Do you want to survey this Pit").setPositiveButton("Yes", pit_dialogClickListener).setNegativeButton("No", pit_dialogClickListener).show();

            }
        });

    }



}