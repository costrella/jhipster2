package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Map;

/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT person from Person person where person.login = ?1")
    Person login(String login);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3")
    Map<String, Long> getTargetsAtMonth(Long personId, LocalDate from, LocalDate to);
}
