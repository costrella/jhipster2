package com.costrella.android.cechini;

import com.costrella.android.cechini.model.Day;
import com.costrella.android.cechini.model.Person;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.model.Warehouse;
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
    String ENDPOINT = "http://195.149.224.137:8080/cechini/api/";
//    String ENDPOINT = "http://192.168.0.12:8080/api/";

    @GET("stores/{storeId}")
    Call<Store> getStore(@Path("storeId") String storeId);

    @GET("warehouses/{warehouseId}")
    Call<Warehouse> getWarehouse(@Path("warehouseId") Long warehouseId);

    @POST("stores")
    Call<Store> createStore(@Body Store store);

    @GET("days/{dayId}")
    Call<Day> getDay(@Path("dayId") Long dayId);
//    @GET("dayStores/{dayId}")
//    Call<List<Store>> getDayStores(@Path("dayId") Long dayId);

    @GET("personStores/{personId}")
    Call<List<Store>> getPersonStores(@Path("personId") String personId);

    @GET("personWeeks/{personId}")
    Call<List<Week>> getPersonWeeks(@Path("personId") Long personId);

    @GET("weekDays/{weekId}")
    Call<List<Day>> getWeekDays(@Path("weekId") Long weekId);

    @GET("lastPersonStoreRaportMobi/{personId}/{storeId}")
    Call<Raport> getLastPersonStoreRaportMobi(@Path("personId") Long personId, @Path("storeId") Long storeId);

    @GET("people/{personId}")
    Call<Person> getPerson(@Path("personId") String personId);

    @GET("warehousesMobi")
    Call<List<Warehouse>> getWarehousesMobi();

    @POST("raportsList")
    Call<List<Raport>> createRaportsList(@Body List<Raport> raports);

    @POST("raports")
    Call<Raport> createRaport(@Body Raport raport);

    @POST("weeks")
    Call<Week> createWeek(@Body Week week);

    @POST("days")
    Call<Day> createDay(@Body Day day);

    @POST("updateDay")
    Call<Day> updateDay(@Body Day day);

    @POST("days2")
    Call <List<Day>> createDay2(@Body List<Day> day);

    @POST("daysList")
    Call<List<Day>> createListDay(@Body List<Day> day);

    @POST("loginPerson")
    Call<Person> loginPerson(@Body Person person);
}
