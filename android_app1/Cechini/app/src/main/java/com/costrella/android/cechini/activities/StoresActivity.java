package com.costrella.android.cechini.activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.StoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Klasa do której przechodzimy zaraz po kalendarzu!
 */
public class StoresActivity extends ListActivity {

    private TextView text;
    private ArrayList<Store> listValues;
    private Set<Store> listCheckedValues;
    private Day day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        listValues = new ArrayList<>();
        listCheckedValues = new HashSet<Store>();
        listCheckedValues.clear();
        listValues.addAll(StoreService.STORES_LIST);
        StoreAdapter adapter = new StoreAdapter(this, listValues);

        text = (TextView) findViewById(R.id.storesMainText);

        day = DayService.selectedDay;

        if(day.getStores() != null) {
            listCheckedValues.addAll(day.getStores());
        }

        text.setText(day.getName() + " " + new SimpleDateFormat("dd/MM/yyyy").format(day.getDate()));

        setListAdapter(adapter);

        final FloatingActionButton updateDay = (FloatingActionButton) findViewById(R.id.updateDay);
        updateDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Set<Store> daysSet = new HashSet<Store>(listCheckedValues);
                day.setStores(daysSet);
                Call<Day> updateDayCall = CechiniService.getInstance().getCechiniAPI().updateDay(day);
                updateDayCall.enqueue(new Callback<Day>() {
                    @Override
                    public void onResponse(Call<Day> call, Response<Day> response) {
                        int code = response.code();
                        if (code == 200) {
                            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Day> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }

    public class StoreAdapter extends ArrayAdapter<Store> {
        public StoreAdapter(Context context, ArrayList<Store> stores) {
            super(context, 0, stores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Store store = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.store_row_layout, parent, false);
            }
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.storeCheckBoxRow);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        listCheckedValues.add(store);
                    } else {
                        listCheckedValues.remove(store);
                    }
                }
            });
            checkBox.setChecked(listCheckedValues.contains(store));
            checkBox.setText(store.getName() + ", " + store.getCity() + ", " + store.getStreet());
//            if (day.getStores() != null) {
//                if (day.getStores().contains(store)) {
//                    checkBox.setChecked(true);
//                    listCheckedValues.add(store);
//                }
//            }
            return convertView;
        }
    }
}
