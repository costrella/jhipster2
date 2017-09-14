package com.costrella.android.cechini.model;

/**
 * Created by mike on 2016-09-15.
 */

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * A Person.
 */
public class Person extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String surname;
    @Expose
    private String login;
    @Expose
    private String pass;
    @Expose
    @Ignore
    private Set<Store> stores = new HashSet<>();
    @Expose
    @Ignore
    private Set<Raport> raports = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Person surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Person stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Person addStore(Store store) {
        stores.add(store);
        store.setPerson(this);
        return this;
    }

    public Person removeStore(Store store) {
        stores.remove(store);
        store.setPerson(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<Raport> getRaports() {
        return raports;
    }

    public Person raports(Set<Raport> raports) {
        this.raports = raports;
        return this;
    }

    public Person addRaport(Raport raport) {
        raports.add(raport);
        raport.setPerson(this);
        return this;
    }

    public Person removeRaport(Raport raport) {
        raports.remove(raport);
        raport.setPerson(null);
        return this;
    }

    public void setRaports(Set<Raport> raports) {
        this.raports = raports;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", surname='" + surname + "'" +
                '}';
    }
}
