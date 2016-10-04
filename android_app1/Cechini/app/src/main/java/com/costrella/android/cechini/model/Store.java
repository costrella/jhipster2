package com.costrella.android.cechini.model;

/**
 * Created by mike on 2016-09-15.
 */

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Store.
 */
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    boolean selected = false;

    private Long id;

    private String name;

    private String city;

    private Person person;

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

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public Store city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Person getPerson() {
        return person;
    }

    public Store person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Raport> getRaports() {
        return raports;
    }

    public Store raports(Set<Raport> raports) {
        this.raports = raports;
        return this;
    }

    public Store addRaport(Raport raport) {
        raports.add(raport);
        raport.setStore(this);
        return this;
    }

    public Store removeRaport(Raport raport) {
        raports.remove(raport);
        raport.setStore(null);
        return this;
    }

    public void setRaports(Set<Raport> raports) {
        this.raports = raports;
    }


    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", city='" + city + "'" +
                '}';
    }
}
