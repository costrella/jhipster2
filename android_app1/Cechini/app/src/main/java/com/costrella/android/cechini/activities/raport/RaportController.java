package com.costrella.android.cechini.activities.raport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.costrella.android.cechini.Constants;
import com.costrella.android.cechini.activities.progress.ProgressBar;
import com.costrella.android.cechini.activities.realm.RealmInit;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.model.Warehouse;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.NetworkService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Michal on 2017-09-12.
 */

public class RaportController {

    private Context context;
    private Warehouse selectedWarehouse;
    private RelativeLayout relativeLayout;
    private static RaportController instance;

    public static RaportController getInstance(Context context) {
        if (instance == null) {
            instance = new RaportController(context);
        }
        return instance;
    }

    public RaportController(Context context) {
        this.context = context;
    }

    private boolean internetAccess = true;

    private Store getStoreFromStoreProxy(Store proxy){
        Store store = new Store();
        store.setVisited(proxy.getVisited());
        store.setAddress(proxy.getAddress());
        store.setComment(proxy.getComment());
        store.setDays(proxy.getDays());
        store.setDescription(proxy.getDescription());
        store.setId(proxy.getId());
        store.setName(proxy.getName());
        store.setNumber(proxy.getNumber());
        store.setPerson(proxy.getPerson());
        return store;
    }

    public void createRaport(Warehouse selectedWarehouse, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, EditText editText,
                             EditText z_a, EditText z_b, EditText z_c, EditText z_d, EditText z_e, EditText z_f, EditText z_g, EditText z_h,
                             final View scroolview, final View progressView, RelativeLayout relativeLayout
    ) {
        this.selectedWarehouse = selectedWarehouse;
        this.relativeLayout = relativeLayout;

        if (valid()) {
            ProgressBar.showProgress(true, context, scroolview, progressView);
            RealmInit.realm.beginTransaction();

            Raport raport = getObject();
            if (selectedWarehouse != null) {
                raport.setWarehouse(internetAccess ? selectedWarehouse : RealmInit.realm.copyToRealm(selectedWarehouse));
            }
            if (DayService.selectedDay != null)
                raport.setDay(DayService.selectedDay);
            if (bitmap1 != null) {
                raport.setFoto1(getImage(bitmap1));
            }
            if (bitmap2 != null) {
                raport.setFoto2(getImage(bitmap2));
            }
            if (bitmap3 != null) {
                raport.setFoto3(getImage(bitmap3));
            }

            raport.setDescription(editText.getText().toString());

            raport.setPerson(internetAccess ? PersonService.PERSON : RealmInit.realm.copyToRealm(PersonService.PERSON));
            Store tmp = internetAccess ? StoreService.STORE : RealmInit.realm.copyToRealm(StoreService.STORE);
            Store tmp1 = getStoreFromStoreProxy(tmp);
            raport.setStore(tmp1);
            raport.setZ_a(getInt(z_a));
            raport.setZ_b(getInt(z_b));
            raport.setZ_c(getInt(z_c));
            raport.setZ_d(getInt(z_d));
            raport.setZ_e(getInt(z_e));
            raport.setZ_f(getInt(z_f));
            raport.setZ_g(getInt(z_g));
            raport.setZ_h(getInt(z_h));
            raport.setWarehousIdRealm(selectedWarehouse.getId());
            raport.setPersonIdRealm(PersonService.PERSON.getId());
            raport.setStoreIdRealm(StoreService.STORE.getId());

            if (internetAccess) {


                Call<Raport> callRaport = CechiniService.getInstance().getCechiniAPI().createRaport(raport);
                callRaport.enqueue(new Callback<Raport>() {
                    @Override
                    public void onResponse(Call<Raport> call, Response<Raport> response) {
                        int code = response.code();
                        ProgressBar.showProgress(false, context, scroolview, progressView);
                        if (code == 201) {
                            Toast.makeText(context, Constants.RAPORT_SUCCESS, Toast.LENGTH_LONG).show();
                            Store store = RealmInit.realm.where(Store.class).equalTo("id", StoreService.STORE.getId()).findFirst();
                            if (store != null) {
                                store.setVisited(true);
                                RealmInit.realm.insertOrUpdate(store);
                                RealmInit.realm.commitTransaction();
                                   return;

                            }
                        } else {
                            Toast.makeText(context, "Niepowodzenie wysłania raportu [oR] " + code, Toast.LENGTH_LONG).show();
                        }
                        RealmInit.realm.cancelTransaction();
                    }

                    @Override
                    public void onFailure(Call<Raport> call, Throwable t) {
                        RealmInit.realm.cancelTransaction();
                        ProgressBar.showProgress(false, context, scroolview, progressView);
                        showSnackBarToAddToQueue();
//                        Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG, Toast.LENGTH_LONG).show();

                    }
                });
            } else {
                showSnackBarToAddToQueue();
            }
        }
    }

    private boolean valid() {
        if (selectedWarehouse.getId() == null) {
            Toast.makeText(context, "Wybierz hutrownie ! / brak internetu", Toast.LENGTH_LONG).show();
            return false;
        }
        if (selectedWarehouse.getId().equals(99999L)) {
            Toast.makeText(context, "Wybierz hutrownie !", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showSnackBarToAddToQueue() {
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "Problem z internetem", Snackbar.LENGTH_LONG)
                .setAction("DODAJ DO KOLEJKI", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        finish();
                        Toast.makeText(context, "Dodano do kolejki, jak będziesz miał internet będziesz mógł wysłać raport", Toast.LENGTH_LONG).show();
                    }
                });
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    private Raport getObject() {
        if (NetworkService.getInstance().isNetworkAvailable(context)) {
            internetAccess = true;
            return new Raport();
        } else {
            internetAccess = false;
            return RealmInit.realm.createObject(Raport.class);
        }
    }

    private byte[] getImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }

    private int getInt(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return 0;
        } else {
            return ((Integer.parseInt(editText.getText().toString())));
        }
    }
}
