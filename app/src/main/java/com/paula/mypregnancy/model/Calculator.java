package com.paula.mypregnancy.model;

/*
    * Calculator
    *
    * An implementation of a pregnancy tracker app.
    * Development of mobile applications
    * Umeå Universitet, summer course 2019
    *
    * Paula D'Cruz
    *
    * This is one of the model classes, a calculator that is used to find solutions to all
    * mathematical problems that may occur when developing a tracker app.
    *
 */

import java.util.Calendar;

public class Calculator {

    /**
     * calculateDueDate
     *
     * this method calculates the user's due date, based on a normal pregnancy that lasts 280 days.
     *
     * @param lastPeriod takes in a calendar object of the user's last tracked period date,
     *                   to base the calculation of the due date from.
     * @return the newly calculated dueDate in the form of a Calendar object.
     */

    public static Calendar calculateDueDate(Calendar lastPeriod) {
        long inMillis = lastPeriod.getTimeInMillis();
        Calendar dueDate = Calendar.getInstance();
        dueDate.setTimeInMillis(inMillis);
        dueDate.add(Calendar.DATE, 280);
        return dueDate;
    }


    /**
     * calculateDaysLeft
     *
     * this method returns how many days left of pregnancy the user has. It was planned to be used
     * in a view but has ended up simply working as a helper function.
     *
     * @param dueDate takes in the due date calendar object to count how many days left there is, based on today's date.
     * @return an integer representing how many days are left.
     */
    public static int calculateDaysLeft(Calendar dueDate) {
        Calendar today = Calendar.getInstance();
        int daysLeft = 0;
        while (today.before(dueDate)) {
            today.add(Calendar.DAY_OF_MONTH, 1);
            daysLeft++;
        }
        return daysLeft;
    }

    /**
     * calculateDaysPregnant
     *
     * this method returns how many days the user has already been pregnant for. It was planned to be used in
     * a view but has ended up simply working as a helper function.
     *
     * @param dueDate takes in the due date calendar to subtract from, in the case of a normal pregnancy of 280 days.
     * @return an integer representing how many days the user has been pregnant for.
     */

    public static int calculateDaysPregnant(Calendar dueDate) {
        int daysLeft = calculateDaysLeft(dueDate);
        return 280 - daysLeft;
    }

    /**
     * calculateWeek
     *
     * this method calculates how many weeks pregnant the user is. it first calculates how many days the user has been pregnant
     * for, by using the helper method calculateDaysPregnant. After that, it calculates how many weeks that is by dividing
     * the days by 7.
     *
     * @param dueDate takes in the due date calendar object to count days from
     * @return an integer representing how many weeks pregnant the user is.
     */

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

    /**
     * calculateWeeksAndDays
     *
     * this method calculates how many weeks AND days the user is pregnant. First by using the calculateWeek-method from above,
     * then seeing if it was evenly divided by 7 or if there are any left over days.
     *
     * @param dueDate takes in the due date calendar object to count weeks and days from
     * @return a String that represents how many weeks and days pregnant the user is.
     */

    public static String calculateWeeksAndDays(Calendar dueDate){
        int week = calculateWeek(dueDate);
        int daysToRemove = week * 7;
        int day = calculateDaysPregnant(dueDate) - daysToRemove;
        return "(" + (week) + " + " + day + ")";
    }

}
