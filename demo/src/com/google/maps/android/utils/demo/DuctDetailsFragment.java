package com.google.maps.android.utils.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * Created by raj.a.natarajan on 5/4/2017.
 */

public class DuctDetailsFragment extends Fragment{
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    private Spinner ownerSpinner;
    private static final String[]owners = {"NBN", "TELSTRA"};
    private static final int CAMERA_REQUEST1 = 1666;
    private static final int CAMERA_REQUEST2 = 1777;
    private static final int CAMERA_REQUEST3 = 1888;
    private static final int CAMERA_REQUEST4 = 1999;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        return inflater.inflate(R.layout.details_view, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            updateDetailsView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateDetailsView(mCurrentPosition);
        }
    }

    public void updateDetailsView(int position) {

        ownerSpinner = (Spinner)getActivity().findViewById(R.id.ownerSpinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,owners);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerSpinner.setAdapter(adapter);
        int temp=position * 8;
        EditText ductIdText = (EditText) getActivity().findViewById(R.id.ductIdText);
        EditText ductLengthText = (EditText) getActivity().findViewById(R.id.ductLengthText);
        EditText ductSizeText = (EditText) getActivity().findViewById(R.id.ductSizeText);
        EditText ductMaterialText = (EditText) getActivity().findViewById(R.id.ductMaterialText);
        EditText ductCapacityText = (EditText) getActivity().findViewById(R.id.ductCapacityText);
        EditText maxMandrelText = (EditText) getActivity().findViewById(R.id.maxMandrelText);
        EditText matrixCodeText = (EditText) getActivity().findViewById(R.id.matrixCodeText);
        ductIdText.setText(Ipsum.DuctId.get(position));
        ductIdText.setKeyListener(null);
        ductLengthText.setText(Ipsum.Details.get(temp));
        ductSizeText.setText(Ipsum.Details.get(++temp));
        ductMaterialText.setText(Ipsum.Details.get(++temp));
        ductCapacityText.setText(Ipsum.Details.get(++temp));
        maxMandrelText.setText(Ipsum.Details.get(++temp));
        matrixCodeText.setText(Ipsum.Details.get(++temp));
        if (Ipsum.Details.get(++temp).equals("TELSTRA")){
            ownerSpinner.setSelection(1);
        } else
        {
            ownerSpinner.setSelection(0);
        }

        Button uploadButton = (Button) getActivity().findViewById(R.id.nextButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Save");
                alert.setMessage("Are you sure to proceed?");
                alert.setPositiveButton("OK",dialogClickListener);
                alert.setNegativeButton("NO",dialogClickListener);
                alert.show();

            }
        });
        mCurrentPosition = position;
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent myIntent = new Intent(getActivity(), CaptureImageActivity.class);
                    getActivity().startActivity(myIntent);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }



}
