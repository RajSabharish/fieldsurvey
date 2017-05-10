package com.google.maps.android.utils.demo;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by raj.a.natarajan on 5/10/2017.
 */

public class PitIdFragment extends ListFragment{

    PitIdFragment.onPitSelectionListener mCallback;

    public interface onPitSelectionListener{
        public void onPitSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layout = android.R.layout.simple_list_item_activated_1;

        setListAdapter(new ArrayAdapter<String>(getActivity(), layout,Ipsum.PitId));
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager().findFragmentById(R.id.pit_details_fragment)!= null){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            mCallback = (PitIdFragment.onPitSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must Implement OnPitSelectionListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        mCallback.onPitSelected(position);
        getListView().setItemChecked(position, true);
    }
}
