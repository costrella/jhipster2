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

    @Query("SELECT store from Store store where store.person.id = ?1 ORDER BY store.name ASC")
    List<Store> getPersonStores(Long id);

    @Query("SELECT store from Store store where store.person.id = ?1")
    Page<Store> getPersonStores(Long id, Pageable pageable);

    @Query("SELECT store from Store store where store.person.id = ?1 AND store.address.id = ?2")
    Page<Store> getPersonAndAddressStores(Long id, Long addressId, Pageable pageable);

    @Query("SELECT store from Store store where store.person.id = ?1 AND store.storegroup.id = ?2")
    Page<Store> getPersonAndStoregroupStores(Long id, Long sgId, Pageable pageable);

    @Query("SELECT store from Store store where store.person.id = ?1 AND store.storegroup.id = ?2 AND store.address.id = ?3")
    Page<Store> getPersonAndStoregroupAndAddressStores(Long id, Long sgId, Long addressId, Pageable pageable);

    @Query("SELECT store from Store store where store.storegroup.id = ?1")
    Page<Store> getStoregroupStores(Long sgId, Pageable pageable);

    @Query("SELECT store from Store store where store.storegroup.id = ?1 AND store.address.id = ?2")
    Page<Store> getStoregroupAndAddressStores(Long sgId, Long addressId, Pageable pageable);

    @Query("SELECT store from Store store where store.address.id = ?1")
    Page<Store> getAddressStores(Long addressId, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.store.id = ?1")
    List<Raport> getStoresRaport(Long id);

}
