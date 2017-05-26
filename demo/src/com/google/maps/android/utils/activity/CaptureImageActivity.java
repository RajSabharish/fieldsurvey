package com.google.maps.android.utils.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.maps.android.utils.demo.R;

public class CaptureImageActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    public static String DuctId,selectedValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        Intent intent = getIntent();
        DuctId = intent.getStringExtra("ductIdKey");
        selectedValue = intent.getStringExtra("selectedValue");
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
                AlertDialog.Builder alert = new AlertDialog.Builder(CaptureImageActivity.this);
                alert.setTitle("Upload");
                if (selectedValue.equals("Yes"))
                {
                    alert.setMessage("Image Uploaded");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }
                if (selectedValue.equals("No"))
                {
                    alert.setMessage("Image Uploaded! Do you want to find alternate ducts ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
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
                    Intent i = new Intent(CaptureImageActivity.this, AlternateDucts.class);
                    i.putExtra("id_key",DuctId);
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
