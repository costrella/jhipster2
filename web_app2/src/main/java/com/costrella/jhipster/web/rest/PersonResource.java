package com.costrella.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.jhipster.domain.Person;

import com.costrella.jhipster.domain.Raport;
import com.costrella.jhipster.domain.Week;
import com.costrella.jhipster.repository.PersonRepository;
import com.costrella.jhipster.repository.RaportRepository;
import com.costrella.jhipster.repository.search.PersonSearchRepository;
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
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Inject
    private PersonRepository personRepository;

    @Inject
    private RaportRepository raportRepository;

    @Inject
    private PersonSearchRepository personSearchRepository;

    /**
     * POST  /people : Create a new person.
     *
     * @param person the person to create
     * @return the ResponseEntity with status 201 (Created) and with body the new person, or with status 400 (Bad Request) if the person has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/people",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to save Person : {}", person);
        if (person.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("person", "idexists", "A new person cannot already have an ID")).body(null);
        }
        Person result = personRepository.save(person);
        personSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("person", result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/loginPerson",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> loginPerson(@Valid @RequestBody Person personPost) throws URISyntaxException {
        log.debug("REST request to login Person : {}", personPost);
        if (personPost.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("person", "idexists", "A person cannot already have an ID")).body(null);
        }
        if (personPost.getLogin() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("person", "loginNPE", "A person have a null LOGIN")).body(null);
        }
        if (personPost.getPass() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("person", "passNPE", "A person have a null PASSWORD")).body(null);
        }

        Person result = personRepository.login(personPost.getLogin());
        if (result == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("person", "personDoesNotExist", "A person does not exist")).body(null);
        }
        if (!result.getPass().equals(personPost.getPass())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("person", "personDoesNotExist", "A person does not exist")).body(null);
        }

        return ResponseEntity.created(new URI("/api/loginPerson/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("person", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people : Updates an existing person.
     *
     * @param person the person to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated person,
     * or with status 400 (Bad Request) if the person is not valid,
     * or with status 500 (Internal Server Error) if the person couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/people",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to update Person : {}", person);
        if (person.getId() == null) {
            return createPerson(person);
        }
        Person result = personRepository.save(person);
        personSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("person", person.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people : get all the people.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of people in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/people",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Person>> getAllPeople(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of People");
        Page<Person> page = personRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /people/:id : get the "id" person.
     *
     * @param id the id of the person to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the person, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/people/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        return Optional.ofNullable(person)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /people/:id : delete the "id" person.
     *
     * @param id the id of the person to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/people/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.delete(id);
        personSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("person", id.toString())).build();
    }

    /**
     * SEARCH  /_search/people?query=:query : search for the person corresponding
     * to the query.
     *
     * @param query    the query of the person search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/people",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Person>> searchPeople(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of People for query {}", query);
        Page<Person> page = personSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/people",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"personId"}
    )
    @Timed
    public ResponseEntity<Map<String, Long>> getTargets(@RequestParam(value = "personId", required = true) Long personId
    ) throws URISyntaxException {
        Map<String, Long> page = null;
        LocalDate today = LocalDate.now();
        Person person = personRepository.findOne(personId);
        int targetMain = person.getTarget01();

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date firstMonth = c.getTime(); //pierwszy'
        LocalDate firstMonthLD1 = firstMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        c = Calendar.getInstance();
        int lastDate = c.getActualMaximum(Calendar.DATE);
        c.set(Calendar.DATE, lastDate);

        Date lastMonth = c.getTime(); //ostatni
        LocalDate lastMonthLD1 = lastMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDateTime firstMonthLD = LocalDateTime.of(firstMonthLD1, LocalTime.MIN);
        LocalDateTime lastMonthLD = LocalDateTime.of(lastMonthLD1, LocalTime.MIN);

        page = personRepository.getTargetsAtMonth(personId, firstMonthLD, lastMonthLD);

        long targetSum01 = 0, targetSum02 = 0, targetSum03 = 0, targetSum04 = 0, targetSum05 = 0, targetSum06 = 0, targetSum07 = 0, targetSum08 = 0;
        targetSum01 =  page.get("z_a");
        targetSum02 =  page.get("z_b");
        targetSum03 =  page.get("z_c");
        targetSum04 =  page.get("z_d");
        targetSum05 =  page.get("z_e");
        targetSum06 =  page.get("z_f");
        targetSum07 =  page.get("z_g");
        targetSum08 =  page.get("z_h");

        long sumAll = (long)targetSum01 + (long)targetSum02 + (long)targetSum03 + (long)targetSum04
//            + (double)targetSum05 BEZ GRATISOW
            + (long)targetSum06 + (long)targetSum07 + (long)targetSum08;
        long sumAllPercent = getPercent(targetMain, sumAll);

        Map<String, Long> myMap = new HashMap<>();
        myMap.put("targetMain", (long)targetMain);
        myMap.put("sumAll", sumAll);
        myMap.put("sumAllPercent", sumAllPercent);


        targetSum03 = targetSum03 / 2;
        targetSum04 = targetSum03 / 2;
        targetSum07 = targetSum03 / 2;
        targetSum08 = targetSum03 / 2;
        myMap.put("target01", targetSum01);
        myMap.put("target02", targetSum02);
        myMap.put("target03", targetSum03);
        myMap.put("target04", targetSum04);
        myMap.put("target05", targetSum05);
        myMap.put("target06", targetSum06);
        myMap.put("target07", targetSum07);
        myMap.put("target08", targetSum08);


//sprzedano <b>{{vm.sumAll}}</b> z {{vm.targetMain}} ({{vm.sumAllPercent}}%)

        return Optional.ofNullable(myMap)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //TODO DUBLOWANIE KODU, juz jest taka metoda w STORERESOURCE
    private boolean checkMonthAndYear(LocalDate raportDate, Month month, int year) {
        if(raportDate == null) return false;
        return raportDate.getYear() == year && raportDate.getMonth().equals(month);
    }

    private double getPercent(double targetD, double targetSumD){
        double percent = 0;

        if(targetD != 0 && targetSumD != 0){
            percent = (targetSumD / targetD) * 100;

            //zaokraglenie do 2 miejsc po przecinku
            percent *= 100; // pi = pi * 100;
            percent = Math.round(percent);
            percent /= 100; // pi = pi / 100;
        }
        return percent;
    }

    private long getPercent(long targetD, long targetSumD){
        long percent = 0;

        if(targetD != 0 && targetSumD != 0){
            percent = (targetSumD / targetD) * 100;

            //zaokraglenie do 2 miejsc po przecinku
            percent *= 100; // pi = pi * 100;
            percent = Math.round(percent);
            percent /= 100; // pi = pi / 100;
        }
        return percent;
    }

}
