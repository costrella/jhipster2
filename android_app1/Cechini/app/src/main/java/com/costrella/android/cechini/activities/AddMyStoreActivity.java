package com.costrella.android.cechini.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.costrella.android.cechini.Constants;
import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.PersonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMyStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_store);
        final EditText storeName = (EditText) this.findViewById(R.id.storeName);
        final EditText cityName = (EditText) this.findViewById(R.id.cityName);
        final EditText streetName = (EditText) this.findViewById(R.id.streetName);
        final EditText descName = (EditText) this.findViewById(R.id.descName);
        Button addStoreSubmit = (Button) this.findViewById(R.id.addStoreSubmit);
        addStoreSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!storeName.getText().toString().isEmpty() && !storeName.getText().toString().equals("")) {
                    Store store = new Store();
                    store.setPerson(PersonService.PERSON);
                    store.setName(storeName.getText().toString());
                    store.setCity(cityName.getText().toString());
                    store.setStreet(streetName.getText().toString());
                    store.setDescription(descName.getText().toString());
                    //storegroup do zadnej nie przypiszemy celowo
                    Call<Store> createStoreCall = CechiniService.getInstance().getCechiniAPI().createStore(store);
                    createStoreCall.enqueue(new Callback<Store>() {
                        @Override
                        public void onResponse(Call<Store> call, Response<Store> response) {
                            int code = response.code();
                            if (code == 201) {
                                Toast.makeText(getApplicationContext(), "Dodano sklep: " + response.body().getName(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MyStoresActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG + code, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Store> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG, Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Nie wypełniłeś nazwy sklepu!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MyStoresActivity.class);
        startActivity(intent);
    }
}
