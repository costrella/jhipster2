package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Storegroup;

import com.costrella.jhipster.repository.StoregroupRepository;
import com.costrella.jhipster.repository.search.StoregroupSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Storegroup.
 */
@RestController
@RequestMapping("/api")
public class StoregroupResource {

    private final Logger log = LoggerFactory.getLogger(StoregroupResource.class);
        
    @Inject
    private StoregroupRepository storegroupRepository;

    @Inject
    private StoregroupSearchRepository storegroupSearchRepository;

    /**
     * POST  /storegroups : Create a new storegroup.
     *
     * @param storegroup the storegroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storegroup, or with status 400 (Bad Request) if the storegroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/storegroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Storegroup> createStoregroup(@Valid @RequestBody Storegroup storegroup) throws URISyntaxException {
        log.debug("REST request to save Storegroup : {}", storegroup);
        if (storegroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("storegroup", "idexists", "A new storegroup cannot already have an ID")).body(null);
        }
        Storegroup result = storegroupRepository.save(storegroup);
        storegroupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/storegroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("storegroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /storegroups : Updates an existing storegroup.
     *
     * @param storegroup the storegroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storegroup,
     * or with status 400 (Bad Request) if the storegroup is not valid,
     * or with status 500 (Internal Server Error) if the storegroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/storegroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Storegroup> updateStoregroup(@Valid @RequestBody Storegroup storegroup) throws URISyntaxException {
        log.debug("REST request to update Storegroup : {}", storegroup);
        if (storegroup.getId() == null) {
            return createStoregroup(storegroup);
        }
        Storegroup result = storegroupRepository.save(storegroup);
        storegroupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("storegroup", storegroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /storegroups : get all the storegroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of storegroups in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/storegroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Storegroup>> getAllStoregroups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Storegroups");
        Page<Storegroup> page = storegroupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/storegroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /storegroups/:id : get the "id" storegroup.
     *
     * @param id the id of the storegroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storegroup, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/storegroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Storegroup> getStoregroup(@PathVariable Long id) {
        log.debug("REST request to get Storegroup : {}", id);
        Storegroup storegroup = storegroupRepository.findOne(id);
        return Optional.ofNullable(storegroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /storegroups/:id : delete the "id" storegroup.
     *
     * @param id the id of the storegroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/storegroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStoregroup(@PathVariable Long id) {
        log.debug("REST request to delete Storegroup : {}", id);
        storegroupRepository.delete(id);
        storegroupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("storegroup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/storegroups?query=:query : search for the storegroup corresponding
     * to the query.
     *
     * @param query the query of the storegroup search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/storegroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Storegroup>> searchStoregroups(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Storegroups for query {}", query);
        Page<Storegroup> page = storegroupSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/storegroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
