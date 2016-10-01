package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Week;

import com.costrella.jhipster.repository.WeekRepository;
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

/**
 * REST controller for managing Week.
 */
@RestController
@RequestMapping("/api")
public class WeekResource {

    private final Logger log = LoggerFactory.getLogger(WeekResource.class);
        
    @Inject
    private WeekRepository weekRepository;

    /**
     * POST  /weeks : Create a new week.
     *
     * @param week the week to create
     * @return the ResponseEntity with status 201 (Created) and with body the new week, or with status 400 (Bad Request) if the week has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/weeks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Week> createWeek(@RequestBody Week week) throws URISyntaxException {
        log.debug("REST request to save Week : {}", week);
        if (week.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("week", "idexists", "A new week cannot already have an ID")).body(null);
        }
        Week result = weekRepository.save(week);
        return ResponseEntity.created(new URI("/api/weeks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("week", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weeks : Updates an existing week.
     *
     * @param week the week to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated week,
     * or with status 400 (Bad Request) if the week is not valid,
     * or with status 500 (Internal Server Error) if the week couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/weeks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Week> updateWeek(@RequestBody Week week) throws URISyntaxException {
        log.debug("REST request to update Week : {}", week);
        if (week.getId() == null) {
            return createWeek(week);
        }
        Week result = weekRepository.save(week);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("week", week.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weeks : get all the weeks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weeks in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/weeks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Week>> getAllWeeks(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Weeks");
        Page<Week> page = weekRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/weeks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /weeks/:id : get the "id" week.
     *
     * @param id the id of the week to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the week, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/weeks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Week> getWeek(@PathVariable Long id) {
        log.debug("REST request to get Week : {}", id);
        Week week = weekRepository.findOne(id);
        return Optional.ofNullable(week)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /weeks/:id : delete the "id" week.
     *
     * @param id the id of the week to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/weeks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWeek(@PathVariable Long id) {
        log.debug("REST request to delete Week : {}", id);
        weekRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("week", id.toString())).build();
    }

}
