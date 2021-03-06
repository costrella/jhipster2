package com.costrella.android.cechini.model;

/**
 * Created by mike on 2016-09-15.
 */

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * A Store.

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Store.
 */
public class Store extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private String name;

    @Ignore
    private Person person;

    @Ignore
    private Address address;

    @Ignore
    private Set<Raport> raports = new HashSet<>();

    @Ignore
    private Set<Day> days = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private boolean visited;

    private String street;

    private String number;

    private String description;

    private String comment;

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<Day> getDays() {
        return days;
    }

    public Store days(Set<Day> days) {
        this.days = days;
        return this;
    }

    public Store addDay(Day day) {
        days.add(day);
        day.getStores().add(this);
        return this;
    }

    public Store removeDay(Day day) {
        days.remove(day);
        day.getStores().remove(this);
        return this;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
