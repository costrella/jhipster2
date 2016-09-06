package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Entitytest1;

import com.costrella.jhipster.repository.Entitytest1Repository;
import com.costrella.jhipster.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Entitytest1.
 */
@RestController
@RequestMapping("/api")
public class Entitytest1Resource {

    private final Logger log = LoggerFactory.getLogger(Entitytest1Resource.class);
        
    @Inject
    private Entitytest1Repository entitytest1Repository;

    /**
     * POST  /entitytest-1-s : Create a new entitytest1.
     *
     * @param entitytest1 the entitytest1 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entitytest1, or with status 400 (Bad Request) if the entitytest1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entitytest-1-s",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entitytest1> createEntitytest1(@RequestBody Entitytest1 entitytest1) throws URISyntaxException {
        log.debug("REST request to save Entitytest1 : {}", entitytest1);
        if (entitytest1.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entitytest1", "idexists", "A new entitytest1 cannot already have an ID")).body(null);
        }
        Entitytest1 result = entitytest1Repository.save(entitytest1);
        return ResponseEntity.created(new URI("/api/entitytest-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entitytest1", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entitytest-1-s : Updates an existing entitytest1.
     *
     * @param entitytest1 the entitytest1 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entitytest1,
     * or with status 400 (Bad Request) if the entitytest1 is not valid,
     * or with status 500 (Internal Server Error) if the entitytest1 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entitytest-1-s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entitytest1> updateEntitytest1(@RequestBody Entitytest1 entitytest1) throws URISyntaxException {
        log.debug("REST request to update Entitytest1 : {}", entitytest1);
        if (entitytest1.getId() == null) {
            return createEntitytest1(entitytest1);
        }
        Entitytest1 result = entitytest1Repository.save(entitytest1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entitytest1", entitytest1.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entitytest-1-s : get all the entitytest1S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entitytest1S in body
     */
    @RequestMapping(value = "/entitytest-1-s",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Entitytest1> getAllEntitytest1S() {
        log.debug("REST request to get all Entitytest1S");
        List<Entitytest1> entitytest1S = entitytest1Repository.findAll();
        return entitytest1S;
    }

    /**
     * GET  /entitytest-1-s/:id : get the "id" entitytest1.
     *
     * @param id the id of the entitytest1 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entitytest1, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/entitytest-1-s/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entitytest1> getEntitytest1(@PathVariable Long id) {
        log.debug("REST request to get Entitytest1 : {}", id);
        Entitytest1 entitytest1 = entitytest1Repository.findOne(id);
        return Optional.ofNullable(entitytest1)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entitytest-1-s/:id : delete the "id" entitytest1.
     *
     * @param id the id of the entitytest1 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/entitytest-1-s/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEntitytest1(@PathVariable Long id) {
        log.debug("REST request to delete Entitytest1 : {}", id);
        entitytest1Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entitytest1", id.toString())).build();
    }

}
