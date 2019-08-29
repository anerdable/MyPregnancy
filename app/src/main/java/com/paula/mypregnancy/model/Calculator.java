package com.paula.mypregnancy.model;

import android.util.Log;
import java.util.Calendar;

public class Calculator {

    private static final String TAG = "Calculator";

    public static Calendar calculateDueDate(Calendar lastPeriod) {
        long inMillis = lastPeriod.getTimeInMillis();
        Calendar dueDate = Calendar.getInstance();
        dueDate.setTimeInMillis(inMillis);
        dueDate.add(Calendar.DATE, 280);
        return dueDate;
    }

    public static int calculateDaysLeft(Calendar dueDate) {
        Calendar today = Calendar.getInstance();
        int daysLeft = 0;
        while (today.before(dueDate)) {
            today.add(Calendar.DAY_OF_MONTH, 1);
            daysLeft++;
        }
        return daysLeft;
    }

    public static int calculateDaysPregnant(Calendar dueDate) {
        int daysLeft = calculateDaysLeft(dueDate);
        return 280 - daysLeft;
    }

    public void calculateTimeLineSize() {

    }

    public static int calculateWeek(Calendar dueDate) {
        int daysPregnant = calculateDaysPregnant(dueDate);
        int week = daysPregnant / 7;
        if (week < 1){
            return 0;
        } else if (week > 39){
            return -1;
        }
        return week;
    }

    public static String calculateWeeksAndDays(Calendar dueDate){
        int week = calculateWeek(dueDate);
        int daysToRemove = week * 7;
        int day = calculateDaysPregnant(dueDate) - daysToRemove;
        return "(" + (week) + " + " + day + ")";
    }

}
