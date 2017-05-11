/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.maps.android.utils.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ViewGroup mListView;
    EditText SamCode;
    private boolean nodeclickstate=false;
    private boolean ugclickstate=false;
    private boolean eqpclickstate=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/titlefont.ttf");

        TextView tit = (TextView)v.findViewById(R.id.title10);
        tit.setText(this.getTitle());
        tit.setTypeface(tf);

        this.getActionBar().setCustomView(v);


        final Drawable str_on = ContextCompat.getDrawable(this,R.drawable.btn_star_big_on);
        final Drawable str_off = ContextCompat.getDrawable(this,R.drawable.btn_star_big_off);

        final ImageButton nodebutton = (ImageButton) findViewById(R.id.nodeimageButton);
        final ImageButton ugbutton = (ImageButton) findViewById(R.id.ugimageButton);
        final ImageButton eqpbutton = (ImageButton) findViewById(R.id.eqpimageButton);
        nodebutton.setImageDrawable(str_off);
        ugbutton.setImageDrawable(str_off);
        eqpbutton.setImageDrawable(str_off);

        nodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nodeclickstate=!nodeclickstate;
                if(nodeclickstate==true)
                {
                    nodebutton.setImageDrawable(str_on);
                }
                else {
                    nodebutton.setImageDrawable(str_off);
                }
            }
        });



        ugbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ugclickstate=!ugclickstate;
                if(ugclickstate==true)
                {
                    ugbutton.setImageDrawable(str_on);
                }
                else {
                    ugbutton.setImageDrawable(str_off);
                }
            }
        });



        eqpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eqpclickstate=!eqpclickstate;
                if(eqpclickstate==true)
                {
                    eqpbutton.setImageDrawable(str_on);
                }
                else {
                    eqpbutton.setImageDrawable(str_off);
                }
            }
        });

        Button SurveyButton = (Button) this.findViewById(R.id.button1);
        SamCode = (EditText)findViewById(R.id.editText);

        SurveyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if((SamCode.getText().toString().trim()).equals("3KGP-01")){
                    startActivity(new Intent(getApplicationContext(), GeoJsonDemoActivity.class));

                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Failure");
                    alert.setMessage("No Details for the SAM found. Try a different SAM");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }

            }
        });

    }

        /*mListView = (ViewGroup) findViewById(R.id.list);

        addDemo("Survey FIeld", GeoJsonDemoActivity.class);

    }

    private void addDemo(String demoName, Class<? extends Activity> activityClass) {
        Button b = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        b.setLayoutParams(layoutParams);
        b.setText(demoName);
        b.setTag(activityClass);
        b.setOnClickListener(this);
        mListView.addView(b);
    }

    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        startActivity(new Intent(this, activityClass));*/

}
