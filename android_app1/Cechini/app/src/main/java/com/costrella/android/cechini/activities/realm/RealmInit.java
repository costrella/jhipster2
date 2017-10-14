package com.costrella.android.cechini.activities.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Michal on 2017-09-12.
 */

public class RealmInit {

    public static Realm realm;


    public static void init(Context context) {
        if (realm == null) {
            Realm.init(context);
            RealmConfiguration config = new RealmConfiguration
                    .Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }
    }
}
