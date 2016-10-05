package com.costrella.jhipster.domain;

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
@Entity
@Table(name = "store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private Set<Raport> raports = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "day_store",
               joinColumns = @JoinColumn(name="stores_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="days_id", referencedColumnName="ID"))
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

    public Store name(String name) {
        this.name = name;
        return this;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Store store = (Store) o;
        if(store.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, store.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
