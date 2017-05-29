package com.google.maps.android.utils.activity;

import android.app.Activity;
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
import android.widget.ImageView;

import com.google.maps.android.utils.adapter.GridViewAdapter;
import com.google.maps.android.utils.demo.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ShowPhotos extends Activity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    public static String DuctId,selectedValue;
    ImageItem item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("came inside the showPhotos class");
        try{
            Intent intent = getIntent();
            DuctId = intent.getStringExtra("ductIdKey");
            selectedValue = intent.getStringExtra("selectedValue");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_photos);
            gridView = (GridView) findViewById(R.id.gridView);
            Button uploadbutton = (Button) this.findViewById(R.id.Uploadbutton);
            uploadbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ShowPhotos.this);
                    alert.setTitle("Upload");

                    if (selectedValue.equals("Yes"))
                    {
                        alert.setMessage("Image Uploaded");
                        alert.setPositiveButton("OK",null);
                        alert.show();
                    }
                    if (selectedValue.equals("No"))
                    {
                        alert.setMessage("Images Uploaded! Do you want to find alternate Ducts ?").setPositiveButton("Yes", dialogClickListener1).setNegativeButton("No", dialogClickListener1).show();
                    }

                }
            });



            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
            gridView.setAdapter(gridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    item = (ImageItem) parent.getItemAtPosition(position);
                    AlertDialog.Builder alert = new AlertDialog.Builder(ShowPhotos.this);
                    alert.setTitle("Delete");
                    alert.setMessage("Are you sure! Do you want to Delete ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                }
            });
        }
        catch (Resources.NotFoundException e)
        {
            System.out.println(e);
        }

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
    DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(ShowPhotos.this, AlternateDucts.class);
                    i.putExtra("id_key",DuctId);
                    startActivity(i);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;

            }
        }
    };
}