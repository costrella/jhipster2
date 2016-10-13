package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Raport;
import com.costrella.jhipster.repository.RaportRepository;
import com.costrella.jhipster.repository.search.RaportSearchRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
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
        raport.setDate(LocalDate.now());
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
    @RequestMapping(value = "/raports",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Raport>> getAllRaports(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Raports");
        Page<Raport> page = raportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/raports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
     * @param query the query of the raport search
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
