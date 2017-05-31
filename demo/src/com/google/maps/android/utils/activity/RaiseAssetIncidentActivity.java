package com.google.maps.android.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.maps.android.utils.demo.R;

public class RaiseAssetIncidentActivity extends AppCompatActivity {
    String AssetId;
    private Spinner IssueCategorySpinner;
    private static final int CAMERA_REQUEST = 1888;
    private static final String[]category = {"Asset Damaged beyond repair", "Asset Missing"};
    ImageButton camera_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_asset_incident);
        Intent intent = getIntent();
        AssetId = intent.getStringExtra("AssetID");
        EditText areaCodeText = (EditText) this.findViewById(R.id.areaCodeText);
        EditText assetIdText = (EditText) this.findViewById(R.id.assetIdText);
        areaCodeText.setKeyListener(null);
        assetIdText.setKeyListener(null);
        areaCodeText.setText("3KGP-01");
        assetIdText.setText(AssetId);

        IssueCategorySpinner = (Spinner)this.findViewById(R.id.issueCategorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IssueCategorySpinner.setAdapter(adapter);

        camera_button = (ImageButton) this.findViewById(R.id.captureImageButton);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            camera_button.setImageBitmap(photo);
        }
    }
}
