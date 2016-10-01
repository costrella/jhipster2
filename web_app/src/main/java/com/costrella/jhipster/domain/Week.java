package com.costrella.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Week.
 */
@Entity
@Table(name = "week")
public class Week implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_before")
    private LocalDate dateBefore;

    @Column(name = "date_after")
    private LocalDate dateAfter;

    @Column(name = "week_of_year")
    private Integer weekOfYear;

    @OneToMany(mappedBy = "week")
    @JsonIgnore
    private Set<Day> days = new HashSet<>();

    @ManyToOne
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

    public LocalDate getDateBefore() {
        return dateBefore;
    }

    public Week dateBefore(LocalDate dateBefore) {
        this.dateBefore = dateBefore;
        return this;
    }

    public void setDateBefore(LocalDate dateBefore) {
        this.dateBefore = dateBefore;
    }

    public LocalDate getDateAfter() {
        return dateAfter;
    }

    public Week dateAfter(LocalDate dateAfter) {
        this.dateAfter = dateAfter;
        return this;
    }

    public void setDateAfter(LocalDate dateAfter) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Week week = (Week) o;
        if(week.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, week.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
