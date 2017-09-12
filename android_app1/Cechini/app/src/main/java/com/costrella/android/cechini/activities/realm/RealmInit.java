package com.costrella.android.cechini.activities.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Michal on 2017-09-12.
 */

public class RealmInit {

    public static Realm realm;


    public static void init(Context context){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.init(context);
        realm = Realm.getInstance(config);
    }
}
