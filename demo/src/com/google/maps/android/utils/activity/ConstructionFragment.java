package com.google.maps.android.utils.activity;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.maps.android.utils.demo.R;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class ConstructionFragment extends Fragment {
    EditText SamCode;
    private ImageButton btnSpeak;
    private String Speechvalue;
    TextToSpeech t1;
    private final int REQ_CODE_SPEECH_INPUT = 100;


    public ConstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_construction, container, false);

        Button SurveyButton = (Button) view.findViewById(R.id.construction_submitbtn);
        SamCode = (EditText) view.findViewById(R.id.construction_samcodetext);
        btnSpeak = (ImageButton)view.findViewById(R.id.nlpButton);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        SurveyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    if ((SamCode.getText().toString().toUpperCase().trim()).equals("3KGP-01")) {
                        Intent myIntent = new Intent(getActivity(), ConstructionActivity.class);
                        getActivity().startActivity(myIntent);

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Failure");
                        alert.setMessage("No Details for the SAM found. Try a different SAM");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    }

            }
        });
        return view;
    }

    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            if (w.equals("alternate"))
                            {

                            }
                            if (w.equals("name"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("I'm Intelligent network field assistant Version 1.0", TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                });
                            }
                            if (w.equals("fibre"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Fiber Survey for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            Intent intent = new Intent(getActivity(), SurveyActivity.class);
                                            Bundle extras = new Bundle();
                                            extras.putBoolean("ugstate", false);
                                            extras.putBoolean("fiberstate", true);
                                            extras.putBoolean("copperstate", false);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            if (w.equals("copper"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Copper Survey for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            try {
                                                Thread.sleep(5000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(getActivity(), SurveyActivity.class);
                                            Bundle extras = new Bundle();
                                            extras.putBoolean("ugstate", false);
                                            extras.putBoolean("fiberstate", false);
                                            extras.putBoolean("copperstate", true);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            if (w.equals("underground")||w.equals("UG")||w.equals("under")||w.equals("ground"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Underground Survey for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            Intent intent = new Intent(getActivity(), SurveyActivity.class);
                                            Bundle extras = new Bundle();
                                            extras.putBoolean("ugstate", true);
                                            extras.putBoolean("fiberstate", false);
                                            extras.putBoolean("copperstate", false);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            if (w.equals("designation"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Network field engineer", TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                });

                            }
                            if (w.equals("designation"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Network field engineer", TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                });

                            }
                            if (w.equals("construction"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Construction view for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            Intent myIntent = new Intent(getActivity(), ConstructionActivity.class);
                                            getActivity().startActivity(myIntent);
                                        }
                                    }
                                });

                            }
                            if (w.equals("upkeep")||w.equals("maintenance"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Upkeep for area 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            Intent intent = new Intent(getActivity(), EquipmentUpkeep.class);
                                            startActivity(intent);
                                        }
                                    }
                                });

                            }
                            if (w.equals("fault")||w.equals("remediation"))
                            {
                                t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status != TextToSpeech.ERROR) {
                                            t1.setLanguage(Locale.UK);
                                            t1.speak("Fault remediation for 3KGP-01", TextToSpeech.QUEUE_FLUSH, null);
                                            Intent intent = new Intent(getActivity(), FaultRemediationActivity.class);
                                            startActivity(intent);
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
    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
    public void Speack()
    {
        t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    t1.speak("Do you want to see the alternate pits", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

}
