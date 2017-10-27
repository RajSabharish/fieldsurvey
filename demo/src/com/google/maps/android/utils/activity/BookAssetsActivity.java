package com.google.maps.android.utils.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.google.maps.android.utils.demo.R;

/**
 * Created by raj.a.natarajan on 10/27/2017.
 */
/*
  In this class we are using material layout to display "SURVEY", "CONSTRUCTION", "MAINTENANCE" functionalities
*/

public class BookAssetsActivity extends Activity {
    private Spinner stageSpinner;
    private static final String[]stages = {"SURVEY", "CONSTRUCTION", "MAINTENANCE"};

    MyCustomAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_assets);
        stageSpinner = (Spinner)this.findViewById(R.id.stageSpinner);
        Button selectButton = (Button) findViewById(R.id.selectButton);
        final FrameLayout layout = (FrameLayout)findViewById(R.id.frame1);
        layout.setVisibility(View.INVISIBLE);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stages);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stageSpinner.setAdapter(adp);

        stageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int stageSelected=stageSpinner.getSelectedItemPosition();
                displayListView(stageSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



       // checkButtonClick();

    }

    private void displayListView(int stageSelected) {

        //Array list of countries
        ArrayList<String> assetsList_survey = new ArrayList<String>();
        assetsList_survey.add("Rodder");
        assetsList_survey.add("Vaccum");
        assetsList_survey.add("Barricade");
        assetsList_survey.add("Traffic Cones");
        assetsList_survey.add("Pit Opener Kit");
        assetsList_survey.add("Marking Tape");
        assetsList_survey.add("Safety Kit");
        assetsList_survey.add("Rodometer");
        assetsList_survey.add("Lime Powder");
        assetsList_survey.add("DIT Kit");

        ArrayList<String> assetsList_construction = new ArrayList<String>();
        assetsList_construction.add("HDD Machine");
        assetsList_construction.add("Digging Equipment");
        assetsList_construction.add("Duct Decoiler");
        assetsList_construction.add("Duct Cutter");
        assetsList_construction.add("Splicing Kit");

        ArrayList<String> assetsList_maintenance = new ArrayList<String>();
        assetsList_maintenance.add("OTDR");
        assetsList_maintenance.add("Splicer");
        assetsList_maintenance.add("Tape");
        assetsList_maintenance.add("Rod");

        if(stageSelected==0) {
            //create an ArrayAdaptar from the String Array
            dataAdapter = new MyCustomAdapter(this,
                    R.layout.assetslayout, assetsList_survey);
            ListView listView = (ListView) findViewById(R.id.listView1);
            // Assign adapter to ListView
            System.out.println("Survey!!!!!!!!!!");
            listView.setAdapter(dataAdapter);
        }
        else if(stageSelected==1) {
            System.out.println("Const!!!!!!!!!!");
            dataAdapter = new MyCustomAdapter(this,
                    R.layout.assetslayout, assetsList_construction);
            ListView listView = (ListView) findViewById(R.id.listView1);
            // Assign adapter to ListView

            listView.setAdapter(dataAdapter);
        }

        else if(stageSelected==2) {
            dataAdapter = new MyCustomAdapter(this,
                    R.layout.assetslayout, assetsList_maintenance);
            ListView listView = (ListView) findViewById(R.id.listView1);
            // Assign adapter to ListView
            System.out.println("Maint!!!!!!!!!!");
            listView.setAdapter(dataAdapter);
        }


       /* listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });*/

    }

    private class MyCustomAdapter extends ArrayAdapter<String> {

        private ArrayList<String> assetsList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> countryList) {
            super(context, textViewResourceId, countryList);
            this.assetsList = new ArrayList<String>();
            this.assetsList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.assetslayout, null);

                holder = new ViewHolder();
               // holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

             /*   holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });*/
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            String country = assetsList.get(position);
            System.out.println(country+"Goes in here !!!!!!");
           // holder.code.setText(" (" +  country.getCode() + ")");
           // holder.name.setText(country.getName());
           // holder.name.setChecked(country.isSelected());
            holder.name.setText(country);

            return convertView;

        }

    }

    /*private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Country> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Country country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }*/

}
