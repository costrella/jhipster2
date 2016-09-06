package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Entitytest2;

import com.costrella.jhipster.repository.Entitytest2Repository;
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
 * REST controller for managing Entitytest2.
 */
@RestController
@RequestMapping("/api")
public class Entitytest2Resource {

    private final Logger log = LoggerFactory.getLogger(Entitytest2Resource.class);
        
    @Inject
    private Entitytest2Repository entitytest2Repository;

    /**
     * POST  /entitytest-2-s : Create a new entitytest2.
     *
     * @param entitytest2 the entitytest2 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entitytest2, or with status 400 (Bad Request) if the entitytest2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entitytest-2-s",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entitytest2> createEntitytest2(@RequestBody Entitytest2 entitytest2) throws URISyntaxException {
        log.debug("REST request to save Entitytest2 : {}", entitytest2);
        if (entitytest2.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entitytest2", "idexists", "A new entitytest2 cannot already have an ID")).body(null);
        }
        Entitytest2 result = entitytest2Repository.save(entitytest2);
        return ResponseEntity.created(new URI("/api/entitytest-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entitytest2", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entitytest-2-s : Updates an existing entitytest2.
     *
     * @param entitytest2 the entitytest2 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entitytest2,
     * or with status 400 (Bad Request) if the entitytest2 is not valid,
     * or with status 500 (Internal Server Error) if the entitytest2 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entitytest-2-s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entitytest2> updateEntitytest2(@RequestBody Entitytest2 entitytest2) throws URISyntaxException {
        log.debug("REST request to update Entitytest2 : {}", entitytest2);
        if (entitytest2.getId() == null) {
            return createEntitytest2(entitytest2);
        }
        Entitytest2 result = entitytest2Repository.save(entitytest2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entitytest2", entitytest2.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entitytest-2-s : get all the entitytest2S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entitytest2S in body
     */
    @RequestMapping(value = "/entitytest-2-s",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Entitytest2> getAllEntitytest2S() {
        log.debug("REST request to get all Entitytest2S");
        List<Entitytest2> entitytest2S = entitytest2Repository.findAll();
        return entitytest2S;
    }

    /**
     * GET  /entitytest-2-s/:id : get the "id" entitytest2.
     *
     * @param id the id of the entitytest2 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entitytest2, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/entitytest-2-s/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entitytest2> getEntitytest2(@PathVariable Long id) {
        log.debug("REST request to get Entitytest2 : {}", id);
        Entitytest2 entitytest2 = entitytest2Repository.findOne(id);
        return Optional.ofNullable(entitytest2)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entitytest-2-s/:id : delete the "id" entitytest2.
     *
     * @param id the id of the entitytest2 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/entitytest-2-s/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEntitytest2(@PathVariable Long id) {
        log.debug("REST request to delete Entitytest2 : {}", id);
        entitytest2Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entitytest2", id.toString())).build();
    }

}
