package com.costrella.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

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
@Document(indexName = "store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "visited")
    private Boolean visited;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "description")
    private String description;

    @Lob
    @JsonIgnore
    @Column(name = "picture_01")
    private byte[] picture01;

    @Column(name = "picture_01_content_type")
    @JsonIgnore
    private String picture01ContentType;

    @Lob
    @JsonIgnore
    @Column(name = "picture_02")
    private byte[] picture02;

    @Column(name = "picture_02_content_type")
    @JsonIgnore
    private String picture02ContentType;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private Set<Raport> raports = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "store_day",
               joinColumns = @JoinColumn(name="stores_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="days_id", referencedColumnName="ID"))
    private Set<Day> days = new HashSet<>();

    @ManyToOne
    private Storegroup storegroup;

    @ManyToOne
    private Address address;

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

    public Boolean isVisited() {
        return visited;
    }

    public Store visited(Boolean visited) {
        this.visited = visited;
        return this;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public String getStreet() {
        return street;
    }

    public Store street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public Store number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public Store description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture01() {
        return picture01;
    }

    public Store picture01(byte[] picture01) {
        this.picture01 = picture01;
        return this;
    }

    public void setPicture01(byte[] picture01) {
        this.picture01 = picture01;
    }

    public String getPicture01ContentType() {
        return picture01ContentType;
    }

    public Store picture01ContentType(String picture01ContentType) {
        this.picture01ContentType = picture01ContentType;
        return this;
    }

    public void setPicture01ContentType(String picture01ContentType) {
        this.picture01ContentType = picture01ContentType;
    }

    public byte[] getPicture02() {
        return picture02;
    }

    public Store picture02(byte[] picture02) {
        this.picture02 = picture02;
        return this;
    }

    public void setPicture02(byte[] picture02) {
        this.picture02 = picture02;
    }

    public String getPicture02ContentType() {
        return picture02ContentType;
    }

    public Store picture02ContentType(String picture02ContentType) {
        this.picture02ContentType = picture02ContentType;
        return this;
    }

    public void setPicture02ContentType(String picture02ContentType) {
        this.picture02ContentType = picture02ContentType;
    }

    public String getComment() {
        return comment;
    }

    public Store comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Storegroup getStoregroup() {
        return storegroup;
    }

    public Store storegroup(Storegroup storegroup) {
        this.storegroup = storegroup;
        return this;
    }

    public void setStoregroup(Storegroup storegroup) {
        this.storegroup = storegroup;
    }

    public Address getAddress() {
        return address;
    }

    public Store address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
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
            ", visited='" + visited + "'" +
            ", street='" + street + "'" +
            ", number='" + number + "'" +
            ", description='" + description + "'" +
            ", picture01='" + picture01 + "'" +
            ", picture01ContentType='" + picture01ContentType + "'" +
            ", picture02='" + picture02 + "'" +
            ", picture02ContentType='" + picture02ContentType + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
