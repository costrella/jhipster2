package com.costrella.android.cechini.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.activities.realm.RealmInit;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.model.Week;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.NetworkService;
import com.costrella.android.cechini.services.OffLineService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Main activity
public class MainActivity extends ListActivity {

    private View mProgressView;
    private TextView text;
    private ArrayList<Week> listValues;
    WeekAdapter adapter;
    OffLineService offLineService;
    FloatingActionButton queueButton;
    int sizeNotSendRaports;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks);
        realm = RealmInit.realm;
        mProgressView = findViewById(R.id.weeks_progress);
        listValues = new ArrayList<>();
        adapter = new WeekAdapter(this, listValues);

        //init stores
        if (StoreService.STORES_LIST.isEmpty()) {
            StoreService.STORES_LIST = realm.where(Store.class).findAll();
        }


        refresh();

        queueButton = (FloatingActionButton) findViewById(R.id.queueButton);

        offLine();
        final MainActivity _this = this;
        queueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(getListView(), "Masz " + sizeNotSendRaports + " niewysłanych raportów", Snackbar.LENGTH_LONG)
                        .setAction("WYŚLIJ TERAZ", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                offLineService.sendRaportsOnline(_this);
                            }
                        });
                snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
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
        FloatingActionButton myStores = (FloatingActionButton) findViewById(R.id.storesFloatingActionButton);
        myStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MyStoresActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton addStoreFloatingActionButton = (FloatingActionButton) findViewById(R.id.addStoreFloatingActionButton);
        addStoreFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddMyStoreActivity.class);
                startActivity(intent);
            }
        });

    }

    public void offLine() {
        if (NetworkService.getInstance().isNetworkAvailable(this)) {
            offLineService = new OffLineService();
            offLineService.init(getApplicationContext());
            sizeNotSendRaports = 0;
            sizeNotSendRaports = offLineService.getNumberOfNotSendRaports();
            if (sizeNotSendRaports != 0) {
                queueButton.setVisibility(View.VISIBLE);
            } else {
                queueButton.setVisibility(View.GONE);
            }
        } else {
            queueButton.setVisibility(View.GONE);
        }
    }

    // when an item of the list is clicked
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        Week selectedItem = (Week) getListView().getItemAtPosition(position);
//        text.setText("You clicked " + selectedItem.getName() + " at position " + position);

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


    private void refresh() {
        realm.beginTransaction();
        getWeeksFromRest();
        realm.cancelTransaction();

    }

    private void getWeeksFromRest() {
        Call<List<Week>> callPersonWeeks = CechiniService.getInstance().getCechiniAPI().getPersonWeeks(PersonService.PERSON.getId());
        callPersonWeeks.enqueue(new Callback<List<Week>>() {
            @Override
            public void onResponse(Call<List<Week>> call, Response<List<Week>> response) {

                final int code = response.code();
                if (code == 200) {
                    List<Week> weeks = response.body();
                    listValues.clear();
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
    public void onBackPressed() {
        if (getCurrentFocus() != null) {
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Czy chcesz się wylogować?", Snackbar.LENGTH_LONG)
                    .setAction("TAK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            back();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else {
            super.onBackPressed();
        }


    }

    private void back() {
        super.onBackPressed();
    }
}