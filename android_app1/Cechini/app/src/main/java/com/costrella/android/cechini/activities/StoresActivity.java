package com.costrella.android.cechini.activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.StoreService;

import java.util.ArrayList;

public class StoresActivity extends ListActivity {

    private TextView text;
    private ArrayList<Store> listValues;
    private ArrayList<Store> listCheckedValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        listValues = new ArrayList<>();
        listCheckedValues = new ArrayList<>();
        listValues.addAll(StoreService.STORES_LIST);
        StoreAdapter adapter = new StoreAdapter(this, listValues);

        text = (TextView) findViewById(R.id.storesMainText);
        setListAdapter(adapter);
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.store_row_layout, parent, false);
            }
            // Lookup view for data population
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.storeCheckBoxRow);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        listCheckedValues.add(store);
                    }else {
                        listCheckedValues.remove(store);
                    }
                }
            });
//            TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
            // Populate the data into the template view using the data object
            checkBox.setText(store.getName());
//            tvHome.setText(day.hometown);
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
