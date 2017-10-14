package com.costrella.android.cechini.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Store store;

    View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments().containsKey(ARG_ITEM_ID)) {
        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        store = StoreService.STORE;

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(store.getName());
        }
//        }
    }

    private void refresh() {
        Call<Raport> lastRaport = CechiniService.getInstance().getCechiniAPI().getLastPersonStoreRaportMobi(PersonService.PERSON.getId(), store.getId());
        lastRaport.enqueue(new Callback<Raport>() {
            @Override
            public void onResponse(Call<Raport> call, Response<Raport> response) {
                int code = response.code();
                if (code == 200) {
                    Raport raport = response.body();
                    ((TextView) rootView.findViewById(R.id.lastRaportTxt3)).setText(getString(raport.getDescription()));
                    ((TextView) rootView.findViewById(R.id.raport_date)).setText(String.valueOf(raport.getDate()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_a)).setText(String.valueOf(raport.getZ_a()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_b)).setText(String.valueOf(raport.getZ_b()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_c)).setText(String.valueOf(raport.getZ_c()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_d)).setText(String.valueOf(raport.getZ_d()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_f)).setText(String.valueOf(raport.getZ_f()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_g)).setText(String.valueOf(raport.getZ_g()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_h)).setText(String.valueOf(raport.getZ_h()));
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_e)).setText(String.valueOf(raport.getZ_e()));
                } else {
                    ((LinearLayout) rootView.findViewById(R.id.l0)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l1)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l2)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l3)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l4)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l5)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l6)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l7)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l8)).setVisibility(View.INVISIBLE);
                    ((LinearLayout) rootView.findViewById(R.id.l9)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaportTxt3)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.raport_date)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_a)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_b)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_c)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_d)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_f)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_g)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_h)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_z_e)).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.lastRaport_text)).setText("Brak ostatniego raportu");

                }
            }

            @Override
            public void onFailure(Call<Raport> call, Throwable t) {
                Log.d("a", "b");
            }
        });

        if (store != null) {
            String storeDetails = "";
            storeDetails += getString(store.getAddress() != null ? store.getAddress().getCity() : "") + ", ";
            storeDetails += getString(store.getStreet());

            ((TextView) rootView.findViewById(R.id.lastRaportTxt1)).setText(storeDetails);
            ((TextView) rootView.findViewById(R.id.lastRaport_opis)).setText(store.getDescription());
            String comment = getString(store.getComment());
            ((TextView) rootView.findViewById(R.id.lastRaport_komentarz)).setText(comment);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_detail, container, false);
        refresh();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private String getString(String string) {
        if (string == null) {
            return "";
        } else {
            return string;
        }

    }
}
