package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Storegroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Storegroup entity.
 */
@SuppressWarnings("unused")
public interface StoregroupRepository extends JpaRepository<Storegroup,Long> {

}
