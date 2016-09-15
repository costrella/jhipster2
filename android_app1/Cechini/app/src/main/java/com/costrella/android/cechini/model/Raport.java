package com.costrella.android.cechini.model;

/**
 * Created by mike on 2016-09-15.
 */

import java.io.Serializable;



/**
 * A Raport.
 */
public class Raport implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String description;

    private byte[] foto1;

    private String foto1ContentType;

    private byte[] foto2;

    private String foto2ContentType;

    private byte[] foto3;

    private String foto3ContentType;

    private Person person;

    private Store store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Raport description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public Raport foto1(byte[] foto1) {
        this.foto1 = foto1;
        return this;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    public String getFoto1ContentType() {
        return foto1ContentType;
    }

    public Raport foto1ContentType(String foto1ContentType) {
        this.foto1ContentType = foto1ContentType;
        return this;
    }

    public void setFoto1ContentType(String foto1ContentType) {
        this.foto1ContentType = foto1ContentType;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public Raport foto2(byte[] foto2) {
        this.foto2 = foto2;
        return this;
    }

    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    public String getFoto2ContentType() {
        return foto2ContentType;
    }

    public Raport foto2ContentType(String foto2ContentType) {
        this.foto2ContentType = foto2ContentType;
        return this;
    }

    public void setFoto2ContentType(String foto2ContentType) {
        this.foto2ContentType = foto2ContentType;
    }

    public byte[] getFoto3() {
        return foto3;
    }

    public Raport foto3(byte[] foto3) {
        this.foto3 = foto3;
        return this;
    }

    public void setFoto3(byte[] foto3) {
        this.foto3 = foto3;
    }

    public String getFoto3ContentType() {
        return foto3ContentType;
    }

    public Raport foto3ContentType(String foto3ContentType) {
        this.foto3ContentType = foto3ContentType;
        return this;
    }

    public void setFoto3ContentType(String foto3ContentType) {
        this.foto3ContentType = foto3ContentType;
    }

    public Person getPerson() {
        return person;
    }

    public Raport person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Store getStore() {
        return store;
    }

    public Raport store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    @Override
    public String toString() {
        return "Raport{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", foto1='" + foto1 + "'" +
                ", foto1ContentType='" + foto1ContentType + "'" +
                ", foto2='" + foto2 + "'" +
                ", foto2ContentType='" + foto2ContentType + "'" +
                ", foto3='" + foto3 + "'" +
                ", foto3ContentType='" + foto3ContentType + "'" +
                '}';
    }
}
