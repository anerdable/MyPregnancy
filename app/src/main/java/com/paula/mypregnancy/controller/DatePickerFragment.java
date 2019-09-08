package com.paula.mypregnancy.controller;

/*
 * DatePickerFragment
 *
 * An implementation of a pregnancy tracker app.
 * Development of mobile applications
 * Umeå Universitet, summer course 2019
 *
 * Paula D'Cruz
 *
 * A controller class that shown a Calendar view where the user can choose a date from.
 *
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String TAG = "DatePickerFragment";

    /**
     * onCreateDialog
     *
     * opens up a DatePickerDialog
     *
     * @param savedInstanceState saved state
     * @return the DatePickerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * onDateSet
     *
     * called when the user picks a date.
     *
     * @param view
     * @param year the year chosen by the user
     * @param month the month chosen by the user
     * @param day the day chosen by the user
     */

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        NewPregnancyFragment npf = (NewPregnancyFragment) getTargetFragment();
        npf.setLastPeriod(year, month, day);
    }

}
