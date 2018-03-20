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
 * A Storegroup.
 */
@Entity
@Table(name = "storegroup")
@Document(indexName = "storegroup")
public class Storegroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "storegroup")
    @JsonIgnore
    private Set<Store> stores = new HashSet<>();

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

    public Set<Store> getStores() {
        return stores;
    }

    public Storegroup stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Storegroup addStore(Store store) {
        stores.add(store);
        store.setStoregroup(this);
        return this;
    }

    public Storegroup removeStore(Store store) {
        stores.remove(store);
        store.setStoregroup(null);
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
        Storegroup storegroup = (Storegroup) o;
        if(storegroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, storegroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Storegroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
