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
public class ConstructionFragment extends Fragment {
    EditText SamCode;


    public ConstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_construction, container, false);

        Button SurveyButton = (Button) view.findViewById(R.id.construction_submitbtn);
        SamCode = (EditText) view.findViewById(R.id.construction_samcodetext);

        SurveyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    if ((SamCode.getText().toString().trim()).equals("3KGP-01")) {
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

}
