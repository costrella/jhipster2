package com.costrella.android.cechini.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.costrella.android.cechini.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        mcv.state().edit()
                .setFirstDayOfWeek(Calendar.WEDNESDAY)
                .setMinimumDate(CalendarDay.from(2016, 4, 3))
                .setMaximumDate(CalendarDay.from(2016, 5, 12))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        mcv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });
    }
}
