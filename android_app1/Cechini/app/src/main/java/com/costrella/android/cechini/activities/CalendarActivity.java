package com.costrella.android.cechini.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.costrella.android.cechini.Constants;
import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.model.Week;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;
import com.costrella.android.cechini.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        final MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        mcv.state().edit()
                .setMinimumDate(CalendarDay.from(2016, 9, 1))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        mcv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
//        mcv.addDecorator(new DayViewFacade().setDaysDisabled(true));

        Call<List<Week>> callPersonWeeks = CechiniService.getInstance().getCechiniAPI().getPersonWeeks(PersonService.PERSON.getId());
//wypelnienie
//        callPersonWeeks.enqueue(new Callback<List<Week>>() {
//            @Override
//            public void onResponse(Call<List<Week>> call, Response<List<Week>> response) {
//                final int code = response.code();
//                if (code == 200) {
//                    List<Week> weeks = response.body();
//                    for(Week week : weeks){
//                        Call<List<Day>> callWeekDays = CechiniService.getInstance().getCechiniAPI().getWeekDays(week.getId());
//                        callWeekDays.enqueue(new Callback<List<Day>>() {
//                            @Override
//                            public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
//                                int code = response.code();
//                                if (code == 200) {
//                                    List<Day> days = response.body();
//                                    for(Day day: days){
//                                        mcv.setDateSelected(day.getDate(), true);
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<Day>> call, Throwable t) {
//
//                            }
//                        });
//                    }
//                    //FIXME czy na pewno tutaj?
//                    Intent intent = new Intent(getApplicationContext(), TrasowkaActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Week>> call, Throwable t) {
//
//            }
//        });

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            }
        });
//        mcv.selectRange();
        mcv.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, final @NonNull List<CalendarDay> dates) {
                final Week week = new Week();
                week.setName("Trasowka na: " + dates.size() + " dni");

                Snackbar snackbar = Snackbar
                        .make(widget, "Czy utworzyć trasówkę?", Snackbar.LENGTH_LONG)
                        .setAction("TAK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                week.setPerson(PersonService.PERSON);
                                final HashMap<CalendarDay, Boolean> tmpMap = new HashMap();
                                for (CalendarDay calendarDay : dates) {
                                    tmpMap.put(calendarDay, false);
                                }

                                Call<Week> callWeek = CechiniService.getInstance().getCechiniAPI().createWeek(week);
                                callWeek.enqueue(new Callback<Week>() {
                                    @Override
                                    public void onResponse(Call<Week> call, Response<Week> response) {
                                        int code = response.code();
                                        Toast.makeText(getApplicationContext(), "creating week... " + code, Toast.LENGTH_LONG).show();
                                        if (code == 201) {

                                            final Week week = response.body();
                                            final List<Day> days = new ArrayList<Day>();
                                            for (final CalendarDay calendarDay : dates) {
                                                Day day = new Day();
                                                day.setDate(calendarDay.getDate());
                                                day.setName(DateUtil.getDayName(calendarDay));
                                                day.setWeek(week);
                                                Set<Store> stores = new HashSet<>();
                                                stores.addAll(StoreService.STORES_LIST);
//                                                day.setStores(stores);
                                                days.add(day);

                                            }
                                            Call<List<Day>> callDays = CechiniService.getInstance().getCechiniAPI().createDay2(days);
                                            callDays.enqueue(new Callback<List<Day>>() {
                                                @Override
                                                public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
                                                    int code = response.code();
                                                    if (code == 201) {
                                                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                                                        Call<List<Day>> callDaysWeek = CechiniService.getInstance().getCechiniAPI().getWeekDays(week.getId());
                                                        callDaysWeek.enqueue(new Callback<List<Day>>() {
                                                            @Override
                                                            public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
                                                                int code = response.code();
                                                                if (code == 200) {
                                                                    Intent intent = new Intent(getApplicationContext(), DaysActivity.class);
//                                                                    intent.putExtra("DAYS", response.body().size());
                                                                    DayService.DAYS.addAll(response.body());
                                                                    startActivity(intent);

                                                                    //TESTOWO!!!!

                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<List<Day>> call, Throwable t) {

                                                            }
                                                        });
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "BAD1", Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<Day>> call, Throwable t) {
                                                    Toast.makeText(getApplicationContext(), "BAD2", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Week> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG, Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        });

                snackbar.setActionTextColor(Color.RED);
                snackbar.show();


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
