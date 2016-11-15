package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.CechiniApp;
import com.costrella.jhipster.domain.Storegroup;
import com.costrella.jhipster.repository.StoregroupRepository;
import com.costrella.jhipster.repository.search.StoregroupSearchRepository;

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
 * Test class for the StoregroupResource REST controller.
 *
 * @see StoregroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CechiniApp.class)
public class StoregroupResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private StoregroupRepository storegroupRepository;

    @Inject
    private StoregroupSearchRepository storegroupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStoregroupMockMvc;

    private Storegroup storegroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoregroupResource storegroupResource = new StoregroupResource();
        ReflectionTestUtils.setField(storegroupResource, "storegroupSearchRepository", storegroupSearchRepository);
        ReflectionTestUtils.setField(storegroupResource, "storegroupRepository", storegroupRepository);
        this.restStoregroupMockMvc = MockMvcBuilders.standaloneSetup(storegroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Storegroup createEntity(EntityManager em) {
        Storegroup storegroup = new Storegroup();
        storegroup = new Storegroup()
                .name(DEFAULT_NAME);
        return storegroup;
    }

    @Before
    public void initTest() {
        storegroupSearchRepository.deleteAll();
        storegroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoregroup() throws Exception {
        int databaseSizeBeforeCreate = storegroupRepository.findAll().size();

        // Create the Storegroup

        restStoregroupMockMvc.perform(post("/api/storegroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storegroup)))
                .andExpect(status().isCreated());

        // Validate the Storegroup in the database
        List<Storegroup> storegroups = storegroupRepository.findAll();
        assertThat(storegroups).hasSize(databaseSizeBeforeCreate + 1);
        Storegroup testStoregroup = storegroups.get(storegroups.size() - 1);
        assertThat(testStoregroup.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Storegroup in ElasticSearch
        Storegroup storegroupEs = storegroupSearchRepository.findOne(testStoregroup.getId());
        assertThat(storegroupEs).isEqualToComparingFieldByField(testStoregroup);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storegroupRepository.findAll().size();
        // set the field null
        storegroup.setName(null);

        // Create the Storegroup, which fails.

        restStoregroupMockMvc.perform(post("/api/storegroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storegroup)))
                .andExpect(status().isBadRequest());

        List<Storegroup> storegroups = storegroupRepository.findAll();
        assertThat(storegroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStoregroups() throws Exception {
        // Initialize the database
        storegroupRepository.saveAndFlush(storegroup);

        // Get all the storegroups
        restStoregroupMockMvc.perform(get("/api/storegroups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(storegroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getStoregroup() throws Exception {
        // Initialize the database
        storegroupRepository.saveAndFlush(storegroup);

        // Get the storegroup
        restStoregroupMockMvc.perform(get("/api/storegroups/{id}", storegroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storegroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStoregroup() throws Exception {
        // Get the storegroup
        restStoregroupMockMvc.perform(get("/api/storegroups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoregroup() throws Exception {
        // Initialize the database
        storegroupRepository.saveAndFlush(storegroup);
        storegroupSearchRepository.save(storegroup);
        int databaseSizeBeforeUpdate = storegroupRepository.findAll().size();

        // Update the storegroup
        Storegroup updatedStoregroup = storegroupRepository.findOne(storegroup.getId());
        updatedStoregroup
                .name(UPDATED_NAME);

        restStoregroupMockMvc.perform(put("/api/storegroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStoregroup)))
                .andExpect(status().isOk());

        // Validate the Storegroup in the database
        List<Storegroup> storegroups = storegroupRepository.findAll();
        assertThat(storegroups).hasSize(databaseSizeBeforeUpdate);
        Storegroup testStoregroup = storegroups.get(storegroups.size() - 1);
        assertThat(testStoregroup.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Storegroup in ElasticSearch
        Storegroup storegroupEs = storegroupSearchRepository.findOne(testStoregroup.getId());
        assertThat(storegroupEs).isEqualToComparingFieldByField(testStoregroup);
    }

    @Test
    @Transactional
    public void deleteStoregroup() throws Exception {
        // Initialize the database
        storegroupRepository.saveAndFlush(storegroup);
        storegroupSearchRepository.save(storegroup);
        int databaseSizeBeforeDelete = storegroupRepository.findAll().size();

        // Get the storegroup
        restStoregroupMockMvc.perform(delete("/api/storegroups/{id}", storegroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean storegroupExistsInEs = storegroupSearchRepository.exists(storegroup.getId());
        assertThat(storegroupExistsInEs).isFalse();

        // Validate the database is empty
        List<Storegroup> storegroups = storegroupRepository.findAll();
        assertThat(storegroups).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStoregroup() throws Exception {
        // Initialize the database
        storegroupRepository.saveAndFlush(storegroup);
        storegroupSearchRepository.save(storegroup);

        // Search the storegroup
        restStoregroupMockMvc.perform(get("/api/_search/storegroups?query=id:" + storegroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storegroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
