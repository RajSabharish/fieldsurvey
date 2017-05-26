package com.google.maps.android.utils.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.maps.android.utils.demo.R;


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
        View view = inflater.inflate(R.layout.survey_fragment, container, false);
        Button ugbutton = (Button) view.findViewById(R.id.ugbutton);
        Button aerialbutton = (Button) view.findViewById(R.id.aerialbutton);
        Button copperbutton = (Button) view.findViewById(R.id.copperbutton);
        Button fiberbutton = (Button) view.findViewById(R.id.fiberbutton);


        aerialbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("AERIAL SURVEY");
                alertDialog.setMessage("Enter SAM CODE");

                final EditText input = new EditText(getActivity());
                input.setMaxLines(1);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String samcode = input.getText().toString();
                                if ((samcode.trim()).equals("3KGP-01")) {
                                    Toast.makeText(getActivity(),"No Aerial network data found for this SAM. Please select a different survey", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(),"No details found for this SAM.. Enter a different SAM Code", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });

        ugbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("UG SURVEY");
                alertDialog.setMessage("Enter SAM CODE");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String samcode = input.getText().toString();
                                if ((samcode.trim()).equals("3KGP-01")) {
                                    Intent intent = new Intent(getActivity(), SurveyActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putBoolean("ugstate", true);
                                    extras.putBoolean("fiberstate", false);
                                    extras.putBoolean("copperstate", false);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(),"No details found for this SAM.. Enter a different SAM Code", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });

        copperbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("COPPER EQUIPMENT SURVEY");
                alertDialog.setMessage("Enter SAM CODE");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String samcode = input.getText().toString();
                                if ((samcode.trim()).equals("3KGP-01")) {
                                    Intent intent = new Intent(getActivity(), SurveyActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putBoolean("ugstate", false);
                                    extras.putBoolean("fiberstate", false);
                                    extras.putBoolean("copperstate", true);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(),"No details found for this SAM.. Enter a different SAM Code", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });

        fiberbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("FIBER EQUIPMENT SURVEY");
                alertDialog.setMessage("Enter SAM CODE");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String samcode = input.getText().toString();
                                if ((samcode.trim()).equals("3KGP-01")) {
                                    Intent intent = new Intent(getActivity(), SurveyActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putBoolean("ugstate", false);
                                    extras.putBoolean("fiberstate", true);
                                    extras.putBoolean("copperstate", false);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(),"No details found for this SAM.. Enter a different SAM Code", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });


    return view;
    }
}

