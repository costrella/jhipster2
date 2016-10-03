package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Week;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Week entity.
 */
@SuppressWarnings("unused")
public interface WeekRepository extends JpaRepository<Week,Long> {

    @Query("SELECT week from Week week where week.person.id = ?1")
    List<Week> getPersonWeeks(Long id);

}
