package com.google.maps.android.utils.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.maps.android.utils.dataset.DataFactory;
import com.google.maps.android.utils.demo.R;
import com.google.maps.android.utils.model.MyJobClass;

import java.util.ArrayList;
import java.util.List;

public class MyJob extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_job);
        init();
    }
    public void init() {
        List<MyJobClass> PresentJob = new ArrayList<>();
        PresentJob = DataFactory.createPresentJobList();
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("Date");
        tv0.setTextColor(Color.BLACK);
        tv0.setPadding(400,20,100,50);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("  Task  ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("  Area  ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("  Status  ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < PresentJob.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(PresentJob.get(i).getDate());
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.LEFT);
            t1v.setPadding(400,20,100,50);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(PresentJob.get(i).getArea());
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.LEFT);
            t2v.setPadding(10,10,100,10);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(PresentJob.get(i).getTask());
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.LEFT);
            t3v.setPadding(10,10,100,10);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(PresentJob.get(i).getStatus());
            t4v.setTextColor(Color.BLACK);
            t4v.setPadding(10,10,100,10);
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }
}
