package com.costrella.android.cechini.services;

import android.util.Base64;

import com.costrella.android.cechini.CechiniAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mike on 2016-09-15.
 */
public class CechiniService {

    public static CechiniService instance = null;

    public static CechiniService getInstance() {
        if (instance == null) {
            instance = new CechiniService();
        }
        return instance;
    }

    private Gson gson;
    private Retrofit retrofit;

    public CechiniAPI getCechiniAPI() {
        if (gson == null) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeHierarchyAdapter(byte[].class,
                    new ByteArrayToBase64TypeAdapter())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(CechiniAPI.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit.create(CechiniAPI.class);
    }

    // Using Android's base64 libraries. This can be replaced with any base64 library.
    private class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }
}
