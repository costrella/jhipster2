package com.costrella.android.cechini.util;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mike on 2016-10-02.
 */
public class DateUtil {

    public static String getDayName(CalendarDay calendarDay) {
        Calendar calendar = calendarDay.getCalendar();
        Locale usersLocale = Locale.getDefault();
        DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
        String weekdays[] = dfs.getWeekdays();
        int dayName = calendar.get(Calendar.DAY_OF_WEEK);
        return weekdays[dayName];
    }
}
