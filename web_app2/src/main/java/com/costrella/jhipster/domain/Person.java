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
 * A Person.
 */
@Entity
@Table(name = "person")
@Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Size(min = 4)
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Size(min = 4)
    @Column(name = "pass", nullable = false)
    private String pass;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private Set<Raport> raports = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private Set<Week> weeks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Person surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public Person login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public Person pass(String pass) {
        this.pass = pass;
        return this;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Person stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Person addStore(Store store) {
        stores.add(store);
        store.setPerson(this);
        return this;
    }

    public Person removeStore(Store store) {
        stores.remove(store);
        store.setPerson(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<Raport> getRaports() {
        return raports;
    }

    public Person raports(Set<Raport> raports) {
        this.raports = raports;
        return this;
    }

    public Person addRaport(Raport raport) {
        raports.add(raport);
        raport.setPerson(this);
        return this;
    }

    public Person removeRaport(Raport raport) {
        raports.remove(raport);
        raport.setPerson(null);
        return this;
    }

    public void setRaports(Set<Raport> raports) {
        this.raports = raports;
    }

    public Set<Week> getWeeks() {
        return weeks;
    }

    public Person weeks(Set<Week> weeks) {
        this.weeks = weeks;
        return this;
    }

    public Person addWeek(Week week) {
        weeks.add(week);
        week.setPerson(this);
        return this;
    }

    public Person removeWeek(Week week) {
        weeks.remove(week);
        week.setPerson(null);
        return this;
    }

    public void setWeeks(Set<Week> weeks) {
        this.weeks = weeks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if(person.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", surname='" + surname + "'" +
            ", login='" + login + "'" +
            ", pass='" + pass + "'" +
            '}';
    }
}
