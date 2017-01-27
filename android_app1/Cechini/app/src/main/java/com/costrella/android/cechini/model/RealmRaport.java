package com.costrella.android.cechini.model;


import io.realm.RealmObject;

/**
 * Created by mike on 2017-01-23.
 */
public class RealmRaport extends RealmObject{

    private int id;

    private int personId;

    private String description;

    private byte[] foto1;

    private String foto1ContentType;

    private byte[] foto2;

    private String foto2ContentType;

    private byte[] foto3;

    private String foto3ContentType;

    private Integer z_a;

    private Integer z_b;

    private Integer z_c;

    private Integer z_d;

    private Integer z_e;

    private Integer z_f;

    private Integer z_g;

    private Integer z_h;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    public String getFoto1ContentType() {
        return foto1ContentType;
    }

    public void setFoto1ContentType(String foto1ContentType) {
        this.foto1ContentType = foto1ContentType;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    public String getFoto2ContentType() {
        return foto2ContentType;
    }

    public void setFoto2ContentType(String foto2ContentType) {
        this.foto2ContentType = foto2ContentType;
    }

    public byte[] getFoto3() {
        return foto3;
    }

    public void setFoto3(byte[] foto3) {
        this.foto3 = foto3;
    }

    public String getFoto3ContentType() {
        return foto3ContentType;
    }

    public void setFoto3ContentType(String foto3ContentType) {
        this.foto3ContentType = foto3ContentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Integer getZ_a() {
        return z_a;
    }

    public void setZ_a(Integer z_a) {
        this.z_a = z_a;
    }

    public Integer getZ_b() {
        return z_b;
    }

    public void setZ_b(Integer z_b) {
        this.z_b = z_b;
    }

    public Integer getZ_c() {
        return z_c;
    }

    public void setZ_c(Integer z_c) {
        this.z_c = z_c;
    }

    public Integer getZ_d() {
        return z_d;
    }

    public void setZ_d(Integer z_d) {
        this.z_d = z_d;
    }

    public Integer getZ_e() {
        return z_e;
    }

    public void setZ_e(Integer z_e) {
        this.z_e = z_e;
    }

    public Integer getZ_f() {
        return z_f;
    }

    public void setZ_f(Integer z_f) {
        this.z_f = z_f;
    }

    public Integer getZ_g() {
        return z_g;
    }

    public void setZ_g(Integer z_g) {
        this.z_g = z_g;
    }

    public Integer getZ_h() {
        return z_h;
    }

    public void setZ_h(Integer z_h) {
        this.z_h = z_h;
    }
}
