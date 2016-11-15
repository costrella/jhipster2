package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.CechiniApp;
import com.costrella.jhipster.domain.Warehouse;
import com.costrella.jhipster.repository.WarehouseRepository;
import com.costrella.jhipster.repository.search.WarehouseSearchRepository;

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
 * Test class for the WarehouseResource REST controller.
 *
 * @see WarehouseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CechiniApp.class)
public class WarehouseResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private WarehouseRepository warehouseRepository;

    @Inject
    private WarehouseSearchRepository warehouseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWarehouseMockMvc;

    private Warehouse warehouse;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WarehouseResource warehouseResource = new WarehouseResource();
        ReflectionTestUtils.setField(warehouseResource, "warehouseSearchRepository", warehouseSearchRepository);
        ReflectionTestUtils.setField(warehouseResource, "warehouseRepository", warehouseRepository);
        this.restWarehouseMockMvc = MockMvcBuilders.standaloneSetup(warehouseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Warehouse createEntity(EntityManager em) {
        Warehouse warehouse = new Warehouse();
        warehouse = new Warehouse()
                .name(DEFAULT_NAME);
        return warehouse;
    }

    @Before
    public void initTest() {
        warehouseSearchRepository.deleteAll();
        warehouse = createEntity(em);
    }

    @Test
    @Transactional
    public void createWarehouse() throws Exception {
        int databaseSizeBeforeCreate = warehouseRepository.findAll().size();

        // Create the Warehouse

        restWarehouseMockMvc.perform(post("/api/warehouses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(warehouse)))
                .andExpect(status().isCreated());

        // Validate the Warehouse in the database
        List<Warehouse> warehouses = warehouseRepository.findAll();
        assertThat(warehouses).hasSize(databaseSizeBeforeCreate + 1);
        Warehouse testWarehouse = warehouses.get(warehouses.size() - 1);
        assertThat(testWarehouse.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Warehouse in ElasticSearch
        Warehouse warehouseEs = warehouseSearchRepository.findOne(testWarehouse.getId());
        assertThat(warehouseEs).isEqualToComparingFieldByField(testWarehouse);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = warehouseRepository.findAll().size();
        // set the field null
        warehouse.setName(null);

        // Create the Warehouse, which fails.

        restWarehouseMockMvc.perform(post("/api/warehouses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(warehouse)))
                .andExpect(status().isBadRequest());

        List<Warehouse> warehouses = warehouseRepository.findAll();
        assertThat(warehouses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWarehouses() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouses
        restWarehouseMockMvc.perform(get("/api/warehouses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(warehouse.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get the warehouse
        restWarehouseMockMvc.perform(get("/api/warehouses/{id}", warehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warehouse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWarehouse() throws Exception {
        // Get the warehouse
        restWarehouseMockMvc.perform(get("/api/warehouses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);
        warehouseSearchRepository.save(warehouse);
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();

        // Update the warehouse
        Warehouse updatedWarehouse = warehouseRepository.findOne(warehouse.getId());
        updatedWarehouse
                .name(UPDATED_NAME);

        restWarehouseMockMvc.perform(put("/api/warehouses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWarehouse)))
                .andExpect(status().isOk());

        // Validate the Warehouse in the database
        List<Warehouse> warehouses = warehouseRepository.findAll();
        assertThat(warehouses).hasSize(databaseSizeBeforeUpdate);
        Warehouse testWarehouse = warehouses.get(warehouses.size() - 1);
        assertThat(testWarehouse.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Warehouse in ElasticSearch
        Warehouse warehouseEs = warehouseSearchRepository.findOne(testWarehouse.getId());
        assertThat(warehouseEs).isEqualToComparingFieldByField(testWarehouse);
    }

    @Test
    @Transactional
    public void deleteWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);
        warehouseSearchRepository.save(warehouse);
        int databaseSizeBeforeDelete = warehouseRepository.findAll().size();

        // Get the warehouse
        restWarehouseMockMvc.perform(delete("/api/warehouses/{id}", warehouse.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean warehouseExistsInEs = warehouseSearchRepository.exists(warehouse.getId());
        assertThat(warehouseExistsInEs).isFalse();

        // Validate the database is empty
        List<Warehouse> warehouses = warehouseRepository.findAll();
        assertThat(warehouses).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);
        warehouseSearchRepository.save(warehouse);

        // Search the warehouse
        restWarehouseMockMvc.perform(get("/api/_search/warehouses?query=id:" + warehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
