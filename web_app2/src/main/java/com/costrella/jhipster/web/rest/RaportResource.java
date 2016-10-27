package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Raport;
import com.costrella.jhipster.repository.RaportRepository;
import com.costrella.jhipster.repository.search.RaportSearchRepository;
import com.costrella.jhipster.web.rest.util.HeaderUtil;
import com.costrella.jhipster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Raport.
 */
@RestController
@RequestMapping("/api")
public class RaportResource {

    private final Logger log = LoggerFactory.getLogger(RaportResource.class);

    @Inject
    private RaportRepository raportRepository;

    @Inject
    private RaportSearchRepository raportSearchRepository;

    /**
     * POST  /raports : Create a new raport.
     *
     * @param raport the raport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new raport, or with status 400 (Bad Request) if the raport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/raports",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Raport> createRaport(@Valid @RequestBody Raport raport) throws URISyntaxException {
        log.debug("REST request to save Raport : {}", raport);
        if (raport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("raport", "idexists", "A new raport cannot already have an ID")).body(null);
        }
        //ALBO TWORZYMY RAPORT Z TRASOWKI, ALBO NIE Z TRASOWKI
        raport.setDate(LocalDate.now());
        if (raport.getDay() != null) {
            if (raport.getDay().getDate() != null) {
                LocalDate tmp = Instant.ofEpochMilli(raport.getDay().getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                raport.setDate(tmp);
            }
        }
        Raport result = raportRepository.save(raport);
        raportSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/raports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("raport", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raports : Updates an existing raport.
     *
     * @param raport the raport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated raport,
     * or with status 400 (Bad Request) if the raport is not valid,
     * or with status 500 (Internal Server Error) if the raport couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/raports",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Raport> updateRaport(@Valid @RequestBody Raport raport) throws URISyntaxException {
        log.debug("REST request to update Raport : {}", raport);
        if (raport.getId() == null) {
            return createRaport(raport);
        }
        Raport result = raportRepository.save(raport);
        raportSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("raport", raport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raports : get all the raports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of raports in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
//    @RequestMapping(value = "/raports",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<List<Raport>> getAllRaports(Pageable pageable)
//        throws URISyntaxException {
//        log.debug("REST request to get a page of Raports");
//        Page<Raport> page = raportRepository.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/raports");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
    @RequestMapping(value = "/raports",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Timed
    public ResponseEntity<List<Raport>> getRaportsFiltered(Pageable pageable,
                                                           @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                           @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                           @RequestParam(value = "person", required = false) Long person,
                                                           @RequestParam(value = "storeId", required = false) Long storeId,
                                                           @RequestParam(value = "dayId", required = false) Long dayId,
                                                           @RequestParam(value = "weekId", required = false) Long weekId
    ) throws URISyntaxException {
        Page<Raport> page;
        //warunek dla wyswietlania raportow w 'sklepie'
        if (storeId != null) {
            page = raportRepository.getStoresRaports(storeId, pageable);
        } else if (dayId != null) {
            page = raportRepository.getDayRaports(dayId, pageable);
        } else if (weekId != null) {
            page = raportRepository.getWeekRaports(weekId, pageable);
        } else {
            if (person == -1) {
                page = raportRepository.getRaportsByDate(fromDate, toDate, pageable);
            } else {
                if(fromDate == null && toDate == null){
                    //przypadek dla raportow w "person"
                    page = raportRepository.getPersonRaports(person, pageable);
                }else {
                    page = raportRepository.getRaportsByDateAndPerson(person, fromDate, toDate, pageable);
                }
            }
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/raports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/raports",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"test"}
    )
    @Timed
    public ResponseEntity<Map<String, Integer>> getRaportsCount(@RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                                @RequestParam(value = "person", required = false) Long person,
                                                                @RequestParam(value = "storeId", required = false) Long storeId,
                                                                @RequestParam(value = "dayId", required = false) Long dayId,
                                                                @RequestParam(value = "weekId", required = false) Long weekId,
                                                                @RequestParam(value = "test", required = false) Long test
    ) throws URISyntaxException {
        List<Raport> raportsList;
        if (person == -1) {
            raportsList = raportRepository.getRaportsByDate(fromDate, toDate);
        } else {
            raportsList = raportRepository.getRaportsByDateAndPerson(person, fromDate, toDate);
        }
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
        for (Raport r : raportsList) {
            a += r.getz_a();
            b += r.getz_b();
            c += r.getz_c();
            d += r.getz_d();
            e += r.getz_e();
            f += r.getz_f();
            g += r.getz_g();
            h += r.getz_h();
        }

        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("a", a);
        myMap.put("b", b);
        myMap.put("c", c);
        myMap.put("d", d);
        myMap.put("e", e);
        myMap.put("f", f);
        myMap.put("g", g);
        myMap.put("h", h);

        return Optional.ofNullable(myMap)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/order/{idPerson}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map<String, Integer>> order(@PathVariable int idPerson)
        throws URISyntaxException {
        log.debug("REST request to get a page of Raports");
        Long idLong = Long.valueOf(idPerson);
        int z_a = raportRepository.getZ_a(idLong);
        int z_b = raportRepository.getZ_b(idLong);
        int z_c = raportRepository.getZ_c(idLong);
        int z_d = raportRepository.getZ_d(idLong);
        int z_e = raportRepository.getZ_e(idLong);
        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("2L NGAZ: ", z_a);
        myMap.put("2L GAZ: ", z_b);
        myMap.put("0,33L: ", z_c);
        myMap.put("CYT: ", z_d);
        myMap.put("gratisy: ", z_e);

        return Optional.ofNullable(myMap)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /raports/:id : get the "id" raport.
     *
     * @param id the id of the raport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the raport, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/raports/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Raport> getRaport(@PathVariable Long id) {
        log.debug("REST request to get Raport : {}", id);
        Raport raport = raportRepository.findOne(id);
        return Optional.ofNullable(raport)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /raports/:id : delete the "id" raport.
     *
     * @param id the id of the raport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/raports/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRaport(@PathVariable Long id) {
        log.debug("REST request to delete Raport : {}", id);
        raportRepository.delete(id);
        raportSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("raport", id.toString())).build();
    }

    /**
     * SEARCH  /_search/raports?query=:query : search for the raport corresponding
     * to the query.
     *
     * @param query    the query of the raport search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/raports",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Raport>> searchRaports(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Raports for query {}", query);
        Page<Raport> page = raportSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/raports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
