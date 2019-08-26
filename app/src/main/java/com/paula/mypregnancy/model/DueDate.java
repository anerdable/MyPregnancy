package com.paula.mypregnancy.model;

import java.text.DateFormatSymbols;

public class DueDate {

    private int mDay, mMonth, mYear;

    public DueDate(int day, int month, int year){
        this.mDay = day;
        this.mMonth = month;
        this.mYear = year;
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
