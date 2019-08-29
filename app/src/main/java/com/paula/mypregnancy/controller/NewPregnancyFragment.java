package com.paula.mypregnancy.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;
import java.util.Calendar;

public class NewPregnancyFragment extends Fragment implements View.OnClickListener{

    private Button mCalculateDueDate, mTrack;
    private DueDate mDueDate;
    private TextView mLastPeriodTextView, mDueDateTextView, mPeriodTitleTextView, mDueDateTitleTextView;
    private final static String TAG = "NewPregnancyFragment";
    public static final int REQUEST_CODE = 11;
    public static final String DUE_DATE_PARCEL = "om.paula.mypregnancy.model.DueDate";

    public NewPregnancyFragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_new_pregnancy, container, false);
        mLastPeriodTextView = view.findViewById(R.id.last);
        mDueDateTextView = view.findViewById(R.id.due);
        mPeriodTitleTextView = view.findViewById(R.id.periodTitle);
        mDueDateTitleTextView = view.findViewById(R.id.dueDateTitle);
        mCalculateDueDate = view.findViewById(R.id.calculateDueDate);
        mCalculateDueDate.setOnClickListener(this);
        mTrack = view.findViewById(R.id.track);
        mTrack.setVisibility(View.INVISIBLE);
        mTrack.setOnClickListener(this);
        if (mDueDate != null){
            displayDueDate();
        }
        return view;

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(DUE_DATE_PARCEL, mDueDate);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.calculateDueDate:
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(NewPregnancyFragment.this, REQUEST_CODE);
                newFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.track:
                MainActivity mMainActivity = (MainActivity) getActivity();
                mMainActivity.setDueDate(mDueDate);
                break;
        }
    }

    protected void setLastPeriod(int year, int month, int day){
        Calendar lastPeriod = Calendar.getInstance();
        lastPeriod.set(year, month, day);
        mDueDate = new DueDate(lastPeriod);
        displayDueDate();
    }

    protected void displayDueDate(){
        mPeriodTitleTextView.setText("First day of last period:");
        mLastPeriodTextView.setText(mDueDate.getLastPeriodString());
        mDueDateTitleTextView.setText("Estimated due date:");
        mDueDateTextView.setText(mDueDate.toString());
        mTrack.setVisibility(View.VISIBLE);
    }
}
