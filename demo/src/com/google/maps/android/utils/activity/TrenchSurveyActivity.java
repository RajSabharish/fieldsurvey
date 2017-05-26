package com.google.maps.android.utils.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.maps.android.utils.demo.R;

public class TrenchSurveyActivity extends FragmentActivity implements DuctIdFragment.onDuctSelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trench_survey);

        if (findViewById(R.id.fragment_container)!=null){
            if (savedInstanceState !=null){
                return;
            }

            DuctIdFragment firstFragment = new DuctIdFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onDuctSelected(int position){

        DuctDetailsFragment detailsfrag = (DuctDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsfrag != null){

            detailsfrag.updateDetailsView(position);
        } else {

            DuctDetailsFragment newFragment = new DuctDetailsFragment();
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
