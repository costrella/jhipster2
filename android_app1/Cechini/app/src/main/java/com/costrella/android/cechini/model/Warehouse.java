package com.costrella.android.cechini.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * A Warehouse.
 */
public class Warehouse extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

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

    public Warehouse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Raport> getRaports() {
        return raports;
    }

    public Warehouse raports(Set<Raport> raports) {
        this.raports = raports;
        return this;
    }

    public Warehouse addRaport(Raport raport) {
        raports.add(raport);
        raport.setWarehouse(this);
        return this;
    }

    public Warehouse removeRaport(Raport raport) {
        raports.remove(raport);
        raport.setWarehouse(null);
        return this;
    }

    public void setRaports(Set<Raport> raports) {
        this.raports = raports;
    }


    @Override
    public String toString() {
        return name;
    }
}
