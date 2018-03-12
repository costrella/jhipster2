package com.howtodoinjava.demo.poi.cechini.model;

import java.io.Serializable;

/**
 * A Address.
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String city;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }
}
