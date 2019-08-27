package com.paula.mypregnancy.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;

public class TrackPregnancyFragment extends Fragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private static final String DUE_DATE_PARCEL = "com.paula.mypregnancy.model.DueDate";
    private DueDate mDueDate;
    private TextView mPregnancyDay;
    private Context mContext;
    private final static String TAG = "TrackPregnancyFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){

        } else {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_track_pregnancy, container, false);

        return view;

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(null, null);
    }

    @Override
    public void onClick(View v) {

    }

}
