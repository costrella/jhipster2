package com.costrella.db.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A Raport.
 */
public class Raport implements Serializable {

    public static final long serialVersionUID = 1L;

    public Long id;

    public String description;

    public byte[] foto1;

    public String foto1ContentType;

    public byte[] foto2;

    public String foto2ContentType;

    public byte[] foto3;

    public String foto3ContentType;

    public LocalDateTime date;


}
