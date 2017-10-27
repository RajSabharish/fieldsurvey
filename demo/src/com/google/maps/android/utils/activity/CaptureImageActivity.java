package com.google.maps.android.utils.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.maps.android.utils.adapter.GridViewAdapter;
import com.google.maps.android.utils.demo.R;

import java.util.ArrayList;
import java.util.Locale;
/*
This activity we are using to capture the images and display them on the screen using camera intent
*/
public class CaptureImageActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    public static String DuctId,selectedValue;
    private GridView gridView;
    private ImageButton btnSpeak;
    private String Speechvalue;
    TextToSpeech t1;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private GridViewAdapter gridAdapter;
    ImageItem item;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        Intent intent = getIntent();
        DuctId = intent.getStringExtra("AssetId");
        selectedValue = intent.getStringExtra("selectedValue");
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                item = (ImageItem) parent.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(CaptureImageActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure! Do you want to Delete ?").setPositiveButton("Yes", dialogClickListener1).setNegativeButton("No", dialogClickListener1).show();
            }
        });
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Button uploadButton = (Button) this.findViewById(R.id.fibereqpbutton);
        uploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CaptureImageActivity.this);
                alert.setTitle("Camera Upload");
                alert.setMessage("Image Uploaded ! You may close the application");
                alert.setPositiveButton("OK",null);
                alert.show();

            }
        });

    }
    private ArrayList<ImageItem> getData() {
        System.out.println(ItemList.imageItems.size());
        for (int i = 0; i < ItemList.imageItems.size(); i++) {
            ItemList.imageItem1.add(new ImageItem(ItemList.imageItems.get(i)));
        }
        ItemList.imageItems.clear();
        return ItemList.imageItem1;
    }


    DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    ItemList.imageItem1.remove(item);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;

            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case CAMERA_REQUEST:
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ItemList.imageItems.add(thumbnail);
                }
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
                            if (w.equals("alternate"))
                            {

                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Here are the Alternate Ducts for which you have been looking for!", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(4000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent i = new Intent(CaptureImageActivity.this, AlternateDucts.class);
                                            i.putExtra("id_key",DuctId);
                                            startActivity(i);
                                        }
                                    }
                                });

                            }

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
                            if (w.equals("work"))
                            {
                                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Here are the work orders for which you have been assigned!", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(5000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent i = new Intent(CaptureImageActivity.this, MyJob.class);
                                            startActivity(i);
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
    /**
     * Showing google speech input dialog
     * */
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
    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

}
