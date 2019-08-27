package com.paula.mypregnancy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class DueDate {

    private Calendar mDueDate;
    private int mDay, mMonth, mYear;

    public DueDate(Calendar dueDate){
        this.mDueDate = dueDate;
    }

    public Calendar getmDueDate(){
        return mDueDate;
    }

    public int getDay(){
        return mDay;
    }

    public int getMonth(){
        return mMonth;
    }

    public static String getMonthName(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    public int getYear(){
        return mYear;
    }

    public int getCountDown(){
        return 0;
    }

    public int getPregnancyDay(){
        return 0;
    }

    @Override
    public String toString() {
        return mDay + " " + getMonthName(mMonth) + ", "  + mYear;
    }

}
