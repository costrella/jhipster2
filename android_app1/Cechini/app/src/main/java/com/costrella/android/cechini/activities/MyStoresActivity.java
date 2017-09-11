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
import android.util.Log;
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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStoresActivity extends ListActivity {
    private ArrayList<Store> listValues;
    private StoreAdapter adapter;
    private View mProgressView;
    private int index;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        mProgressView = findViewById(R.id.myStores_progress);
        DayService.selectedDay = null;
        listValues = new ArrayList<>();
        adapter = new StoreAdapter(this, listValues);
        TextView title = (TextView) findViewById(R.id.storesMainText);
        title.setText("Moje sklepy");

        FloatingActionButton store_synchro = (FloatingActionButton) findViewById(R.id.store_synchro);
        store_synchro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listValues.clear();
                realm.beginTransaction();
                realm.delete(Store.class);
                realm.commitTransaction();

                refreshStores();

            }
        });
    }


    private void initStoresToList(){
        listValues.clear();
        listValues.addAll(StoreService.STORES_LIST);
        setListAdapter(adapter);
    }

    private void refreshStores() {
        List<Store> personStores = realm.where(Store.class).findAll();
        if (personStores != null && !personStores.isEmpty()) {
            StoreService.STORES_LIST = personStores;
            initStoresToList();
        } else {
            showProgress(true);
            //pobieramy z resta jesli w bazie nie ma stores - przy wylogowaniu usunac stores z bazy !
            Call<List<Store>> call = CechiniService.getInstance().getCechiniAPI().getPersonStores(PersonService.PERSON.getId().toString());
            call.enqueue(new Callback<List<Store>>() {
                @Override
                public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                    final int code = response.code();
                    if (code == 200) {
                        List<Store> list = response.body();
                        realm.beginTransaction();
                        realm.insertOrUpdate(list);
                        realm.commitTransaction();
                        StoreService.STORES_LIST = list;
                        initStoresToList();
                    }
                    showProgress(false);

                }

                @Override
                public void onFailure(Call<List<Store>> call, Throwable t) {
                    showProgress(false);
                    Log.e("s", "f");
                }
            });
        }
    }

    private void realmInit() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.init(getApplicationContext());
        realm = Realm.getInstance(config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        realmInit();
        refreshStores();
        setSelection(index);
    }

    @Override
    protected void onListItemClick(ListView mList, View v, int position, long id) {
        index = mList.getFirstVisiblePosition();
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

            if (!store.getVisited()) {
                textView.setTextColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
            } else {
                textView.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
            }
            String txt = "";
            txt += store.getName() + ", ";
            if (store.getAddress() != null) {
                txt += store.getAddress().getCity() + ", ";
            }
            txt += store.getStreet();
            textView.setText(txt);
            return convertView;
        }
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WeeksActivity.class);
        startActivity(intent);
    }

}
