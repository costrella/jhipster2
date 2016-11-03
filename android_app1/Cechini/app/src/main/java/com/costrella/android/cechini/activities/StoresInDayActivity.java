package com.costrella.android.cechini.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.StoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoresInDayActivity extends ListActivity {

    private TextView text;
    private ArrayList<Store> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        listValues = new ArrayList<>();
        listValues.clear();
        final StoreAdapter adapter = new StoreAdapter(this, listValues);
        text = (TextView) findViewById(R.id.storesMainText);
        final Day day = DayService.selectedDay;
        text.setText(day.getName() + " " + new SimpleDateFormat("dd/MM/yyyy").format(day.getDate()));
        FloatingActionButton updateDay = (FloatingActionButton) findViewById(R.id.updateDay);
        updateDay.setVisibility(View.INVISIBLE);

        Call<Day> callDayStores = CechiniService.getInstance().getCechiniAPI().getDay(day.getId());
        callDayStores.enqueue(new Callback<Day>() {
            @Override
            public void onResponse(Call<Day> call, Response<Day> response) {
                int code = response.code();
                if (code == 200) {
                    Day day = response.body();
                    Set<Store> stores = day.getStores();
                    listValues.addAll(stores);
                    setListAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<Day> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        Store selectedItem = (Store) getListView().getItemAtPosition(position);
        StoreService.STORE = selectedItem;

        Intent intent = new Intent(this, ItemDetailActivity.class);
        startActivity(intent);
    }

    public class StoreAdapter extends ArrayAdapter<Store> {
        public StoreAdapter(Context context, ArrayList<Store> stores) {
            super(context, 0, stores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Store store = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_row_layout, parent, false);
            }
            // Lookup view for data population
            TextView textView = (TextView) convertView.findViewById(R.id.listText);
            // Populate the data into the template view using the data object
            textView.setText(store.getName() + ", " + store.getCity() + ", " + store.getStreet());
//            tvHome.setText(day.hometown);
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
