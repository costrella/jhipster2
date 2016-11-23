package com.costrella.android.cechini.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStoresActivity extends ListActivity {
    private ArrayList<Store> listValues;
    private StoreAdapter adapter;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        mProgressView = findViewById(R.id.myStores_progress);
        showProgress(true);
        DayService.selectedDay = null;
        listValues = new ArrayList<>();
        adapter = new StoreAdapter(this, listValues);
        FloatingActionButton updateDay = (FloatingActionButton) findViewById(R.id.updateDay);
        updateDay.setVisibility(View.INVISIBLE);
        TextView title = (TextView) findViewById(R.id.storesMainText);
        title.setText("Moje sklepy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
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

            textView.setText(store.getName() + ", " + store.getCity() + ", " + store.getStreet());
            return convertView;
        }
    }

    private void refresh(){
        Call<List<Store>> call = CechiniService.getInstance().getCechiniAPI().getPersonStores(PersonService.PERSON.getId().toString());
        call.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                List<Store> list = response.body();
                StoreService.STORES_LIST.clear();
                StoreService.STORES_LIST = list;
                listValues.addAll(list);
                setListAdapter(adapter);
                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

}
