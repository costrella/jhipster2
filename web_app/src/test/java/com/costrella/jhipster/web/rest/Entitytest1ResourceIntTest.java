package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.JhipsterApp;
import com.costrella.jhipster.domain.Entitytest1;
import com.costrella.jhipster.repository.Entitytest1Repository;

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
 * Test class for the Entitytest1Resource REST controller.
 *
 * @see Entitytest1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class Entitytest1ResourceIntTest {
    private static final String DEFAULT_TEST_1 = "AAAAA";
    private static final String UPDATED_TEST_1 = "BBBBB";
    private static final String DEFAULT_STRING_123 = "AAAAA";
    private static final String UPDATED_STRING_123 = "BBBBB";

    @Inject
    private Entitytest1Repository entitytest1Repository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntitytest1MockMvc;

    private Entitytest1 entitytest1;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Entitytest1Resource entitytest1Resource = new Entitytest1Resource();
        ReflectionTestUtils.setField(entitytest1Resource, "entitytest1Repository", entitytest1Repository);
        this.restEntitytest1MockMvc = MockMvcBuilders.standaloneSetup(entitytest1Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entitytest1 createEntity(EntityManager em) {
        Entitytest1 entitytest1 = new Entitytest1();
        entitytest1 = new Entitytest1()
                .test1(DEFAULT_TEST_1)
                .string123(DEFAULT_STRING_123);
        return entitytest1;
    }

    @Before
    public void initTest() {
        entitytest1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntitytest1() throws Exception {
        int databaseSizeBeforeCreate = entitytest1Repository.findAll().size();

        // Create the Entitytest1

        restEntitytest1MockMvc.perform(post("/api/entitytest-1-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entitytest1)))
                .andExpect(status().isCreated());

        // Validate the Entitytest1 in the database
        List<Entitytest1> entitytest1S = entitytest1Repository.findAll();
        assertThat(entitytest1S).hasSize(databaseSizeBeforeCreate + 1);
        Entitytest1 testEntitytest1 = entitytest1S.get(entitytest1S.size() - 1);
        assertThat(testEntitytest1.getTest1()).isEqualTo(DEFAULT_TEST_1);
        assertThat(testEntitytest1.getString123()).isEqualTo(DEFAULT_STRING_123);
    }

    @Test
    @Transactional
    public void getAllEntitytest1S() throws Exception {
        // Initialize the database
        entitytest1Repository.saveAndFlush(entitytest1);

        // Get all the entitytest1S
        restEntitytest1MockMvc.perform(get("/api/entitytest-1-s?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entitytest1.getId().intValue())))
                .andExpect(jsonPath("$.[*].test1").value(hasItem(DEFAULT_TEST_1.toString())))
                .andExpect(jsonPath("$.[*].string123").value(hasItem(DEFAULT_STRING_123.toString())));
    }

    @Test
    @Transactional
    public void getEntitytest1() throws Exception {
        // Initialize the database
        entitytest1Repository.saveAndFlush(entitytest1);

        // Get the entitytest1
        restEntitytest1MockMvc.perform(get("/api/entitytest-1-s/{id}", entitytest1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entitytest1.getId().intValue()))
            .andExpect(jsonPath("$.test1").value(DEFAULT_TEST_1.toString()))
            .andExpect(jsonPath("$.string123").value(DEFAULT_STRING_123.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntitytest1() throws Exception {
        // Get the entitytest1
        restEntitytest1MockMvc.perform(get("/api/entitytest-1-s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntitytest1() throws Exception {
        // Initialize the database
        entitytest1Repository.saveAndFlush(entitytest1);
        int databaseSizeBeforeUpdate = entitytest1Repository.findAll().size();

        // Update the entitytest1
        Entitytest1 updatedEntitytest1 = entitytest1Repository.findOne(entitytest1.getId());
        updatedEntitytest1
                .test1(UPDATED_TEST_1)
                .string123(UPDATED_STRING_123);

        restEntitytest1MockMvc.perform(put("/api/entitytest-1-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEntitytest1)))
                .andExpect(status().isOk());

        // Validate the Entitytest1 in the database
        List<Entitytest1> entitytest1S = entitytest1Repository.findAll();
        assertThat(entitytest1S).hasSize(databaseSizeBeforeUpdate);
        Entitytest1 testEntitytest1 = entitytest1S.get(entitytest1S.size() - 1);
        assertThat(testEntitytest1.getTest1()).isEqualTo(UPDATED_TEST_1);
        assertThat(testEntitytest1.getString123()).isEqualTo(UPDATED_STRING_123);
    }

    @Test
    @Transactional
    public void deleteEntitytest1() throws Exception {
        // Initialize the database
        entitytest1Repository.saveAndFlush(entitytest1);
        int databaseSizeBeforeDelete = entitytest1Repository.findAll().size();

        // Get the entitytest1
        restEntitytest1MockMvc.perform(delete("/api/entitytest-1-s/{id}", entitytest1.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Entitytest1> entitytest1S = entitytest1Repository.findAll();
        assertThat(entitytest1S).hasSize(databaseSizeBeforeDelete - 1);
    }
}
