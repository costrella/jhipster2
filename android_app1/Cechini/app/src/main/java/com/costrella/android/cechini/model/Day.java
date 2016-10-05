package com.costrella.android.cechini.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * A Day.
 */
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Date date;

    private Week week;

    private Set<Store> stores = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Day name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public Day date(Date date) {
        this.date = date;
        return this;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Week getWeek() {
        return week;
    }

    public Day week(Week week) {
        this.week = week;
        return this;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Day stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Day addStore(Store store) {
        stores.add(store);
        store.getDays().add(this);
        return this;
    }

    public Day removeStore(Store store) {
        stores.remove(store);
        store.getDays().remove(this);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", date='" + date + "'" +
                '}';
    }
}
