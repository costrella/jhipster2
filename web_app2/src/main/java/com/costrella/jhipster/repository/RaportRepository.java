package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Raport;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Raport entity.
 */
@SuppressWarnings("unused")
public interface RaportRepository extends JpaRepository<Raport,Long> {
    @Query("select sum(raport.z_a) from Raport raport where raport.person.id = ?1")
    int getZ_a(Long idPerson);

    @Query("select sum(r.z_b) from Raport r where r.person.id = ?1")
    int getZ_b(Long idPerson);

    @Query("select sum(r.z_c) from Raport r where r.person.id = ?1")
    int getZ_c(Long idPerson);

    @Query("select sum(r.z_d) from Raport r where r.person.id = ?1")
    int getZ_d(Long idPerson);

    @Query("select sum(r.z_e) from Raport r where r.person.id = ?1")
    int getZ_e(Long idPerson);

}
