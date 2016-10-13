package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Day;

import com.costrella.jhipster.repository.DayRepository;
import com.costrella.jhipster.repository.search.DaySearchRepository;
import com.costrella.jhipster.web.rest.util.HeaderUtil;
import com.costrella.jhipster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Day.
 */
@RestController
@RequestMapping("/api")
public class DayResource {

    private final Logger log = LoggerFactory.getLogger(DayResource.class);
        
    @Inject
    private DayRepository dayRepository;

    @Inject
    private DaySearchRepository daySearchRepository;

    /**
     * POST  /days : Create a new day.
     *
     * @param day the day to create
     * @return the ResponseEntity with status 201 (Created) and with body the new day, or with status 400 (Bad Request) if the day has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/days",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Day> createDay(@RequestBody Day day) throws URISyntaxException {
        log.debug("REST request to save Day : {}", day);
        if (day.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("day", "idexists", "A new day cannot already have an ID")).body(null);
        }
        Day result = dayRepository.save(day);
        daySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("day", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /days : Updates an existing day.
     *
     * @param day the day to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated day,
     * or with status 400 (Bad Request) if the day is not valid,
     * or with status 500 (Internal Server Error) if the day couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/days",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Day> updateDay(@RequestBody Day day) throws URISyntaxException {
        log.debug("REST request to update Day : {}", day);
        if (day.getId() == null) {
            return createDay(day);
        }
        Day result = dayRepository.save(day);
        daySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("day", day.getId().toString()))
            .body(result);
    }

    /**
     * GET  /days : get all the days.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of days in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/days",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Day>> getAllDays(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Days");
        Page<Day> page = dayRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/days");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /days/:id : get the "id" day.
     *
     * @param id the id of the day to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the day, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/days/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Day> getDay(@PathVariable Long id) {
        log.debug("REST request to get Day : {}", id);
        Day day = dayRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(day)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /days/:id : delete the "id" day.
     *
     * @param id the id of the day to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/days/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDay(@PathVariable Long id) {
        log.debug("REST request to delete Day : {}", id);
        dayRepository.delete(id);
        daySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("day", id.toString())).build();
    }

    /**
     * SEARCH  /_search/days?query=:query : search for the day corresponding
     * to the query.
     *
     * @param query the query of the day search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/days",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Day>> searchDays(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Days for query {}", query);
        Page<Day> page = daySearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/days");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
