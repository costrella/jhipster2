package com.costrella.jhipster.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Entitytest2.
 */
@Entity
@Table(name = "entitytest_2")
public class Entitytest2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "test_1")
    private byte[] test1;

    @Column(name = "test_1_content_type")
    private String test1ContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getTest1() {
        return test1;
    }

    public Entitytest2 test1(byte[] test1) {
        this.test1 = test1;
        return this;
    }

    public void setTest1(byte[] test1) {
        this.test1 = test1;
    }

    public String getTest1ContentType() {
        return test1ContentType;
    }

    public Entitytest2 test1ContentType(String test1ContentType) {
        this.test1ContentType = test1ContentType;
        return this;
    }

    public void setTest1ContentType(String test1ContentType) {
        this.test1ContentType = test1ContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entitytest2 entitytest2 = (Entitytest2) o;
        if(entitytest2.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entitytest2.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entitytest2{" +
            "id=" + id +
            ", test1='" + test1 + "'" +
            ", test1ContentType='" + test1ContentType + "'" +
            '}';
    }
}
