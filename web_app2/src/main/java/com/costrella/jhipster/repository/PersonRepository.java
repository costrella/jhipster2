package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Person;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT person from Person person where person.login = ?1")
    Person login(String login);
}
