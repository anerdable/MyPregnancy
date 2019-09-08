package com.paula.mypregnancy.controller;

/*
 * NewPregnancyFragment
 *
 * An implementation of a pregnancy tracker app.
 * Development of mobile applications
 * Umeå Universitet, summer course 2019
 *
 * Paula D'Cruz
 *
 * This is one of the controller classes. It controls the view where the user can set a new pregnancy to track.
 *
 */

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

public class NewPregnancyFragment extends Fragment {

    private Button mCalculateDueDate, mTrack;
    private DueDate mDueDate;
    private TextView mLastPeriodTextView, mDueDateTextView, mPeriodTitleTextView, mDueDateTitleTextView;
    private final static String TAG = "NewPregnancyFragment";
    public static final int REQUEST_CODE = 11;
    public static final String DUE_DATE_PARCEL = "om.paula.mypregnancy.model.DueDate";

    public NewPregnancyFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate
     *
     * Handles restoring state by receiving parcelable
     * data and extras when available.
     *
     * @param savedInstanceState Bundle: saved state
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mDueDate = savedInstanceState.getParcelable(DUE_DATE_PARCEL);
        } else {

        }
    }

    /**
     * onCreateView
     *
     * this method sets up the view that is shown if there is no current pregnancy logged to be tracked.
     * it is used to calculate the user's expected due date, and creates the button to save and track a pregnancy.
     *
     * @param inflater the layoutInflator object that is used to inflate view to the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_new_pregnancy, container, false);
        mLastPeriodTextView = view.findViewById(R.id.last);
        mDueDateTextView = view.findViewById(R.id.due);
        mPeriodTitleTextView = view.findViewById(R.id.periodTitle);
        mDueDateTitleTextView = view.findViewById(R.id.dueDateTitle);
        mCalculateDueDate = view.findViewById(R.id.calculateDueDate);
        mCalculateDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(NewPregnancyFragment.this, REQUEST_CODE);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        mTrack = view.findViewById(R.id.track);
        mTrack.setVisibility(View.INVISIBLE);
        mTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mMainActivity = (MainActivity) getActivity();
                mMainActivity.setDueDate(mDueDate);
            }
        });
        if (mDueDate != null){
            displayDueDate();
        }
        return view;

    }

    /**
     * onSaveInstanceState
     *
     * saves the due date object (if available) to transient storage in a bundle on configuration change such as rotation
     *
     * @param outState bundle in which to place saved state
     */

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(DUE_DATE_PARCEL, mDueDate);
    }

    /**
     * setLastPeriod
     *
     * method used when choosing a date in the DatePickerFragment, that represents the first day of the last period.
     *
     * @param year the year chosen in DatePickerFragment
     * @param month the month chosen in DatePickerFragment
     * @param day the day chosen in DatePickerFragment
     */

    protected void setLastPeriod(int year, int month, int day){
        Calendar lastPeriod = Calendar.getInstance();
        lastPeriod.set(year, month, day);
        mDueDate = new DueDate(lastPeriod);
        displayDueDate();
    }

    /**
     * displayDueDate
     *
     * this method calculates a due date based on the last period and sets a text view that is visible to the user.
     * It also activates the button that lets the user track the pregnancy, with the date shown in the view.
     *
     */

    protected void displayDueDate(){
        mPeriodTitleTextView.setText("First day of last period:");
        mLastPeriodTextView.setText(mDueDate.getLastPeriodString());
        mDueDateTitleTextView.setText("Estimated due date:");
        mDueDateTextView.setText(mDueDate.toString());
        mTrack.setVisibility(View.VISIBLE);
    }

}
