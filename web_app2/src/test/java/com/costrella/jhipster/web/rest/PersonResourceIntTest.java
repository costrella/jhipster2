package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.CechiniApp;
import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.repository.PersonRepository;
import com.costrella.jhipster.repository.search.PersonSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CechiniApp.class)
public class PersonResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SURNAME = "AAAAA";
    private static final String UPDATED_SURNAME = "BBBBB";
    private static final String DEFAULT_LOGIN = "AAAA";
    private static final String UPDATED_LOGIN = "BBBB";
    private static final String DEFAULT_PASS = "AAAA";
    private static final String UPDATED_PASS = "BBBB";

    private static final Integer DEFAULT_TARGET_01 = 1;
    private static final Integer UPDATED_TARGET_01 = 2;

    private static final Integer DEFAULT_TARGET_02 = 1;
    private static final Integer UPDATED_TARGET_02 = 2;

    private static final Integer DEFAULT_TARGET_03 = 1;
    private static final Integer UPDATED_TARGET_03 = 2;

    private static final Integer DEFAULT_TARGET_04 = 1;
    private static final Integer UPDATED_TARGET_04 = 2;

    private static final Integer DEFAULT_TARGET_05 = 1;
    private static final Integer UPDATED_TARGET_05 = 2;

    private static final Integer DEFAULT_TARGET_06 = 1;
    private static final Integer UPDATED_TARGET_06 = 2;

    private static final Integer DEFAULT_TARGET_07 = 1;
    private static final Integer UPDATED_TARGET_07 = 2;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private PersonSearchRepository personSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource();
        ReflectionTestUtils.setField(personResource, "personSearchRepository", personSearchRepository);
        ReflectionTestUtils.setField(personResource, "personRepository", personRepository);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person();
        person = new Person()
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .login(DEFAULT_LOGIN)
                .pass(DEFAULT_PASS)
                .target01(DEFAULT_TARGET_01)
                .target02(DEFAULT_TARGET_02)
                .target03(DEFAULT_TARGET_03)
                .target04(DEFAULT_TARGET_04)
                .target05(DEFAULT_TARGET_05)
                .target06(DEFAULT_TARGET_06)
                .target07(DEFAULT_TARGET_07);
        return person;
    }

    @Before
    public void initTest() {
        personSearchRepository.deleteAll();
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = people.get(people.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testPerson.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testPerson.getPass()).isEqualTo(DEFAULT_PASS);
        assertThat(testPerson.getTarget01()).isEqualTo(DEFAULT_TARGET_01);
        assertThat(testPerson.getTarget02()).isEqualTo(DEFAULT_TARGET_02);
        assertThat(testPerson.getTarget03()).isEqualTo(DEFAULT_TARGET_03);
        assertThat(testPerson.getTarget04()).isEqualTo(DEFAULT_TARGET_04);
        assertThat(testPerson.getTarget05()).isEqualTo(DEFAULT_TARGET_05);
        assertThat(testPerson.getTarget06()).isEqualTo(DEFAULT_TARGET_06);
        assertThat(testPerson.getTarget07()).isEqualTo(DEFAULT_TARGET_07);

        // Validate the Person in ElasticSearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToComparingFieldByField(testPerson);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setName(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isBadRequest());

        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setSurname(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isBadRequest());

        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setLogin(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isBadRequest());

        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setPass(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isBadRequest());

        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the people
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
                .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
                .andExpect(jsonPath("$.[*].pass").value(hasItem(DEFAULT_PASS.toString())))
                .andExpect(jsonPath("$.[*].target01").value(hasItem(DEFAULT_TARGET_01)))
                .andExpect(jsonPath("$.[*].target02").value(hasItem(DEFAULT_TARGET_02)))
                .andExpect(jsonPath("$.[*].target03").value(hasItem(DEFAULT_TARGET_03)))
                .andExpect(jsonPath("$.[*].target04").value(hasItem(DEFAULT_TARGET_04)))
                .andExpect(jsonPath("$.[*].target05").value(hasItem(DEFAULT_TARGET_05)))
                .andExpect(jsonPath("$.[*].target06").value(hasItem(DEFAULT_TARGET_06)))
                .andExpect(jsonPath("$.[*].target07").value(hasItem(DEFAULT_TARGET_07)));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.pass").value(DEFAULT_PASS.toString()))
            .andExpect(jsonPath("$.target01").value(DEFAULT_TARGET_01))
            .andExpect(jsonPath("$.target02").value(DEFAULT_TARGET_02))
            .andExpect(jsonPath("$.target03").value(DEFAULT_TARGET_03))
            .andExpect(jsonPath("$.target04").value(DEFAULT_TARGET_04))
            .andExpect(jsonPath("$.target05").value(DEFAULT_TARGET_05))
            .andExpect(jsonPath("$.target06").value(DEFAULT_TARGET_06))
            .andExpect(jsonPath("$.target07").value(DEFAULT_TARGET_07));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        updatedPerson
                .name(UPDATED_NAME)
                .surname(UPDATED_SURNAME)
                .login(UPDATED_LOGIN)
                .pass(UPDATED_PASS)
                .target01(UPDATED_TARGET_01)
                .target02(UPDATED_TARGET_02)
                .target03(UPDATED_TARGET_03)
                .target04(UPDATED_TARGET_04)
                .target05(UPDATED_TARGET_05)
                .target06(UPDATED_TARGET_06)
                .target07(UPDATED_TARGET_07);

        restPersonMockMvc.perform(put("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
                .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = people.get(people.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testPerson.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testPerson.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testPerson.getTarget01()).isEqualTo(UPDATED_TARGET_01);
        assertThat(testPerson.getTarget02()).isEqualTo(UPDATED_TARGET_02);
        assertThat(testPerson.getTarget03()).isEqualTo(UPDATED_TARGET_03);
        assertThat(testPerson.getTarget04()).isEqualTo(UPDATED_TARGET_04);
        assertThat(testPerson.getTarget05()).isEqualTo(UPDATED_TARGET_05);
        assertThat(testPerson.getTarget06()).isEqualTo(UPDATED_TARGET_06);
        assertThat(testPerson.getTarget07()).isEqualTo(UPDATED_TARGET_07);

        // Validate the Person in ElasticSearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToComparingFieldByField(testPerson);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);
        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean personExistsInEs = personSearchRepository.exists(person.getId());
        assertThat(personExistsInEs).isFalse();

        // Validate the database is empty
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);

        // Search the person
        restPersonMockMvc.perform(get("/api/_search/people?query=id:" + person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].pass").value(hasItem(DEFAULT_PASS.toString())))
            .andExpect(jsonPath("$.[*].target01").value(hasItem(DEFAULT_TARGET_01)))
            .andExpect(jsonPath("$.[*].target02").value(hasItem(DEFAULT_TARGET_02)))
            .andExpect(jsonPath("$.[*].target03").value(hasItem(DEFAULT_TARGET_03)))
            .andExpect(jsonPath("$.[*].target04").value(hasItem(DEFAULT_TARGET_04)))
            .andExpect(jsonPath("$.[*].target05").value(hasItem(DEFAULT_TARGET_05)))
            .andExpect(jsonPath("$.[*].target06").value(hasItem(DEFAULT_TARGET_06)))
            .andExpect(jsonPath("$.[*].target07").value(hasItem(DEFAULT_TARGET_07)));
    }
}
