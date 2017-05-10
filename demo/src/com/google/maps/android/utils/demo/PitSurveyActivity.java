package com.google.maps.android.utils.demo;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;


/**
 * Created by raj.a.natarajan on 5/10/2017.
 */

public class PitSurveyActivity extends FragmentActivity implements PitIdFragment.onPitSelectionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_survey);

        if (findViewById(R.id.pit_fragment_container)!=null){
            if (savedInstanceState !=null){
                return;
            }

            PitIdFragment firstFragment = new PitIdFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.pit_fragment_container, firstFragment).commit();
        }
    }

    public void onPitSelected(int position){

        PitDetailsFragment detailsfrag = (PitDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.pit_details_fragment);

        if (detailsfrag != null){

            detailsfrag.updateDetailsView(position);
        } else {

            PitDetailsFragment newFragment = new PitDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(DuctDetailsFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

}
