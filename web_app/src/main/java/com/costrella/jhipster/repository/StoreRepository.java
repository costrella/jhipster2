package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Raport;
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

    @Query("SELECT raport from Raport raport where raport.store.id = ?1")
    List<Raport> getStoresRaport(Long id);

//    @Query("SELECT store from Store store left join fetch store.days where day.id = ?1")
//    List<Store> getDayStores(Long id);

    //   @Query("select day from Day day left join fetch day.stores where day.id =:id")
}
