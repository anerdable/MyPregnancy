package com.paula.mypregnancy.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DueDate implements Parcelable{

    private Calendar mLastPeriod, mDueDate;
    private long mLastPeriodInMillis, mDueDateInMillis;
    private final static String TAG = "DueDate";

    public DueDate(Calendar lastPeriod){
        this.mLastPeriod = lastPeriod;
        this.mDueDate = Calculator.calculateDueDate(lastPeriod);
        this.mLastPeriodInMillis = mLastPeriod.getTimeInMillis();
        this.mDueDateInMillis = mDueDate.getTimeInMillis();
    }

    protected DueDate(Parcel in) {
        mLastPeriodInMillis = in.readLong();
        Calendar lastPeriod = Calendar.getInstance();
        lastPeriod.setTimeInMillis(mLastPeriodInMillis);
        mLastPeriod = lastPeriod;

        mDueDateInMillis = in.readLong();
        Calendar dueDate = Calendar.getInstance();
        dueDate.setTimeInMillis(mDueDateInMillis);
        mDueDate = dueDate;

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

    public Calendar getLastPeriod(){
        return mLastPeriod;
    }

    public Calendar getDueDate(){
        return mDueDate;
    }

    public int getDaysLeft(){
        return Calculator.calculateDaysLeft(mDueDate);
    }

    public int getPregnancyDay(){
        return Calculator.calculateDaysPregnant(mDueDate);
    }

    public int getPregnancyWeek(){
        return Calculator.calculateWeek(mDueDate);
    }

    public String getWeeksAndDays(){
        return Calculator.calculateWeeksAndDays(mDueDate);
    }

    public long getDueDateInMillis(){
        return mDueDateInMillis;
    }

    public long getLastPeriodInMillis(){
        return mLastPeriodInMillis;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        return dateFormat.format(mDueDate.getTime());
    }

    public String getLastPeriodString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        return dateFormat.format(mLastPeriod.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        mLastPeriodInMillis = mLastPeriod.getTimeInMillis();
        dest.writeLong(mLastPeriodInMillis);

        mDueDateInMillis = mDueDate.getTimeInMillis();
        dest.writeLong(mDueDateInMillis);
    }
}
