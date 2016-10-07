package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Day;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Day entity.
 */
@SuppressWarnings("unused")
public interface DayRepository extends JpaRepository<Day, Long> {

    @Query("SELECT day from Day day where day.week.id = ?1")
    List<Day> getWeekDays(Long id);

    @Query("select distinct day from Day day left join fetch day.stores")
    List<Day> findAllWithEagerRelationships();

    @Query("select day from Day day left join fetch day.stores where day.id =:id")
    Day findOneWithEagerRelationships(@Param("id") Long id);

}
