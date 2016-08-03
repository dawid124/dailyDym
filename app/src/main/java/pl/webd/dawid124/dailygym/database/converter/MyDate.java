package pl.webd.dawid124.dailygym.database.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Java on 2016-05-10.
 */
public class MyDate {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String today;

    public static Date StringToDate (String dateStr){
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToString (Date dateDate){
        return dateFormat.format(dateDate);
    }

    public static String nextDayString (String dateStr){
        Date date = StringToDate(dateStr);
        date = addDays(date,1);
        return dateToString(date);
    }

    public static String prevDayString (String dateStr){
        Date date = StringToDate(dateStr);
        date = addDays(date,-1);
        return dateToString(date);
    }


    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static String addDays(String dateStr, int days)
    {
        Date date = StringToDate(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return dateToString(cal.getTime());
    }

    public static String getToday() {
        return today;
    }

    public static void setToday() {
        Date date = new Date();
        MyDate.today = dateToString(date);
    }

}