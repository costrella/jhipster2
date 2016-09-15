package com.costrella.android.cechini;

import com.costrella.android.cechini.model.Person;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.model.Store;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mike on 2016-09-15.
 */
public interface CechiniAPI {
    String ENDPOINT = "http://192.168.0.10:8080/api/";

    @GET("stores/{storeId}")
    Call<Store> getStore(@Path("storeId") String storeId);

    @GET("people/{personId}")
    Call<Person> getPerson(@Path("personId") String personId);

    @POST("raports")
    Call<Raport> createRaport(@Body Raport raport);
}
