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
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private ViewGroup mListView;
    EditText SamCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
