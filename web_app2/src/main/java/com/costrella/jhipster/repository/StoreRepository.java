package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Raport;
import com.costrella.jhipster.domain.Store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Store entity.
 */
@SuppressWarnings("unused")
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select distinct store from Store store left join fetch store.days")
    List<Store> findAllWithEagerRelationships();

    @Query("select store from Store store left join fetch store.days where store.id =:id")
    Store findOneWithEagerRelationships(@Param("id") Long id);

    @Query("SELECT store from Store store where store.person.id = ?1")
    List<Store> getPersonStores(Long id);

    @Query("SELECT store from Store store where store.person.id = ?1")
    Page<Store> getPersonStores(Long id, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.store.id = ?1")
    List<Raport> getStoresRaport(Long id);

}
