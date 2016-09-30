package com.mkyong.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mike on 2016-09-29.
 */
public class DateTest {

    public static void main(String[] args) {
        int iStartWeek=0;

        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH)+1;
        int year =  cal.get(Calendar.YEAR);
        System.out.format("\n-----\n"+day+" "+month +" "+ year+"\n");



        Calendar sDateCalendar = new GregorianCalendar();
//        sDateCalendar.set(2016, 0, 4);
        System.out.format(date.getYear() +" "+ date.getMonth()+" "+ date.getDay()+"\n");
        sDateCalendar.set(date.getYear(), date.getMonth(), date.getDay());
        System.out.format("sDateCalendar %tc\n", sDateCalendar);
        iStartWeek = sDateCalendar.getWeekYear();

        int tydzien = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println("iStartWeek : "+iStartWeek+ ":  "+tydzien);

        sDateCalendar.set(Calendar.WEEK_OF_YEAR, tydzien);
        System.out.format("sDateCalendar %tc\n", sDateCalendar);
    }
}
