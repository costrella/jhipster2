package com.howtodoinjava.demo.poi.cechini.model;

import java.io.Serializable;

/**
 * A Storegroup.
 */
public class Storegroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Storegroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Storegroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
