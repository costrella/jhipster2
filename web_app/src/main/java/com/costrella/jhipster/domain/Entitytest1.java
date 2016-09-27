package com.costrella.jhipster.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Entitytest1.
 */
@Entity
@Table(name = "entitytest_1")
public class Entitytest1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "test_1")
    private String test1;

    @Column(name = "string_123")
    private String string123;

    @Column(name = "data_1")
    private LocalDate data1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest1() {
        return test1;
    }

    public Entitytest1 test1(String test1) {
        this.test1 = test1;
        return this;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getString123() {
        return string123;
    }

    public Entitytest1 string123(String string123) {
        this.string123 = string123;
        return this;
    }

    public void setString123(String string123) {
        this.string123 = string123;
    }

    public LocalDate getData1() {
        return data1;
    }

    public Entitytest1 data1(LocalDate data1) {
        this.data1 = data1;
        return this;
    }

    public void setData1(LocalDate data1) {
        this.data1 = data1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entitytest1 entitytest1 = (Entitytest1) o;
        if(entitytest1.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entitytest1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entitytest1{" +
            "id=" + id +
            ", test1='" + test1 + "'" +
            ", string123='" + string123 + "'" +
            ", data1='" + data1 + "'" +
            '}';
    }
}
