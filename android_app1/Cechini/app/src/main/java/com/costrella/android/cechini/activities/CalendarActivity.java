package com.costrella.android.cechini.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.costrella.android.cechini.Constants;
import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Week;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.PersonService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        mcv.state().edit()
                .setMinimumDate(CalendarDay.from(2016, 9, 1))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        mcv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });
//        mcv.selectRange();
        mcv.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                final Week week = new Week();
                Set<Day> days = new HashSet<>();
                for (CalendarDay calendarDay : dates) {
                    Day day = new Day();
                    day.setDate(calendarDay.getDate());
                    Calendar calendar = calendarDay.getCalendar();
                    Locale usersLocale = Locale.getDefault();
                    DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
                    String weekdays[] = dfs.getWeekdays();
                    int dayName = calendar.get(Calendar.DAY_OF_WEEK);
                    day.setName(weekdays[dayName]);
                    days.add(day);
                }
                week.setName("Trasowka na: " + dates.size() + " dni");
                week.setDays(days);
                week.setPerson(PersonService.PERSON);
                Call<Week> callWeek = CechiniService.getInstance().getCechiniAPI().createWeek(week);
                callWeek.enqueue(new Callback<Week>() {
                    @Override
                    public void onResponse(Call<Week> call, Response<Week> response) {
                        int code = response.code();
                        Toast.makeText(getApplicationContext(), "creating week... " + code, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Week> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG, Toast.LENGTH_LONG).show();
                    }
                });

//                new AlertDialog.Builder(getApplicationContext())
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Potwierdź")
//                        .setMessage("Czy dodać trasówkę na: " + dates.size() + " dni")
//                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                                );
//
//                            }
//
//                        })
//                        .setNegativeButton("Nie", null)
//                        .show();
            }
        });

    }
}
