package com.google.maps.android.utils.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
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

public class ConstructionActivity extends BaseDemoActivity {
    private ImageButton btnSpeak;
    private String Speechvalue;
    TextToSpeech t1;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    protected int getLayoutId() {
        return R.layout.activity_construction;
    }


    @Override
    protected void startDemo() {
        loadmap(true,true,true);
    }

    public void loadmap(boolean trenchlayer, boolean pitlayer, boolean eqplayer)
    {
        final CharSequence[] items = {"Trench","Pit","Equipment"};
        final ArrayList selectedItems=new ArrayList();

        LatLng initial_coordinate = new LatLng(-37.74499146, 144.79864884);
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate, 25);
        getMap().animateCamera(initial_location);
        try
        {
            final GeoJsonLayer layer_temp = new GeoJsonLayer(getMap(), R.raw.temp, this);
            GeoJsonLayer layer_eqp_all = new GeoJsonLayer(getMap(), R.raw.equipment_all, this);
            GeoJsonLayer layer_tls_pit = new GeoJsonLayer(getMap(), R.raw.pit, this);
            GeoJsonLayer layer_tls_trench = new GeoJsonLayer(getMap(), R.raw.tls_trench, this);

            if(eqplayer)
            {
                for (GeoJsonFeature feature : layer_eqp_all.getFeatures())
                {
                    layer_temp.addFeature(feature);
                }
            }
            if(pitlayer)
            {
                for (GeoJsonFeature feature : layer_tls_pit.getFeatures())
                {
                    layer_temp.addFeature(feature);
                }
            }
            if(trenchlayer)
            {
                for (GeoJsonFeature feature : layer_tls_trench.getFeatures())
                {
                    layer_temp.addFeature(feature);
                }
            }

            addGeoJsonLayerToMap(layer_temp);
            ImageButton home_button = (ImageButton) findViewById(R.id.home_button);

            home_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newIntent_book = new Intent(ConstructionActivity.this,MainActivity.class);
                    startActivity(newIntent_book);
                }
            });

            btnSpeak = (ImageButton)this.findViewById(R.id.nlpButton);

            btnSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promptSpeechInput();
                }
            });

            ImageButton layer_button = (ImageButton) findViewById(R.id.layer_button);

            layer_button.setOnClickListener(new View.OnClickListener()
            {
                AlertDialog dialog;

                @Override
                public void onClick(View view)
                {
                    selectedItems.clear();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConstructionActivity.this);
                    builder.setTitle("Choose Layers");
                    builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked)
                        {
                            if (isChecked)
                            {
                                selectedItems.add(indexSelected);
                            }
                            else if (selectedItems.contains(indexSelected))
                            {
                                selectedItems.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                boolean trench=false,pit=false,eqp=false;
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {

                                    for(int i=0;i<selectedItems.size();i++)
                                    {
                                        if(selectedItems.get(i).equals(0))
                                        {
                                            trench=true;
                                        }
                                        else if(selectedItems.get(i).equals(1))
                                        {
                                            pit=true;
                                        }
                                        else if(selectedItems.get(i).equals(2))
                                        {
                                            eqp=true;
                                        }

                                    }
                                    layer_temp.removeLayerFromMap();
                                    loadmap(trench,pit,eqp);
                                }

                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int id){ }
                            });
                    dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    dialog.show();
                }
            });

            ImageButton information_button = (ImageButton) findViewById(R.id.information_button);

            information_button.setOnClickListener(new View.OnClickListener(){
                AlertDialog dialog;
                @Override
                public void onClick(View v){

                    AlertDialog.Builder builder = new AlertDialog.Builder(ConstructionActivity.this);
                    builder.setTitle("Information");
                    String sam = "Area Code : 3KGP-01";
                    String designer = "Designer : Manoj";
                    builder.setMessage(sam + "\n" + designer + "\n");
                    builder.setPositiveButton("Call",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int id){
                            Intent intent = new Intent(Intent.ACTION_CALL);

                            intent.setData(Uri.parse("tel:" + "8489733394"));
                            ConstructionActivity.this.startActivity(intent);
                        }

                    });

                    builder.setNegativeButton("Message",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int id){
                            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + "8489733394"));
                            intent.putExtra( "sms_body", "Hi" );
                            startActivity(intent);
                        }

                    });

                    builder.setNeutralButton("Cancel",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog,int id){}
                    });


                    dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    dialog.show();

                }
            });

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
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
            Bitmap fus_im = BitmapFactory.decodeResource(getResources(), R.drawable.fuse);
            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
            if(feature.hasProperty("EQUIPMENT_ID"))
            {
                if(feature.getProperty("EQUIPMENT_ID").contains("FNO"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(fno_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("EBR"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(ebr_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("MPT"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(mpt_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("CJL"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(cjl_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("DJL"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(djl_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("ODF"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(odf_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("FUS"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(fus_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }

            }
            else
                {
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

    private void addGeoJsonLayerToMap(final GeoJsonLayer layer)
    {
        addColorsToMarkers(layer);
        addColorsToTrenches(layer);
        layer.addLayerToMap();
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
                                            ConstructionActivity.this.startActivity(intent);
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
