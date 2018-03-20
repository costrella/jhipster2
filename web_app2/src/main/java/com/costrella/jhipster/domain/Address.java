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
 * A Address.
 */
@Entity
@Table(name = "address")
@Document(indexName = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq")
    private Long id;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        if(address.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", city='" + city + "'" +
            '}';
    }
}
