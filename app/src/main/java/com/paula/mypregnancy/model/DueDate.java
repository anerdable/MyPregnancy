package com.paula.mypregnancy.model;

/*
 * DueDate
 *
 * An implementation of a pregnancy tracker app.
 * Development of mobile applications
 * Umeå Universitet, summer course 2019
 *
 * Paula D'Cruz
 *
 * This is one of the model classes, a Calendar object that represents a due date.
 *
 */

import android.os.Parcel;
import android.os.Parcelable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DueDate implements Parcelable{

    private Calendar mLastPeriod, mDueDate;
    private long mLastPeriodInMillis, mDueDateInMillis;

    /**
     * DueDate
     *
     * the constructor for the DueDate object.
     *
     * @param lastPeriod takes in the calendar object that represents the first day of the last period, to calculate the due date from.
     */

    public DueDate(Calendar lastPeriod){
        this.mLastPeriod = lastPeriod;
        this.mDueDate = Calculator.calculateDueDate(lastPeriod);
        this.mLastPeriodInMillis = mLastPeriod.getTimeInMillis();
        this.mDueDateInMillis = mDueDate.getTimeInMillis();
    }

    /**
     * DuDate
     *
     * protected constructor to recreate states using parcels for transient state storage.
     *
     * @param in contains the object that should be recreated
     */

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

    /**
     * Parcelable.Creator
     *
     * static method that calls the protected constructor to recreate state.
     *
     */

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

    /**
     * getPregnancyWeek
     *
     * method that calls the calculateWeek-method from the calculator
     *
     * @return an integer value representing how many weeks pregnant the user is, to be shown in a view.
     */

    public int getPregnancyWeek(){
        return Calculator.calculateWeek(mDueDate);
    }


    /**
     * getWeeksAndDays
     *
     * method that calls the calculateWeeksAndDays-method from the calculator
     *
     * @return a String value representing how many weeks and days pregnant the user is, to be shown in a view.
     */

    public String getWeeksAndDays(){
        return Calculator.calculateWeeksAndDays(mDueDate);
    }

    /**
     * getDueDateInMillis
     *
     * a method that is used when saving the date object to the internal storage
     *
     * @return a long value that represents the calendar object of the due date
     */

    public long getDueDateInMillis(){
        return mDueDateInMillis;
    }

    /**
     * toString
     *
     * @return a String representation of the due date object
     */

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        return dateFormat.format(mDueDate.getTime());
    }

    /**
     * getLastPeriodString
     *
     * @return a String representation of the last period object
     */

    public String getLastPeriodString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        return dateFormat.format(mLastPeriod.getTime());
    }

    /**
     * describeContents
     *
     * mandatory method for Parcelable, not used
     *
     * @return 0, not used
     */

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * writeToParcel
     *
     * this method converts the due date object and the last period object to longs, and then
     * writes them to a parcel
     *
     * @param dest this is where all data is being written to
     * @param flags
     */

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        mLastPeriodInMillis = mLastPeriod.getTimeInMillis();
        dest.writeLong(mLastPeriodInMillis);

        mDueDateInMillis = mDueDate.getTimeInMillis();
        dest.writeLong(mDueDateInMillis);
    }
}
