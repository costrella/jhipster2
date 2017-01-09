package com.costrella.android.cechini.model;

import io.realm.RealmObject;

/**
 * Created by mike on 2017-01-09.
 */
public class User extends RealmObject {
    private String login;
    private String pass;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
