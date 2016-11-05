package com.example.arijit.loginapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MyListAdapter extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_list_adapter, container,
                false);

        String[] values = new String[]{"Message1", "Message2", "Message3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        return rootView;


    }
}

