package com.paula.mypregnancy.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.Calculator;
import com.paula.mypregnancy.model.DueDate;

import java.util.Calendar;

public class TrackPregnancyFragment extends Fragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button mBabyButton, mGalleryButton;
    private DueDate mDueDate;
    private TextView mDueDateTextView, mWeekNoTextView, mWeeksAndDaysTextView;
    private static final String DUE_DATE_PARCEL = "com.paula.mypregnancy.model.DueDate";
    private final static String TAG = "TrackPregnancyFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mDueDate = savedInstanceState.getParcelable(DUE_DATE_PARCEL);
        } else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_track_pregnancy, container, false);
        if (getArguments() != null) {
            mDueDate = getArguments().getParcelable("dueDate");
        }
        mToolbar = view.findViewById(R.id.action_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        mDueDateTextView = view.findViewById(R.id.dueDate);
        mWeekNoTextView = view.findViewById(R.id.weekNo);
        mWeeksAndDaysTextView = view.findViewById(R.id.weeksAndDays);
        mBabyButton = view.findViewById(R.id.baby);
        mBabyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) getActivity();
                ma.abortPregnancy();
            }
        });
        updateView();
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(DUE_DATE_PARCEL, mDueDate);
    }

    @Override
    public void onClick(View v) {

    }

    private void updateView(){
        int week = mDueDate.getPregnancyWeek();
        if (week == -1){
            mWeekNoTextView.setText("Overdue");
        } else {
            mWeekNoTextView.setText(Integer.toString(mDueDate.getPregnancyWeek()));
        }
        mWeeksAndDaysTextView.setText(mDueDate.getWeeksAndDays());
        mDueDateTextView.setText(mDueDate.toString());
    }

}
