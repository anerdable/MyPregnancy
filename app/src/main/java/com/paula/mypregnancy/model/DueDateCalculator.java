package com.paula.mypregnancy.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DueDateCalculator {

    private Calendar mLastPeriod;
    private String TAG ="DueDateCalculator";

    public DueDateCalculator(Calendar lastPeriod){
        this.mLastPeriod = lastPeriod;
    }

    public static String calculateDueDate(Calendar lastPeriod){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        Calendar dueDate = lastPeriod;
        dueDate.add(Calendar.DATE, 280);
        return dateFormat.format(dueDate.getTime());
    }

}
