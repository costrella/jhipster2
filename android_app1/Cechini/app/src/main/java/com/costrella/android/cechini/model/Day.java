package com.costrella.android.cechini.model;

import java.io.Serializable;
import java.util.Date;

/**
 * A Day.
 */
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Date date;

    private Week week;

    private Store store;

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

    public Store getStore() {
        return store;
    }

    public Day store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
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
