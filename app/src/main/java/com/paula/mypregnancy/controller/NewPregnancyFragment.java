package com.paula.mypregnancy.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.Calculator;
import com.paula.mypregnancy.model.DueDate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewPregnancyFragment extends Fragment implements View.OnClickListener{

    private Button mCalculateDueDate, mTrack;
    private Toolbar mToolbar;
    private DueDate mDueDate;
    private Calendar mTemp;
    private TextView mLast, mDue, mPeriodTitle, mDueDateTitle;
    private Context mContext;
    private final static String TAG = "NewPregnancyFragment";
    public static final int REQUEST_CODE = 11;

    public NewPregnancyFragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_new_pregnancy, container, false);
        mLast = view.findViewById(R.id.last);
        mDue = view.findViewById(R.id.due);
        mPeriodTitle = view.findViewById(R.id.periodTitle);
        mDueDateTitle = view.findViewById(R.id.dueDateTitle);

        mCalculateDueDate = view.findViewById(R.id.calculateDueDate);
        mCalculateDueDate.setOnClickListener(this);
        mTrack = view.findViewById(R.id.track);
        mTrack.setVisibility(View.INVISIBLE);
        mTrack.setOnClickListener(this);
        return view;

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(null, null);
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
                mDueDate = new DueDate(mTemp);
                MainActivity ma = (MainActivity) getActivity();
                ma.trackPregnancy();
                break;
        }
    }

    protected void displayDueDate(int year, int month, int day){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        Calendar lastPeriod = Calendar.getInstance();
        lastPeriod.set(year, month, day);
        mPeriodTitle.setText("First day of last period:");
        mLast.setText(dateFormat.format(lastPeriod.getTime()));
        mDueDateTitle.setText("Estimated due date:");
        mTemp = Calculator.calculateDueDate(lastPeriod);
        mDue.setText(dateFormat.format(mTemp.getTime()));
        mTrack.setVisibility(View.VISIBLE);
    }
}
