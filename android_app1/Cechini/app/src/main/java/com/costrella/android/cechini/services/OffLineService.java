package com.costrella.android.cechini.services;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.costrella.android.cechini.activities.WeeksActivity;
import com.costrella.android.cechini.model.Raport;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mike on 2017-01-11.
 */
public class OffLineService {
    private Realm realm;
    Context context;

    public OffLineService init(Context context) {
        this.context = context;
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
        return this;
    }

    public int getNumberOfNotSendRaports(){
        List<Raport> raports = realm.where(Raport.class).findAll();
        return raports.size();
    }

    public void sendRaportsOnline(final Activity activity) {
        realm.beginTransaction();
        List<Raport> realmResults = realm.where(Raport.class).findAll();
        Call<List<Raport>> callRaports = CechiniService.getInstance().getCechiniAPI().createRaportsList(realm.copyFromRealm(realmResults));
        callRaports.enqueue(new Callback<List<Raport>>() {
            @Override
            public void onResponse(Call<List<Raport>> call, Response<List<Raport>> response) {
                int code = response.code();
                if (code == 201) {
                    Toast.makeText(context, "Poprawnie wysłano "+response.body().size() +" raportów z kolejki", Toast.LENGTH_LONG).show();
                    realm.delete(Raport.class);
                    realm.commitTransaction();
                    if(activity instanceof WeeksActivity){
                        ((WeeksActivity) activity).offLine();
                    }
                }else{
                    realm.cancelTransaction();
                    Toast.makeText(context, "Nie wysłano raportów z kolejki ! [oR]", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Raport>> call, Throwable t) {
                Toast.makeText(context, "Nie wysłano raportów z kolejki ! Poczekaj na dobry zasięg ! [oF]", Toast.LENGTH_LONG).show();
                realm.cancelTransaction();
            }
        });

    }

}
