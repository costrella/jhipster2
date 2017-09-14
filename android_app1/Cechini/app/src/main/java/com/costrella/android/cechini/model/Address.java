package com.costrella.android.cechini.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Address.
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    private Long id;
    @Expose
    private String city;
    @Expose
    private Set<Store> stores = new HashSet<>();

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

    public Set<Store> getStores() {
        return stores;
    }

    public Address stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Address addStore(Store store) {
        stores.add(store);
        store.setAddress(this);
        return this;
    }

    public Address removeStore(Store store) {
        stores.remove(store);
        store.setAddress(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", city='" + city + "'" +
            '}';
    }
}
