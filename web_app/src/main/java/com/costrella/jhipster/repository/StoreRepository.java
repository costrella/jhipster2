package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Store;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Store entity.
 */
@SuppressWarnings("unused")
public interface StoreRepository extends JpaRepository<Store,Long> {

    @Query("SELECT store from Store store where store.person.id = ?1")
    List<Store> getPersonStores(Long id);
}
