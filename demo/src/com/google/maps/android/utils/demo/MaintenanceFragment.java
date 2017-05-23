package com.google.maps.android.utils.demo;


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


public class MaintenanceFragment extends Fragment {

    private boolean eqpupkeepclickstate = false;
    private boolean faultremediationclickstate = false;
    EditText SamCode;

    public MaintenanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);
        Button fault_button = (Button) view.findViewById(R.id.fault_button);
        Button upkeep_button = (Button) view.findViewById(R.id.upkeep_button);

        fault_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("FAULT MAINTENANCE");
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
                                    Intent intent = new Intent(getActivity(), MaintenanceActivity.class);
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
    /*
        final Button eqpupkeepbutton = (Button) view.findViewById(R.id.eqpupkeepbutton);
        final Button faultremediationbutton = (Button) view.findViewById(R.id.faultremediationbutton);

        eqpupkeepbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eqpupkeepclickstate=!eqpupkeepclickstate;
                if(eqpupkeepclickstate==true)
                {
                    eqpupkeepbutton.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                    if(faultremediationclickstate==true){
                        faultremediationclickstate=!faultremediationclickstate;
                        faultremediationbutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    }
                }
                else {
                    eqpupkeepbutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
            }
        });

        faultremediationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faultremediationclickstate=!faultremediationclickstate;
                if(faultremediationclickstate==true)
                {
                    faultremediationbutton.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
                    if(eqpupkeepclickstate==true){
                        eqpupkeepclickstate=!eqpupkeepclickstate;
                        eqpupkeepbutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    }
                }
                else {
                    faultremediationbutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
            }
        });

        Button SubmitButton = (Button) view.findViewById(R.id.maintenance_submitbtn);
        SamCode = (EditText) view.findViewById(R.id.maintenance_samcodetext);

        SubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(((SamCode.getText().toString().trim()).equals("3KGP-01"))&&(faultremediationclickstate==true || eqpupkeepclickstate==true)){
                    Intent intent = new Intent(getActivity(), MaintenanceActivity.class);
                    Bundle extras = new Bundle();
                    extras.putBoolean("upkeepclickstate", eqpupkeepclickstate);
                    extras.putBoolean("remediationclickstate", faultremediationclickstate);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
                else if(faultremediationclickstate==false && eqpupkeepclickstate==false){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Failure");
                    alert.setMessage("Please select one of the options above");
                    alert.setPositiveButton("OK",null);
                    alert.show();
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

}*/
