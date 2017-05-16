package com.google.maps.android.utils.demo;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private boolean fiberclickstate =false;
    private boolean ugclickstate=false;
    private boolean copperclickstate =false;
    EditText SamCode;


    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey, container, false);
        final Button nodebutton = (Button) view.findViewById(R.id.fibereqpbutton);
        final Button ugbutton = (Button) view.findViewById(R.id.ugbutton);
        final Button eqpbutton = (Button) view.findViewById(R.id.coppereqpbutton);


        nodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiberclickstate =!fiberclickstate;
                if(fiberclickstate ==true)
                {
                    nodebutton.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                }
                else {
                    nodebutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
            }
        });

        ugbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ugclickstate=!ugclickstate;
                if(ugclickstate==true)
                {
                    ugbutton.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                }
                else {
                    ugbutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
            }
        });

        eqpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copperclickstate =!copperclickstate;
                if(copperclickstate ==true)
                {
                    eqpbutton.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                }
                else {
                    eqpbutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
            }
        });

        Button SurveyButton = (Button) view.findViewById(R.id.survey_submitbtn);
        SamCode = (EditText) view.findViewById(R.id.survey_samcodetext);

        SurveyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (copperclickstate || ugclickstate || fiberclickstate) {
                    if ((SamCode.getText().toString().trim()).equals("3KGP-01")) {
                        Intent intent = new Intent(getActivity(), SurveyActivity.class);
                        Bundle extras = new Bundle();
                        extras.putBoolean("ugstate", ugclickstate);
                        extras.putBoolean("fiberstate", fiberclickstate);
                        extras.putBoolean("copperstate", copperclickstate);
                        intent.putExtras(extras);
                        startActivity(intent);


                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Failure");
                        alert.setMessage("No Details for the SAM found. Try a different SAM");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    }

                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Failure");
                    alert.setMessage("Select any one of the Work package to Survey");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            }
        });

        return view;
    }

}
