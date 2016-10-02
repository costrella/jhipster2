package com.costrella.android.cechini.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Week.
 */
public class Week implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Date dateBefore;

    private Date dateAfter;

    private Integer weekOfYear;

    private Set<Day> days = new HashSet<>();

    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Week name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public Week dateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
        return this;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public Date getDateAfter() {
        return dateAfter;
    }

    public Week dateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
        return this;
    }

    public void setDateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
    }

    public Integer getWeekOfYear() {
        return weekOfYear;
    }

    public Week weekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
        return this;
    }

    public void setWeekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public Set<Day> getDays() {
        return days;
    }

    public Week days(Set<Day> days) {
        this.days = days;
        return this;
    }

    public Week addDay(Day day) {
        days.add(day);
        day.setWeek(this);
        return this;
    }

    public Week removeDay(Day day) {
        days.remove(day);
        day.setWeek(null);
        return this;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public Person getPerson() {
        return person;
    }

    public Week person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Week{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", dateBefore='" + dateBefore + "'" +
                ", dateAfter='" + dateAfter + "'" +
                ", weekOfYear='" + weekOfYear + "'" +
                '}';
    }
}
