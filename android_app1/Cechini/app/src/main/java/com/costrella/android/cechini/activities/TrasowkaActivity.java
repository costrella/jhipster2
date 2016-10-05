package com.costrella.android.cechini.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.activities.dummy.DummyContent;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.PersonService;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrasowkaActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasowka);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                days = 0;
            } else {
                days = extras.getInt("DAYS");
            }
        } else {
            days = (int) savedInstanceState.getSerializable("DAYS");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trasowka, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private String date;
        public String getDate(){
            return date;
        }
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_trasowka, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            int i = getArguments().getInt(ARG_SECTION_NUMBER);

            //sortujemy ?DAYS
            Day fragmentDay = DayService.DAYS.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            date = sdf.format(fragmentDay.getDate());
            textView.setText(date);

            View recyclerView= rootView.findViewById(R.id.rv);
//            View recyclerView = findViewById(R.id.item_list);
//            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
//                    fragmentDay.

                }
            });


            return rootView;
        }

        private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
            Call<List<Store>> call = CechiniService.getInstance().getCechiniAPI().getPersonStores(PersonService.PERSON.getId().toString());
            call.enqueue(new Callback<List<Store>>() {
                @Override
                public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                    DummyContent.ITEMS.clear();
                    List<Store> list = response.body();
                    for (Store s : list) {
                        DummyContent.addItem(s);
                    }
                    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
                }

                @Override
                public void onFailure(Call<List<Store>> call, Throwable t) {
                    Log.e("s", "f");
                }
            });

        }

        public class SimpleItemRecyclerViewAdapter
                extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

            private final List<Store> mValues;

            public SimpleItemRecyclerViewAdapter(List<Store> items) {
                mValues = items;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_content_day, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(final ViewHolder holder, int position) {
                holder.mItem = mValues.get(position);
                holder.checkBox.setText(mValues.get(position).getName());
                holder.checkBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Store store = holder.mItem;
                        Toast.makeText(getContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
//                        store.setSelected(cb.isChecked());
                    }
                });
//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, ItemDetailActivity.class);
//                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());
//                        StoreService.STORE = holder.mItem;
//
//                        context.startActivity(intent);
//                    }
//                });
            }

            @Override
            public int getItemCount() {
                return mValues.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                public final View mView;
                public final TextView mIdView;
                public final TextView mContentView;
                public final CheckBox checkBox;
                public Store mItem;

                public ViewHolder(View view) {
                    super(view);
                    mView = view;
                    mIdView = (TextView) view.findViewById(R.id.id);
                    mContentView = (TextView) view.findViewById(R.id.content);
                    checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                }

                @Override
                public String toString() {
                    return super.toString() + " '" + mContentView.getText() + "'";
                }
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return days;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
