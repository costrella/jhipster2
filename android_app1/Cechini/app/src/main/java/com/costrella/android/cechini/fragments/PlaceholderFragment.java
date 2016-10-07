package com.costrella.android.cechini.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.activities.dummy.DummyContent;
import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.PersonService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mike on 2016-10-06.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String date;
    private RecyclerView.ViewHolder viewHolder;
    public Map<Long, Store> fragmentStoresMap = new HashMap<>();
    //TUTAJ DODAJ LISTE SKLEPOW
    public PlaceholderFragment() {
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
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
//        if(date != null){
//            return rootView;
//        }
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        int i = getArguments().getInt(ARG_SECTION_NUMBER);

        //sortujemy ?DAYS
        Day fragmentDay = DayService.DAYS.get(i);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        date = sdf.format(fragmentDay.getDate());
        textView.setText(date);
        View recyclerView= rootView.findViewById(R.id.rv);
        setupRecyclerView((RecyclerView) recyclerView);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Snackbar.make(view, "fragmentStoresMap.size(): "+ fragmentStoresMap.size(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
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
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("s", "f");
            }
        });

    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        public final List<Store> mValues;

        public SimpleItemRecyclerViewAdapter(List<Store> items) {
            mValues = items;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content_day, parent, false);
            ViewHolder v = new ViewHolder(view);
            v.checkBox.setText("!!!!!!!!!!!!!!!");
            return v;
//            return  new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
//                holder.checkBox.setSelected(holder.mItem.isSelected());
            holder.checkBox.setText(mValues.get(position).getName());
            holder.checkBox.setText("????????????");
            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    holder.checkBox.setText("/////////////////");
                    mValues.get(position).setSelected(cb.isChecked());
//                        Toast.makeText(getContext(),
//                                "Clicked on Checkbox: " + cb.getText() +
//                                        " is " + cb.isChecked(),
//                                Toast.LENGTH_LONG).show();

                }
            });
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