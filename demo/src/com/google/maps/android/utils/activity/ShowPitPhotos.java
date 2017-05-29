package com.google.maps.android.utils.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.maps.android.utils.adapter.GridViewAdapter;
import com.google.maps.android.utils.demo.R;

import java.util.ArrayList;

public class ShowPitPhotos extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    public static String selectedValue,pitId,pit_position;
    ImageItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pit_photos);
        gridView = (GridView) findViewById(R.id.gridView1);
        Intent intent = getIntent();
        pitId = intent.getStringExtra("pitId");
        pit_position = intent.getStringExtra("pit_position");
        selectedValue = intent.getStringExtra("selectedValue");
        Button uploadbutton = (Button) this.findViewById(R.id.Uploadbutton1);
        uploadbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ShowPitPhotos.this);
                alert.setTitle("Upload");

                if (selectedValue.equals("Yes"))
                {
                    alert.setMessage("Image Uploaded");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }
                if (selectedValue.equals("No"))
                {
                    alert.setMessage("Images Uploaded! Do you want to find alternate Pits ?").setPositiveButton("Yes", dialogClickListener1).setNegativeButton("No", dialogClickListener1).show();
                }

            }
        });



        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                item = (ImageItem) parent.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(ShowPitPhotos.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure! Do you want to Delete ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
            }
        });
    }


    private ArrayList<ImageItem> getData() {
        for (int i = 0; i < ItemList.imageItems1.size(); i++) {
            ItemList.imageItem2.add(new ImageItem(ItemList.imageItems1.get(i)));
            System.out.println("second list added");
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
    DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(ShowPitPhotos.this, AlternatePits.class);
                    i.putExtra("pitId",pitId);
                    i.putExtra("pit_position",pit_position);
                    startActivity(i);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;

            }
        }
    };
}