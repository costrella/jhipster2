package com.costrella.android.cechini.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.services.DayService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DaysActivity extends ListActivity {

    private TextView text;
    private ArrayList<Day> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        listValues = new ArrayList<>();
        listValues.addAll(DayService.DAYS);
// Create the adapter to convert the array to views
        DayAdapter adapter = new DayAdapter(this, listValues);

        String title="";
        text = (TextView) findViewById(R.id.storesMainText);

        if(!DayService.DAYS.isEmpty()){
            title += DayService.DAYS.get(0).getWeek().getName();
        }

        text.setText(title);
        // initiate the listadapter
//        ArrayAdapter<Day> myAdapter = new ArrayAdapter<String>(this,
//                R.layout.day_row_layout, R.id.listText, listValues);

        // assign the list adapter
        setListAdapter(adapter);

    }

    // when an item of the list is clicked
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        Day selectedItem = (Day) getListView().getItemAtPosition(position);
        DayService.selectedDay = selectedItem;

        Intent intent = new Intent(this, StoresActivity.class);
        startActivity(intent);
    }

    public class DayAdapter extends ArrayAdapter<Day> {
        public DayAdapter(Context context, ArrayList<Day> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Day day = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_row_layout, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.listText);
//            TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
            // Populate the data into the template view using the data object
            tvName.setText(day.getName() + " " + new SimpleDateFormat("dd/MM/yyyy").format(day.getDate()));
//            tvHome.setText(day.hometown);
            // Return the completed view to render on screen
            return convertView;
        }
    }

}