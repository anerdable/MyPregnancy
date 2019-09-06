package com.paula.mypregnancy.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;

public class TrackPregnancyFragment extends Fragment {

    private Toolbar mToolbar;
    private Button mBabyButton, mMummyButton;
    private DueDate mDueDate;
    private ProgressBar mProgressBar;
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
        mProgressBar = view.findViewById(R.id.progressBar);
        mBabyButton = view.findViewById(R.id.baby);
        mBabyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) getActivity();
                ma.openBabyFragment();
            }
        });
        mMummyButton = view.findViewById(R.id.gallery);
        mMummyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) getActivity();
                ma.openMummyFragment();
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
        MainActivity ma = (MainActivity) getActivity();
        switch(item.getItemId()){
            case R.id.abort:
                ma.abortPregnancy();
                return true;
            case R.id.camera:
                ma.startCamera();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(DUE_DATE_PARCEL, mDueDate);
    }

    private void updateView() {
        int week = mDueDate.getPregnancyWeek();
        if (week == -1) {
            mWeekNoTextView.setText("Overdue");
        } else {
            mWeekNoTextView.setText(Integer.toString(mDueDate.getPregnancyWeek()));
        }
        mWeeksAndDaysTextView.setText(mDueDate.getWeeksAndDays());
        mDueDateTextView.setText(mDueDate.toString());
        mProgressBar.setProgress(getPercentage());
    }

    private int getPercentage(){
        switch(mDueDate.getPregnancyWeek()){
                case 0:
                    return 0;
                case 1:
                    return 3;
                case 2:
                    return 5;
                case 3:
                    return 8;
                case 4:
                    return 10;
                case 5:
                    return 13;
                case 6:
                    return 15;
                case 7:
                    return 18;
                case 8:
                    return 20;
                case 9:
                    return 23;
                case 10:
                    return 25;
                case 11:
                    return 28;
                case 12:
                    return 30;
                case 13:
                    return 33;
                case 14:
                    return 35;
                case 15:
                    return 38;
                case 16:
                    return 40;
                case 17:
                    return 43;
                case 18:
                    return 45;
                case 19:
                    return 48;
                case 20:
                    return 50;
                case 21:
                    return 53;
                case 22:
                    return 55;
                case 23:
                    return 58;
                case 24:
                    return 60;
                case 25:
                    return 63;
                case 26:
                    return 65;
                case 27:
                    return 68;
                case 28:
                    return 70;
                case 29:
                    return 73;
                case 30:
                    return 75;
                case 31:
                    return 78;
                case 32:
                    return 80;
                case 33:
                    return 83;
                case 34:
                    return 85;
                case 35:
                    return 88;
                case 36:
                    return 90;
                case 37:
                    return 93;
                case 38:
                    return 95;
                case 39:
                    return 98;
                case 40:
                    return 100;
                default:
                    return 100;
            }
    }
}
