package com.costrella.android.cechini.model;

/**
 * Created by mike on 2016-09-15.
 */

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Person.
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String surname;

    private String login;

    private String pass;

    private Set<Store> stores = new HashSet<>();

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
