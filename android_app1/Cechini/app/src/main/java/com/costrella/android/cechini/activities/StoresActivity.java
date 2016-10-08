package com.costrella.android.cechini.activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Store;

import java.util.ArrayList;

public class StoresActivity extends ListActivity {

    private TextView text;
    private ArrayList<Store> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        listValues = new ArrayList<>();
        StoreAdapter adapter = new StoreAdapter(this, listValues);

        text = (TextView) findViewById(R.id.mainText);

        Store d1 = new Store();
        d1.setName("store1");

        Store d2 = new Store();
        d2.setName("store2");

        listValues.add(d1);
        listValues.add(d2);
        setListAdapter(adapter);
    }

    public class StoreAdapter extends ArrayAdapter<Store> {
        public StoreAdapter(Context context, ArrayList<Store> stores) {
            super(context, 0, stores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Store day = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_row_layout, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.listText);
//            TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
            // Populate the data into the template view using the data object
            tvName.setText(day.getName());
//            tvHome.setText(day.hometown);
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
