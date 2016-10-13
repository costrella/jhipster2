package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.CechiniApp;
import com.costrella.jhipster.domain.Store;
import com.costrella.jhipster.repository.StoreRepository;
import com.costrella.jhipster.repository.search.StoreSearchRepository;

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
 * Test class for the StoreResource REST controller.
 *
 * @see StoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CechiniApp.class)
public class StoreResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";

    private static final Boolean DEFAULT_VISITED = false;
    private static final Boolean UPDATED_VISITED = true;
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreSearchRepository storeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStoreMockMvc;

    private Store store;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoreResource storeResource = new StoreResource();
        ReflectionTestUtils.setField(storeResource, "storeSearchRepository", storeSearchRepository);
        ReflectionTestUtils.setField(storeResource, "storeRepository", storeRepository);
        this.restStoreMockMvc = MockMvcBuilders.standaloneSetup(storeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createEntity(EntityManager em) {
        Store store = new Store();
        store = new Store()
                .name(DEFAULT_NAME)
                .city(DEFAULT_CITY)
                .visited(DEFAULT_VISITED)
                .street(DEFAULT_STREET)
                .number(DEFAULT_NUMBER)
                .description(DEFAULT_DESCRIPTION);
        return store;
    }

    @Before
    public void initTest() {
        storeSearchRepository.deleteAll();
        store = createEntity(em);
    }

    @Test
    @Transactional
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store

        restStoreMockMvc.perform(post("/api/stores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(store)))
                .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = stores.get(stores.size() - 1);
        assertThat(testStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStore.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStore.isVisited()).isEqualTo(DEFAULT_VISITED);
        assertThat(testStore.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testStore.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testStore.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Store in ElasticSearch
        Store storeEs = storeSearchRepository.findOne(testStore.getId());
        assertThat(storeEs).isEqualToComparingFieldByField(testStore);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setName(null);

        // Create the Store, which fails.

        restStoreMockMvc.perform(post("/api/stores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(store)))
                .andExpect(status().isBadRequest());

        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setCity(null);

        // Create the Store, which fails.

        restStoreMockMvc.perform(post("/api/stores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(store)))
                .andExpect(status().isBadRequest());

        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the stores
        restStoreMockMvc.perform(get("/api/stores?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].visited").value(hasItem(DEFAULT_VISITED.booleanValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(store.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.visited").value(DEFAULT_VISITED.booleanValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        storeSearchRepository.save(store);
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        Store updatedStore = storeRepository.findOne(store.getId());
        updatedStore
                .name(UPDATED_NAME)
                .city(UPDATED_CITY)
                .visited(UPDATED_VISITED)
                .street(UPDATED_STREET)
                .number(UPDATED_NUMBER)
                .description(UPDATED_DESCRIPTION);

        restStoreMockMvc.perform(put("/api/stores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStore)))
                .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeUpdate);
        Store testStore = stores.get(stores.size() - 1);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStore.isVisited()).isEqualTo(UPDATED_VISITED);
        assertThat(testStore.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testStore.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testStore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Store in ElasticSearch
        Store storeEs = storeSearchRepository.findOne(testStore.getId());
        assertThat(storeEs).isEqualToComparingFieldByField(testStore);
    }

    @Test
    @Transactional
    public void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        storeSearchRepository.save(store);
        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Get the store
        restStoreMockMvc.perform(delete("/api/stores/{id}", store.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean storeExistsInEs = storeSearchRepository.exists(store.getId());
        assertThat(storeExistsInEs).isFalse();

        // Validate the database is empty
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        storeSearchRepository.save(store);

        // Search the store
        restStoreMockMvc.perform(get("/api/_search/stores?query=id:" + store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].visited").value(hasItem(DEFAULT_VISITED.booleanValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
}
