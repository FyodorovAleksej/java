package com.company.CommonUserPackage;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexey on 04.03.2017.
 * Class of checking date for refresh quota
 */
public class DateControll extends GregorianCalendar {
    /**
     * basic constructor, that get current date of computer
     */
    public DateControll(){
        super();
    }

    /**
     * constructor for create calendar for input date
     * @param year - new year
     * @param month - new month
     * @param day - new day
     */
    public DateControll(int year, int month, int day){
        super(year,month,day);
    }
    public static DateControll valueOf(String s){
        String[] strings = s.split(":");
        if (strings.length < 2) return null;
        return new DateControll(Integer.valueOf(strings[2]),Integer.valueOf(strings[1]),Integer.valueOf(strings[0]));
    }

    /**
     * Object method for transform date in String in format DAY:MONTH:YEAR
     * @return - the result of transform
     */
    public String toString(){
        String day = "";
        String month = "";
        String year = Integer.toString(this.get(YEAR));
        if (this.get(DAY_OF_MONTH) < 10){
            day = "0";
        }
        day += Integer.toString(this.get(Calendar.DAY_OF_MONTH));
        if (this.get(MONTH) < 10){
            month = "0";
        }
        month += Integer.toString(this.get(Calendar.MONTH));

        return (day + ":" + month + ":" + year);
    }

    /**
     * method that compare this date with input date
     * @param date - date, with that this date will compare
     * @return - true - if this date more than input date
     *          false - if this date less or equals than input date
     */
    public boolean moreThan(DateControll date){
        if (this.get(YEAR) > date.get(YEAR))
            return true;
        if (this.get(YEAR) < date.get(YEAR))
            return false;
        if (this.get(MONTH) > date.get(MONTH))
            return true;
        if (this.get(MONTH) < date.get(MONTH))
            return false;
        if (this.get(DATE) > date.get(DATE))
            return true;
        if (this.get(DATE) < date.get(DATE))
            return false;
        return false;
    }
}
