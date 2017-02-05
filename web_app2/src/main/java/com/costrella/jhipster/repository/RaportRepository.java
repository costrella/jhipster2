package com.costrella.jhipster.repository;

import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Raport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Spring Data JPA repository for the Raport entity.
 */
@SuppressWarnings("unused")
public interface RaportRepository extends JpaRepository<Raport, Long> {
    @Query("select sum(raport.z_a) from Raport raport where raport.person.id = ?1")
    Map<String, Integer> getZ_atest(Long idPerson);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.date BETWEEN ?1 AND ?2 AND u.id = ?3")
    Map<String, Long> getRaportsByDate(LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.store.id = ?1 AND u.id = ?2")
    Map<String, Long> getStoresRaports(Long idStore, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 AND u.id = ?4 ")
    Map<String, Long> getRaportsByDateAndStore(Long storeId, LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.store.id = ?2 AND raport.date BETWEEN ?3 AND ?4 AND u.id = ?5 ")
    Map<String, Long> getRaportsByDateAndStoreAndWarehouse(Long warehouseId, Long storeId, LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4 AND u.id = ?5")
    Map<String, Long> getRaportsByDateAndStoreAndStoreGroup(Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5 AND u.id = ?6")
    Map<String, Long> getRaportsByDateAndStoreAndStoreGroupAndWarehouse(Long warehouseId, Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND u.id = ?5 ")
    Map<String, Long> getRaportsByDateAndPersonAndStore(Long idPerson, Long storeId, LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.store.id =?3 AND raport.date BETWEEN ?4 AND ?5 AND u.id = ?6 ")
    Map<String, Long> getRaportsByDateAndPersonAndStoreAndWarehouse(Long warehouseId, Long idPerson, Long storeId, LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " +
        "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5 AND u.id = ?6")
    Map<String, Long> getRaportsByDateAndPersonAndStoreAndStoreGroup(Long idPerson, Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.store.id =?3 AND raport.date BETWEEN ?4 AND ?5 AND raport.store.storegroup.id = ?6 AND u.id = ?7")
    Map<String, Long> getRaportsByDateAndPersonAndStoreAndStoreGroupAndWarehouse(Long warehouseId, Long idPerson, Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.day.id = ?1 AND u.id = ?2")
    Map<String, Long> getDayRaports(Long idDay, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.day.week.id = ?1 AND u.id = ?2")
    Map<String, Long> getWeekRaports(Long idWeek, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND u.id = ?4")
    Map<String, Long> getRaportsByDateAndWarehouse(Long warehouseId, LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4 AND u.id = ?5")
    Map<String, Long> getRaportsByDateAndStoreGroupAndWarehouse(Long warehouseId, LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.date BETWEEN ?1 AND ?2 AND raport.store.storegroup.id = ?3 AND u.id = ?4")
    Map<String, Long> getRaportsByDateAndStoreGroup(LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND u.id = ?4")
    Map<String, Long> getRaportsByDateAndPerson(Long idPerson, LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.date BETWEEN ?3 AND ?4 AND u.id = ?5 ")
    Map<String, Long> getRaportsByDateAndPersonAndWarehouse(Long warehouseId, Long idPerson, LocalDate from, LocalDate to, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5 AND u.id = ?6")
    Map<String, Long> getRaportsByDateAndPersonAndStoreGroupAndWarehouse(Long warehouseId, Long idPerson, LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4 AND u.id = ?5")
    Map<String, Long> getRaportsByDateAndPersonAndStoreGroup(Long idPerson, LocalDate from, LocalDate to, Long storegroupId, Long userId);

    @Query("SELECT sum(raport.z_a) as z_a, sum(raport.z_b) as z_b, sum(raport.z_c) as z_c, sum(raport.z_d) as z_d, " + "sum(raport.z_e) as z_e, sum(raport.z_f) as z_f, sum(raport.z_g) as z_g, sum(raport.z_h) as z_h " +
        "from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND u.id = ?2")
    Map<String, Long> getPersonRaportsMap(Long idPerson, Long userId);

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

    @Query("SELECT raport " +
        "from Raport raport where raport.person.id = ?1 ORDER BY raport.date DESC")
    List<Raport>  getPersonRaports(Long idPerson);

    @Query("SELECT raport from Raport raport where raport.person.id = ?1 AND raport.store.id = ?2 ORDER BY raport.id DESC")
    Page<Raport> getLastRaportByPersonAndStore(Long personId, Long storeId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.date BETWEEN ?1 AND ?2 AND u.id = ?3")
    Page<Raport> getRaportsByDate(LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND u.id = ?4")
    Page<Raport> getRaportsByDateAndWarehouse(Long warehouseId, LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.date BETWEEN ?1 AND ?2 AND raport.store.storegroup.id = ?3 AND u.id = ?4")
    Page<Raport> getRaportsByDateAndStoreGroup(LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4 AND u.id = ?5")
    Page<Raport> getRaportsByDateAndStoreGroupAndWarehouse(Long warehouseId, LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND u.id = ?4")
    Page<Raport> getRaportsByDateAndPerson(Long idPerson, LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.date BETWEEN ?3 AND ?4 AND u.id = ?5")
    Page<Raport> getRaportsByDateAndPersonAndWarehouse(Long warehouseId, Long idPerson, LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4 AND u.id = ?5")
    Page<Raport> getRaportsByDateAndPersonAndStoreGroup(Long idPerson, LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5 AND u.id = ?6")
    Page<Raport> getRaportsByDateAndPersonAndStoreGroupAndWarehouse(Long warehouseId, Long idPerson, LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND u.id = ?5")
    Page<Raport> getRaportsByDateAndPersonAndStore(Long idPerson, Long storeId, LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.store.id =?3 AND raport.date BETWEEN ?4 AND ?5 AND u.id = ?6")
    Page<Raport> getRaportsByDateAndPersonAndStoreAndWarehouse(Long warehouseId, Long idPerson, Long storeId, LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5 AND u.id = ?6")
    Page<Raport> getRaportsByDateAndPersonAndStoreAndStoreGroup(Long idPerson, Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.person.id = ?2 AND raport.store.id =?3 AND raport.date BETWEEN ?4 AND ?5 AND raport.store.storegroup.id = ?6 AND u.id = ?7")
    Page<Raport> getRaportsByDateAndPersonAndStoreAndStoreGroupAndWarehouse(Long warehouseId, Long idPerson, Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 AND u.id = ?4")
    Page<Raport> getRaportsByDateAndStore(Long storeId, LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.store.id = ?2 AND raport.date BETWEEN ?3 AND ?4 AND u.id = ?5")
    Page<Raport> getRaportsByDateAndStoreAndWarehouse(Long warehouseId, Long storeId, LocalDate from, LocalDate to, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.store.id =?1 AND raport.date BETWEEN ?2 AND ?3 AND raport.store.storegroup.id = ?4 AND u.id = ?5")
    Page<Raport> getRaportsByDateAndStoreAndStoreGroup(Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.warehouse.id = ?1 AND raport.store.id =?2 AND raport.date BETWEEN ?3 AND ?4 AND raport.store.storegroup.id = ?5 AND u.id = ?6")
    Page<Raport> getRaportsByDateAndStoreAndStoreGroupAndWarehouse(Long warehouseId, Long storeId, LocalDate from, LocalDate to, Long storegroupId, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.person.id = ?1 AND u.id = ?2 ORDER BY raport.date DESC")
    Page<Raport> getPersonRaports(Long idPerson, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.store.id = ?1 AND u.id = ?2 ORDER BY raport.date DESC")
    Page<Raport> getStoresRaports(Long idStore, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.day.id = ?1 AND u.id = ?2 ORDER BY raport.date DESC")
    Page<Raport> getDayRaports(Long idDay, Long userId, Pageable pageable);

    @Query("SELECT raport from Raport raport JOIN raport.person.users u where raport.day.week.id = ?1 AND u.id = ?2 ORDER BY raport.date DESC")
    Page<Raport> getWeekRaports(Long idWeek, Long userId, Pageable pageable);

}
