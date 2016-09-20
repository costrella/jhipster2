package com.costrella.android.cechini.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.costrella.android.cechini.model.Person;
import com.costrella.android.cechini.services.CechiniService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mike on 2016-09-16.
 */
public class LoginService {

    private static LoginService instance;
    public static LoginService getInstance(){
        if(instance == null){
            instance = new LoginService();
        }
        return instance;
    }

    private Person person; //zalogowana osoba
    private CechiniService cechiniService;

    public Person getPerson() {
        return person;
    }

    public void doLogin(final Context context, String login, String pass){
        person = null;
        Person personRequst = new Person();
        personRequst.setLogin(login);
        personRequst.setPass(pass);
        personRequst.setName(login);
        personRequst.setSurname(login);
        cechiniService = CechiniService.getInstance();
        Call<Person> call = cechiniService.getCechiniAPI().loginPerson(personRequst);

        //asynchronous call
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                int code = response.code();
                if (code == 201) {
                    person = response.body();
                    Toast.makeText(context, "LOGIN PERSON: " + person.getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("s", "d");

            }
        });
    }
}
