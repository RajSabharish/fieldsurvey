package com.google.maps.android.utils.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
/**
 * Created by varada.vamsi on 19/5/2017.
 */
public class CaptureImagePit extends AppCompatActivity {
    public static String pitId,pit_position,selectedValue;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image_pit);
        Bundle bundle = getIntent().getExtras();
        pitId = bundle.getString("pitid");
        pit_position = bundle.getString("pitposition");
        selectedValue = bundle.getString("selectedRadioIdText");
        System.out.println(pitId+"pitId"+pit_position+"inside the CaptureImagePitclass");
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.ugbutton);
        photoButton.setOnClickListener(new View.OnClickListener() {

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
                AlertDialog.Builder alert = new AlertDialog.Builder(CaptureImagePit.this);
                alert.setTitle("Upload");

                if (selectedValue.equals("Yes"))
                {
                    alert.setMessage("Image Uploaded");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }
                if (selectedValue.equals("No"))
                {
                    alert.setMessage("Image Uploaded! Do you want to find alternate Pits ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                }

            }
        });
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(CaptureImagePit.this, AlternatePits.class);
                    i.putExtra("pitId",pitId);
                    i.putExtra("pit_position",pit_position);
                    startActivity(i);
                    break;

            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
