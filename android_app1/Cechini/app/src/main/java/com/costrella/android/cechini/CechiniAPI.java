package com.costrella.android.cechini;

import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Person;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.model.Week;

import java.util.List;

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

    @GET("personStores/{personId}")
    Call<List<Store>> getPersonStores(@Path("personId") String personId);

    @GET("people/{personId}")
    Call<Person> getPerson(@Path("personId") String personId);

    @POST("raports")
    Call<Raport> createRaport(@Body Raport raport);

    @POST("weeks")
    Call<Week> createWeek(@Body Week week);

    @POST("days")
    Call<Day> createDay(@Body Day day);

    @POST("daysList")
    Call<List<Day>> createListDay(@Body List<Day> day);

    @POST("loginPerson")
    Call<Person> loginPerson(@Body Person person);
}
