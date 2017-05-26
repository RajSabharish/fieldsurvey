package com.google.maps.android.utils.activity;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.maps.android.utils.demo.R;


/**
 * Created by raj.a.natarajan on 5/4/2017.
 */

public class DuctIdFragment extends ListFragment {
    onDuctSelectionListener mCallback;

    public interface onDuctSelectionListener{
        public void onDuctSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layout = android.R.layout.simple_list_item_activated_1;

        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, ListItems.DuctId));
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager().findFragmentById(R.id.details_fragment)!= null){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            mCallback = (onDuctSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must Implement OnDuctSelectionListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        mCallback.onDuctSelected(position);
        getListView().setItemChecked(position, true);
    }
}
