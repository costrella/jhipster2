package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Raport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Raport entity.
 */
@SuppressWarnings("unused")
public interface RaportRepository extends JpaRepository<Raport, Long> {
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

    @Query("SELECT raport from Raport raport where raport.date BETWEEN ?1 AND ?2")
    Page<Raport> getRaportsByDate(LocalDate from, LocalDate to, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.date BETWEEN ?1 AND ?2 AND raport.store.storegroup.id = ?3")
    Page<Raport> getRaportsByDateAndStoreGroup(LocalDate from, LocalDate to, Long storegroupId, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.date BETWEEN ?1 AND ?2 AND raport.store.storegroup.id = ?3")
    List<Raport> getRaportsByDateAndStoreGroup(LocalDate from, LocalDate to, Long storegroupId);

    @Query("SELECT raport from Raport raport where raport.date BETWEEN ?1 AND ?2 ORDER BY raport.date DESC")
    List<Raport> getRaportsByDate(LocalDate from, LocalDate to);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 ")
    Page<Raport> getRaportsByDateAndPerson(Long idPerson, LocalDate from, LocalDate to, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4")
    Page<Raport> getRaportsByDateAndPersonAndStoreGroup(Long idPerson, LocalDate from, LocalDate to, Long storegroupId, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4")
    List<Raport> getRaportsByDateAndPersonAndStoreGroup(Long idPerson, LocalDate from, LocalDate to, Long storegroupId);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 ")
    Page<Raport> getRaportsByDateAndPersonAndStore(Long idPerson, Long storeId, LocalDate from, LocalDate to, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5")
    Page<Raport> getRaportsByDateAndPersonAndStoreAndStoreGroup(Long idPerson, Long storeId, LocalDate from, LocalDate to, Long storegroupId, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5")
    List<Raport> getRaportsByDateAndPersonAndStoreAndStoreGroup(Long idPerson, Long storeId, LocalDate from, LocalDate to, Long storegroupId);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 ")
    List<Raport> getRaportsByDateAndPersonAndStore(Long idPerson, Long storeId, LocalDate from, LocalDate to);

    @Query("SELECT raport from Raport raport where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 ")
    Page<Raport> getRaportsByDateAndStore(Long storeId, LocalDate from, LocalDate to, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4")
    Page<Raport> getRaportsByDateAndStoreAndStoreGroup(Long storeId, LocalDate from, LocalDate to, Long storegroupId, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4")
    List<Raport> getRaportsByDateAndStoreAndStoreGroup(Long storeId, LocalDate from, LocalDate to, Long storegroupId);

    @Query("SELECT raport from Raport raport where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 ")
    List<Raport> getRaportsByDateAndStore(Long storeId, LocalDate from, LocalDate to);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 ORDER BY raport.date DESC")
    Page<Raport> getPersonRaports(Long idPerson, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 ORDER BY raport.date DESC")
    List<Raport> getPersonRaports(Long idPerson);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 ORDER BY raport.date DESC")
    List<Raport> getRaportsByDateAndPerson(Long idPerson, LocalDate from, LocalDate to);

    @Query("SELECT raport from Raport raport where raport.store.id = ?1 ORDER BY raport.date DESC")
    Page<Raport> getStoresRaports(Long idStore, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.store.id = ?1 ORDER BY raport.date DESC")
    List<Raport> getStoresRaports(Long idStore);

    @Query("SELECT raport from Raport raport where raport.day.id = ?1 ORDER BY raport.date DESC")
    Page<Raport> getDayRaports(Long idDay, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.day.id = ?1 ORDER BY raport.date DESC")
    List<Raport> getDayRaports(Long idDay);

    @Query("SELECT raport from Raport raport where raport.day.week.id = ?1 ORDER BY raport.date DESC")
    Page<Raport> getWeekRaports(Long idWeek, Pageable pageable);

    @Query("SELECT raport from Raport raport where raport.day.week.id = ?1 ORDER BY raport.date DESC")
    List<Raport> getWeekRaports(Long idWeek);
}
