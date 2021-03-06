package com.costrella.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Day.
 */
@Entity
@Table(name = "day")
@Document(indexName = "day")
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    private Week week;

    @ManyToMany
    @JoinTable(name = "day_store",
               joinColumns = @JoinColumn(name="days_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="stores_id", referencedColumnName="ID"))
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "day")
    @JsonIgnore
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

    public Set<Raport> getRaports() {
        return raports;
    }

    public Day raports(Set<Raport> raports) {
        this.raports = raports;
        return this;
    }

    public Day addRaport(Raport raport) {
        raports.add(raport);
        raport.setDay(this);
        return this;
    }

    public Day removeRaport(Raport raport) {
        raports.remove(raport);
        raport.setDay(null);
        return this;
    }

    public void setRaports(Set<Raport> raports) {
        this.raports = raports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Day day = (Day) o;
        if(day.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, day.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
