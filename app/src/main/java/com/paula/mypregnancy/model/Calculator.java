package com.paula.mypregnancy.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Calculator {

    private String TAG ="Calculator";

    public static String calculateDueDate(Calendar lastPeriod){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        Calendar dueDate = lastPeriod;
        dueDate.add(Calendar.DATE, 280);
        return dateFormat.format(dueDate.getTime());
    }

    public static int calculateDaysLeft(Calendar dueDate){
        return Math.round(0 / (24 * 60 * 60 * 1000));
    }

    public static int calculateDaysPregnant(Calendar lastPeriod){
        return Math.round(0 / (24 * 60 * 60 * 1000));
    }

}
