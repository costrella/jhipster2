package com.costrella.android.cechini.services;

import com.costrella.android.cechini.CechiniAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mike on 2016-09-15.
 */
public class CechiniService {

    public static CechiniService instance = null;

    public static CechiniService getInstance() {
        if(instance == null){
            instance = new CechiniService();
        }
        return instance;
    }

    public CechiniAPI getCechiniAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CechiniAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(CechiniAPI.class);
    }
}
