package com.costrella.android.cechini.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.model.Week;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeeksActivity extends ListActivity {

    private TextView text;
    private ArrayList<Week> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks);

        listValues = new ArrayList<>();
        final WeekAdapter adapter = new WeekAdapter(this, listValues);

        Call<List<Store>> call = CechiniService.getInstance().getCechiniAPI().getPersonStores(PersonService.PERSON.getId().toString());
        call.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                List<Store> list = response.body();
                StoreService.STORES_LIST.clear();
                StoreService.STORES_LIST = list;

                Call<List<Week>> callPersonWeeks = CechiniService.getInstance().getCechiniAPI().getPersonWeeks(PersonService.PERSON.getId());
                callPersonWeeks.enqueue(new Callback<List<Week>>() {
                    @Override
                    public void onResponse(Call<List<Week>> call, Response<List<Week>> response) {

                        final int code = response.code();
                        if (code == 200) {
                            List<Week> weeks = response.body();
                            listValues.addAll(weeks);

                            text = (TextView) findViewById(R.id.weeksMainText);
                            setListAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Week>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("s", "f");
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.weeksAddWeek);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });


    }

    // when an item of the list is clicked
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        Week selectedItem = (Week) getListView().getItemAtPosition(position);
        text.setText("You clicked " + selectedItem.getName() + " at position " + position);

        DayService.DAYS.clear();
        Call<List<Day>> callDaysWeek = CechiniService.getInstance().getCechiniAPI().getWeekDays(selectedItem.getId());
        callDaysWeek.enqueue(new Callback<List<Day>>() {
            @Override
            public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
                int code = response.code();
                if (code == 200) {
                    DayService.DAYS.addAll(response.body());
                    Intent intent = new Intent(getApplicationContext(), DaysReviewActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Day>> call, Throwable t) {

            }
        });

    }

    public class WeekAdapter extends ArrayAdapter<Week> {
        public WeekAdapter(Context context, ArrayList<Week> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Week week = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_row_layout, parent, false);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.listText);
            tvName.setText(week.getId() + ", " + week.getName());
            return convertView;
        }
    }

}