package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Store;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Store entity.
 */
@SuppressWarnings("unused")
public interface StoreRepository extends JpaRepository<Store,Long> {

    @Query("select distinct store from Store store left join fetch store.days")
    List<Store> findAllWithEagerRelationships();

    @Query("select store from Store store left join fetch store.days where store.id =:id")
    Store findOneWithEagerRelationships(@Param("id") Long id);

}
