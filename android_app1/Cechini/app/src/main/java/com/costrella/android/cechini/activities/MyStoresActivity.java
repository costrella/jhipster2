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
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.StoreService;

import java.util.ArrayList;

public class MyStoresActivity extends ListActivity {
    private ArrayList<Store> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        listValues = new ArrayList<>();
        listValues.addAll(StoreService.STORES_LIST);
        StoreAdapter adapter = new StoreAdapter(this, listValues);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
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
            TextView textView = (TextView) convertView.findViewById(R.id.listText);
            // Populate the data into the template view using the data object

            if(store.getVisited() == null){
                textView.setTextColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
            }else{
                textView.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
            }

            textView.setText(store.getName());
            return convertView;
        }
    }
}
