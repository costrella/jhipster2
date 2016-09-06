package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.JhipsterApp;
import com.costrella.jhipster.domain.Entitytest2;
import com.costrella.jhipster.repository.Entitytest2Repository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Entitytest2Resource REST controller.
 *
 * @see Entitytest2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class Entitytest2ResourceIntTest {

    private static final byte[] DEFAULT_TEST_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TEST_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_TEST_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TEST_1_CONTENT_TYPE = "image/png";

    @Inject
    private Entitytest2Repository entitytest2Repository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntitytest2MockMvc;

    private Entitytest2 entitytest2;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Entitytest2Resource entitytest2Resource = new Entitytest2Resource();
        ReflectionTestUtils.setField(entitytest2Resource, "entitytest2Repository", entitytest2Repository);
        this.restEntitytest2MockMvc = MockMvcBuilders.standaloneSetup(entitytest2Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entitytest2 createEntity(EntityManager em) {
        Entitytest2 entitytest2 = new Entitytest2();
        entitytest2 = new Entitytest2()
                .test1(DEFAULT_TEST_1)
                .test1ContentType(DEFAULT_TEST_1_CONTENT_TYPE);
        return entitytest2;
    }

    @Before
    public void initTest() {
        entitytest2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntitytest2() throws Exception {
        int databaseSizeBeforeCreate = entitytest2Repository.findAll().size();

        // Create the Entitytest2

        restEntitytest2MockMvc.perform(post("/api/entitytest-2-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entitytest2)))
                .andExpect(status().isCreated());

        // Validate the Entitytest2 in the database
        List<Entitytest2> entitytest2S = entitytest2Repository.findAll();
        assertThat(entitytest2S).hasSize(databaseSizeBeforeCreate + 1);
        Entitytest2 testEntitytest2 = entitytest2S.get(entitytest2S.size() - 1);
        assertThat(testEntitytest2.getTest1()).isEqualTo(DEFAULT_TEST_1);
        assertThat(testEntitytest2.getTest1ContentType()).isEqualTo(DEFAULT_TEST_1_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEntitytest2S() throws Exception {
        // Initialize the database
        entitytest2Repository.saveAndFlush(entitytest2);

        // Get all the entitytest2S
        restEntitytest2MockMvc.perform(get("/api/entitytest-2-s?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entitytest2.getId().intValue())))
                .andExpect(jsonPath("$.[*].test1ContentType").value(hasItem(DEFAULT_TEST_1_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].test1").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEST_1))));
    }

    @Test
    @Transactional
    public void getEntitytest2() throws Exception {
        // Initialize the database
        entitytest2Repository.saveAndFlush(entitytest2);

        // Get the entitytest2
        restEntitytest2MockMvc.perform(get("/api/entitytest-2-s/{id}", entitytest2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entitytest2.getId().intValue()))
            .andExpect(jsonPath("$.test1ContentType").value(DEFAULT_TEST_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.test1").value(Base64Utils.encodeToString(DEFAULT_TEST_1)));
    }

    @Test
    @Transactional
    public void getNonExistingEntitytest2() throws Exception {
        // Get the entitytest2
        restEntitytest2MockMvc.perform(get("/api/entitytest-2-s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntitytest2() throws Exception {
        // Initialize the database
        entitytest2Repository.saveAndFlush(entitytest2);
        int databaseSizeBeforeUpdate = entitytest2Repository.findAll().size();

        // Update the entitytest2
        Entitytest2 updatedEntitytest2 = entitytest2Repository.findOne(entitytest2.getId());
        updatedEntitytest2
                .test1(UPDATED_TEST_1)
                .test1ContentType(UPDATED_TEST_1_CONTENT_TYPE);

        restEntitytest2MockMvc.perform(put("/api/entitytest-2-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEntitytest2)))
                .andExpect(status().isOk());

        // Validate the Entitytest2 in the database
        List<Entitytest2> entitytest2S = entitytest2Repository.findAll();
        assertThat(entitytest2S).hasSize(databaseSizeBeforeUpdate);
        Entitytest2 testEntitytest2 = entitytest2S.get(entitytest2S.size() - 1);
        assertThat(testEntitytest2.getTest1()).isEqualTo(UPDATED_TEST_1);
        assertThat(testEntitytest2.getTest1ContentType()).isEqualTo(UPDATED_TEST_1_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteEntitytest2() throws Exception {
        // Initialize the database
        entitytest2Repository.saveAndFlush(entitytest2);
        int databaseSizeBeforeDelete = entitytest2Repository.findAll().size();

        // Get the entitytest2
        restEntitytest2MockMvc.perform(delete("/api/entitytest-2-s/{id}", entitytest2.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Entitytest2> entitytest2S = entitytest2Repository.findAll();
        assertThat(entitytest2S).hasSize(databaseSizeBeforeDelete - 1);
    }
}
