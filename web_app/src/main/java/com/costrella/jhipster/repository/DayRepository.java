package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Day;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Day entity.
 */
@SuppressWarnings("unused")
public interface DayRepository extends JpaRepository<Day,Long> {

    @Query("SELECT day from Day day where day.week.id = ?1")
    List<Day> getWeekDays(Long id);

}
