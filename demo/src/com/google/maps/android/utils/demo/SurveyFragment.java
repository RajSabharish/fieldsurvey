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

    private boolean nodeclickstate=false;
    private boolean ugclickstate=false;
    private boolean eqpclickstate=false;
    EditText SamCode;


    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey, container, false);
        final Button nodebutton = (Button) view.findViewById(R.id.nodebutton);
        final Button ugbutton = (Button) view.findViewById(R.id.ugbutton);
        final Button eqpbutton = (Button) view.findViewById(R.id.eqpbutton);

        nodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nodeclickstate=!nodeclickstate;
                if(nodeclickstate==true)
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
                eqpclickstate=!eqpclickstate;
                if(eqpclickstate==true)
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
                if((SamCode.getText().toString().trim()).equals("3KGP-01")){
                    startActivity(new Intent(getActivity(), GeoJsonDemoActivity.class));

                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Failure");
                    alert.setMessage("No Details for the SAM found. Try a different SAM");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }

            }
        });

        return view;
    }

}
