package com.google.maps.android.utils.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.maps.android.utils.adapter.GridViewAdapter;
import com.google.maps.android.utils.demo.R;

import java.util.ArrayList;

/**
 * Created by varada.vamsi on 19/5/2017.
 */
public class CaptureImagePit extends AppCompatActivity {
    public static String pitId,pit_position,selectedValue;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    ImageItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("inside the capture pit class");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image_pit);
        Bundle bundle = getIntent().getExtras();
        pitId = bundle.getString("pitid");
        pit_position = bundle.getString("pitposition");
        selectedValue = bundle.getString("selectedRadioIdText");
        gridView = (GridView) findViewById(R.id.gridView1);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                item = (ImageItem) parent.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(CaptureImagePit.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure! Do you want to Delete ?").setPositiveButton("Yes", dialogClickListener1).setNegativeButton("No", dialogClickListener1).show();
            }
        });
        System.out.println(pitId+"pitId"+pit_position+"inside the CaptureImagePitclass");
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {

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
                alert.setTitle("Camera Upload");
                if (selectedValue.equals("Yes"))
                {
                    alert.setMessage("Image Uploaded ! You may close the application");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }
                if (selectedValue.equals("No"))
                {
                    alert.setMessage("Images Uploaded! Do you want to see the alternate pits").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
    }
    private ArrayList<ImageItem> getData() {
        System.out.println(ItemList.imageItems1.size());
        for (int i = 0; i < ItemList.imageItems1.size(); i++) {
            ItemList.imageItem2.add(new ImageItem(ItemList.imageItems1.get(i)));
        }
        ItemList.imageItems1.clear();
        return ItemList.imageItem2;
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
                case DialogInterface.BUTTON_NEGATIVE:
                    break;

            }
        }
    };
    DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    ItemList.imageItem2.remove(item);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;

            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ItemList.imageItems1.add(thumbnail);
        }
    }
}
