package com.costrella.jhipster.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Raport.
 */
@Entity
@Table(name = "raport")
@Document(indexName = "raport")
public class Raport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "foto_1")
    private byte[] foto1;

    @Column(name = "foto_1_content_type")
    private String foto1ContentType;

    @Lob
    @Column(name = "foto_2")
    private byte[] foto2;

    @Column(name = "foto_2_content_type")
    private String foto2ContentType;

    @Lob
    @Column(name = "foto_3")
    private byte[] foto3;

    @Column(name = "foto_3_content_type")
    private String foto3ContentType;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "z_a")
    private Integer z_a;

    @Column(name = "z_b")
    private Integer z_b;

    @Column(name = "z_c")
    private Integer z_c;

    @Column(name = "z_d")
    private Integer z_d;

    @Column(name = "z_e")
    private Integer z_e;

    @Column(name = "z_f")
    private Integer z_f;

    @Column(name = "z_g")
    private Integer z_g;

    @Column(name = "z_h")
    private Integer z_h;

    @ManyToOne
    @NotNull
    private Person person;

    @ManyToOne
    @NotNull
    private Store store;

    @ManyToOne
    private Day day;

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

    public LocalDate getDate() {
        return date;
    }

    public Raport date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getz_a() {
        return z_a;
    }

    public Raport z_a(Integer z_a) {
        this.z_a = z_a;
        return this;
    }

    public void setz_a(Integer z_a) {
        this.z_a = z_a;
    }

    public Integer getz_b() {
        return z_b;
    }

    public Raport z_b(Integer z_b) {
        this.z_b = z_b;
        return this;
    }

    public void setz_b(Integer z_b) {
        this.z_b = z_b;
    }

    public Integer getz_c() {
        return z_c;
    }

    public Raport z_c(Integer z_c) {
        this.z_c = z_c;
        return this;
    }

    public void setz_c(Integer z_c) {
        this.z_c = z_c;
    }

    public Integer getz_d() {
        return z_d;
    }

    public Raport z_d(Integer z_d) {
        this.z_d = z_d;
        return this;
    }

    public void setz_d(Integer z_d) {
        this.z_d = z_d;
    }

    public Integer getz_e() {
        return z_e;
    }

    public Raport z_e(Integer z_e) {
        this.z_e = z_e;
        return this;
    }

    public void setz_e(Integer z_e) {
        this.z_e = z_e;
    }

    public Integer getz_f() {
        return z_f;
    }

    public Raport z_f(Integer z_f) {
        this.z_f = z_f;
        return this;
    }

    public void setz_f(Integer z_f) {
        this.z_f = z_f;
    }

    public Integer getz_g() {
        return z_g;
    }

    public Raport z_g(Integer z_g) {
        this.z_g = z_g;
        return this;
    }

    public void setz_g(Integer z_g) {
        this.z_g = z_g;
    }

    public Integer getz_h() {
        return z_h;
    }

    public Raport z_h(Integer z_h) {
        this.z_h = z_h;
        return this;
    }

    public void setz_h(Integer z_h) {
        this.z_h = z_h;
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

    public Day getDay() {
        return day;
    }

    public Raport day(Day day) {
        this.day = day;
        return this;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Raport raport = (Raport) o;
        if(raport.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, raport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
            ", date='" + date + "'" +
            ", z_a='" + z_a + "'" +
            ", z_b='" + z_b + "'" +
            ", z_c='" + z_c + "'" +
            ", z_d='" + z_d + "'" +
            ", z_e='" + z_e + "'" +
            ", z_f='" + z_f + "'" +
            ", z_g='" + z_g + "'" +
            ", z_h='" + z_h + "'" +
            '}';
    }
}
