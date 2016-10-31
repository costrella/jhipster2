package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Raport;
import com.costrella.jhipster.domain.Store;

import com.costrella.jhipster.repository.RaportRepository;
import com.costrella.jhipster.repository.StoreRepository;
import com.costrella.jhipster.repository.search.StoreSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreSearchRepository storeSearchRepository;

    /**
     * POST  /stores : Create a new store.
     *
     * @param store the store to create
     * @return the ResponseEntity with status 201 (Created) and with body the new store, or with status 400 (Bad Request) if the store has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Store> createStore(@Valid @RequestBody Store store) throws URISyntaxException {
        log.debug("REST request to save Store : {}", store);
        if (store.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("store", "idexists", "A new store cannot already have an ID")).body(null);
        }
        Store result = storeRepository.save(store);
        storeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("store", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param store the store to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated store,
     * or with status 400 (Bad Request) if the store is not valid,
     * or with status 500 (Internal Server Error) if the store couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Store> updateStore(@Valid @RequestBody Store store) throws URISyntaxException {
        log.debug("REST request to update Store : {}", store);
        if (store.getId() == null) {
            return createStore(store);
        }
        Store result = storeRepository.save(store);
        storeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("store", store.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Store>> getAllStores(Pageable pageable, @RequestParam(value = "personId", required = false) Long personId)
        throws URISyntaxException {
        log.debug("REST request to get a page of Stores");
        Page<Store> page;
        if(personId == null) {
            page = storeRepository.findAll(pageable);
        }else{
            page = storeRepository.getPersonStores(personId, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stores");

        return new ResponseEntity<>(checVisited(page.getContent()), headers, HttpStatus.OK);
    }

    private boolean checkMonthAndYear(LocalDate raportDate, Month month, int year) {
        if(raportDate == null) return false;
        return raportDate.getYear() == year && raportDate.getMonth().equals(month);
    }

    private List<Store> checVisited(List<Store> stores) {
        LocalDate today = LocalDate.now();
        Month month = today.getMonth();
        int year = today.getYear();

        //FIXME pobierac z bazy !
        for (Store s : stores) {
            List<Raport> raports = storeRepository.getStoresRaport(s.getId());
            for (Raport r : raports) {
                if(checkMonthAndYear(r.getDate(), month, year)){
                    s.setVisited(true);
                    break;
                }
//                else{
//                    s.setVisited(true);
//                }
            }
        }

        return stores;
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the store to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the store, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/stores/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Store> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        Store store = storeRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(store)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/personStores/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Store>> getPersonStores(@PathVariable Long id) {
        log.debug("REST request to get personStores : {}", id);
        List<Store> stores = storeRepository.getPersonStores(id);
        return new ResponseEntity<List<Store>>(checVisited(stores), HttpStatus.OK);
    }

//    @RequestMapping(value = "/dayStores/{id}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<List<Store>> getDayStores(@PathVariable Long id) {
//        log.debug("REST request to get dayStores : {}", id);
//        List<Store> stores = storeRepository.getDayStores(id);
//        return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
//    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the store to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/stores/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeRepository.delete(id);
        storeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("store", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stores?query=:query : search for the store corresponding
     * to the query.
     *
     * @param query the query of the store search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/stores",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Store>> searchStores(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Stores for query {}", query);
        Page<Store> page = storeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/stores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/stores",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"storeId"}
    )
    @Timed
    public ResponseEntity<Map<String, Integer>> getVisitCount(@RequestParam(value = "storeId", required = true) Long storeId
    ) throws URISyntaxException {

        LocalDate today = LocalDate.now();
        LocalDate monthAgo = today.minusMonths(1);

        Month month = today.getMonth();
        int year = today.getYear();
        int monthCount = 0;
        int monthAgoCount = 0;
        List<Raport> raports = storeRepository.getStoresRaport(storeId);
        for (Raport r : raports) {
            if(checkMonthAndYear(r.getDate(), month, year)){
                monthCount++;
            }
            if(checkMonthAndYear(r.getDate(), monthAgo.getMonth(), monthAgo.getYear())){
                monthAgoCount++;
            }
        }

        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("allCount", raports.size());
        myMap.put("month", monthCount);
        myMap.put("monthAgo", monthAgoCount);

        return Optional.ofNullable(myMap)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
