package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.JhipsterApp;
import com.costrella.jhipster.domain.Raport;
import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Store;
import com.costrella.jhipster.repository.RaportRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RaportResource REST controller.
 *
 * @see RaportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class RaportResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final byte[] DEFAULT_FOTO_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FOTO_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FOTO_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO_3 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_3_CONTENT_TYPE = "image/png";

    @Inject
    private RaportRepository raportRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRaportMockMvc;

    private Raport raport;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RaportResource raportResource = new RaportResource();
        ReflectionTestUtils.setField(raportResource, "raportRepository", raportRepository);
        this.restRaportMockMvc = MockMvcBuilders.standaloneSetup(raportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raport createEntity(EntityManager em) {
        Raport raport = new Raport();
        raport = new Raport()
                .date(DEFAULT_DATE)
                .description(DEFAULT_DESCRIPTION)
                .foto1(DEFAULT_FOTO_1)
                .foto1ContentType(DEFAULT_FOTO_1_CONTENT_TYPE)
                .foto2(DEFAULT_FOTO_2)
                .foto2ContentType(DEFAULT_FOTO_2_CONTENT_TYPE)
                .foto3(DEFAULT_FOTO_3)
                .foto3ContentType(DEFAULT_FOTO_3_CONTENT_TYPE);
        // Add required entity
        Person person = PersonResourceIntTest.createEntity(em);
        em.persist(person);
        em.flush();
        raport.setPerson(person);
        // Add required entity
        Store store = StoreResourceIntTest.createEntity(em);
        em.persist(store);
        em.flush();
        raport.setStore(store);
        return raport;
    }

    @Before
    public void initTest() {
        raport = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaport() throws Exception {
        int databaseSizeBeforeCreate = raportRepository.findAll().size();

        // Create the Raport

        restRaportMockMvc.perform(post("/api/raports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(raport)))
                .andExpect(status().isCreated());

        // Validate the Raport in the database
        List<Raport> raports = raportRepository.findAll();
        assertThat(raports).hasSize(databaseSizeBeforeCreate + 1);
        Raport testRaport = raports.get(raports.size() - 1);
        assertThat(testRaport.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRaport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRaport.getFoto1()).isEqualTo(DEFAULT_FOTO_1);
        assertThat(testRaport.getFoto1ContentType()).isEqualTo(DEFAULT_FOTO_1_CONTENT_TYPE);
        assertThat(testRaport.getFoto2()).isEqualTo(DEFAULT_FOTO_2);
        assertThat(testRaport.getFoto2ContentType()).isEqualTo(DEFAULT_FOTO_2_CONTENT_TYPE);
        assertThat(testRaport.getFoto3()).isEqualTo(DEFAULT_FOTO_3);
        assertThat(testRaport.getFoto3ContentType()).isEqualTo(DEFAULT_FOTO_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = raportRepository.findAll().size();
        // set the field null
        raport.setDate(null);

        // Create the Raport, which fails.

        restRaportMockMvc.perform(post("/api/raports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(raport)))
                .andExpect(status().isBadRequest());

        List<Raport> raports = raportRepository.findAll();
        assertThat(raports).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = raportRepository.findAll().size();
        // set the field null
        raport.setDescription(null);

        // Create the Raport, which fails.

        restRaportMockMvc.perform(post("/api/raports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(raport)))
                .andExpect(status().isBadRequest());

        List<Raport> raports = raportRepository.findAll();
        assertThat(raports).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRaports() throws Exception {
        // Initialize the database
        raportRepository.saveAndFlush(raport);

        // Get all the raports
        restRaportMockMvc.perform(get("/api/raports?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(raport.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].foto1ContentType").value(hasItem(DEFAULT_FOTO_1_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_1))))
                .andExpect(jsonPath("$.[*].foto2ContentType").value(hasItem(DEFAULT_FOTO_2_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_2))))
                .andExpect(jsonPath("$.[*].foto3ContentType").value(hasItem(DEFAULT_FOTO_3_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_3))));
    }

    @Test
    @Transactional
    public void getRaport() throws Exception {
        // Initialize the database
        raportRepository.saveAndFlush(raport);

        // Get the raport
        restRaportMockMvc.perform(get("/api/raports/{id}", raport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raport.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.foto1ContentType").value(DEFAULT_FOTO_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto1").value(Base64Utils.encodeToString(DEFAULT_FOTO_1)))
            .andExpect(jsonPath("$.foto2ContentType").value(DEFAULT_FOTO_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto2").value(Base64Utils.encodeToString(DEFAULT_FOTO_2)))
            .andExpect(jsonPath("$.foto3ContentType").value(DEFAULT_FOTO_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto3").value(Base64Utils.encodeToString(DEFAULT_FOTO_3)));
    }

    @Test
    @Transactional
    public void getNonExistingRaport() throws Exception {
        // Get the raport
        restRaportMockMvc.perform(get("/api/raports/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaport() throws Exception {
        // Initialize the database
        raportRepository.saveAndFlush(raport);
        int databaseSizeBeforeUpdate = raportRepository.findAll().size();

        // Update the raport
        Raport updatedRaport = raportRepository.findOne(raport.getId());
        updatedRaport
                .date(UPDATED_DATE)
                .description(UPDATED_DESCRIPTION)
                .foto1(UPDATED_FOTO_1)
                .foto1ContentType(UPDATED_FOTO_1_CONTENT_TYPE)
                .foto2(UPDATED_FOTO_2)
                .foto2ContentType(UPDATED_FOTO_2_CONTENT_TYPE)
                .foto3(UPDATED_FOTO_3)
                .foto3ContentType(UPDATED_FOTO_3_CONTENT_TYPE);

        restRaportMockMvc.perform(put("/api/raports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRaport)))
                .andExpect(status().isOk());

        // Validate the Raport in the database
        List<Raport> raports = raportRepository.findAll();
        assertThat(raports).hasSize(databaseSizeBeforeUpdate);
        Raport testRaport = raports.get(raports.size() - 1);
        assertThat(testRaport.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRaport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRaport.getFoto1()).isEqualTo(UPDATED_FOTO_1);
        assertThat(testRaport.getFoto1ContentType()).isEqualTo(UPDATED_FOTO_1_CONTENT_TYPE);
        assertThat(testRaport.getFoto2()).isEqualTo(UPDATED_FOTO_2);
        assertThat(testRaport.getFoto2ContentType()).isEqualTo(UPDATED_FOTO_2_CONTENT_TYPE);
        assertThat(testRaport.getFoto3()).isEqualTo(UPDATED_FOTO_3);
        assertThat(testRaport.getFoto3ContentType()).isEqualTo(UPDATED_FOTO_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteRaport() throws Exception {
        // Initialize the database
        raportRepository.saveAndFlush(raport);
        int databaseSizeBeforeDelete = raportRepository.findAll().size();

        // Get the raport
        restRaportMockMvc.perform(delete("/api/raports/{id}", raport.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Raport> raports = raportRepository.findAll();
        assertThat(raports).hasSize(databaseSizeBeforeDelete - 1);
    }
}
