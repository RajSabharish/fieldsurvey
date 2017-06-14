package com.google.maps.android.utils.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Locale;

public class EquipmentUpkeep extends BaseDemoActivity {
    private String textvalue;
    private ImageButton btnSpeak;
    private String Speechvalue;
    TextToSpeech t1;
    private final int REQ_CODE_SPEECH_INPUT = 100;

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
        btnSpeak = (ImageButton)this.findViewById(R.id.nlpButton);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
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
                                            EquipmentUpkeep.this.startActivity(intent);
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
                                            t1.speak("20 customers are affected due to equipment failures", TextToSpeech.QUEUE_FLUSH, null);
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
                        }
                    }
                }
                break;
            }

        }
    }
}
