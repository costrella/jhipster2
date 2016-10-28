package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Week;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Week entity.
 */
@SuppressWarnings("unused")
public interface WeekRepository extends JpaRepository<Week,Long> {
@Query("SELECT week from Week week where week.person.id = ?1")
    List<Week> getPersonWeeks(Long id);

    @Query("SELECT week from Week week where week.dateBefore BETWEEN ?1 AND ?2")
    Page<Week> getWeeksByDate(LocalDate from, LocalDate to, Pageable pageable);

    @Query("SELECT week from Week week where week.person.id = ?1 AND week.dateBefore BETWEEN ?2 AND ?3")
    Page<Week> getWeeksByDateAndPerson(Long id, LocalDate from, LocalDate to, Pageable pageable);
}
