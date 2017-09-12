package com.costrella.android.cechini.services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mike on 2017-01-27.
 */
public class NetworkService {

    private static NetworkService instance;

    public static NetworkService getInstance(){
        if(instance == null){
            instance = new NetworkService();
        }
        return instance;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
