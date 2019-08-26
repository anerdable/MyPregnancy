package com.paula.mypregnancy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormatSymbols;

public class DueDate implements Parcelable {

    private int mDay, mMonth, mYear;

    public DueDate(int day, int month, int year){
        this.mDay = day;
        this.mMonth = month;
        this.mYear = year;
    }

    protected DueDate(Parcel in) {
        mDay = in.readInt();
        mMonth = in.readInt();
        mYear = in.readInt();
    }

    public static final Creator<DueDate> CREATOR = new Creator<DueDate>() {
        @Override
        public DueDate createFromParcel(Parcel in) {
            return new DueDate(in);
        }

        @Override
        public DueDate[] newArray(int size) {
            return new DueDate[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mDay);
        dest.writeInt(mMonth);
        dest.writeInt(mYear);
    }
}
