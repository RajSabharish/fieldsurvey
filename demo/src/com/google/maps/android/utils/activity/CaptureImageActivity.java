package com.google.maps.android.utils.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.maps.android.utils.adapter.GridViewAdapter;
import com.google.maps.android.utils.demo.R;

import java.util.ArrayList;

public class CaptureImageActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    public static String DuctId,selectedValue;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    ImageItem item;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        Intent intent = getIntent();
        DuctId = intent.getStringExtra("AssetId");
        selectedValue = intent.getStringExtra("selectedValue");
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                item = (ImageItem) parent.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(CaptureImageActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure! Do you want to Delete ?").setPositiveButton("Yes", dialogClickListener1).setNegativeButton("No", dialogClickListener1).show();
            }
        });
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
                AlertDialog.Builder alert = new AlertDialog.Builder(CaptureImageActivity.this);
                alert.setTitle("Camera Upload");
                if (selectedValue.equals("Yes"))
                {
                    alert.setMessage("Image Uploaded ! You may close the application");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }
                if (selectedValue.equals("No"))
                {
                    alert.setMessage("Images Uploaded! Do you want to see the alternate Ducts").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                }
            }
        });

    }
    private ArrayList<ImageItem> getData() {
        System.out.println(ItemList.imageItems.size());
        for (int i = 0; i < ItemList.imageItems.size(); i++) {
            ItemList.imageItem1.add(new ImageItem(ItemList.imageItems.get(i)));
        }
        ItemList.imageItems.clear();
        return ItemList.imageItem1;
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
                    ItemList.imageItem1.remove(item);
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
            ItemList.imageItems.add(thumbnail);
        }
    }
}
