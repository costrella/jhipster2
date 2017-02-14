package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.CechiniApp;
import com.costrella.jhipster.domain.Raport;
import com.costrella.jhipster.domain.Person;
import com.costrella.jhipster.domain.Store;
import com.costrella.jhipster.repository.RaportRepository;
import com.costrella.jhipster.repository.search.RaportSearchRepository;

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
import java.time.LocalDateTime;
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
@SpringBootTest(classes = CechiniApp.class)
public class RaportResourceIntTest {
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

    private static final LocalDateTime DEFAULT_DATE = null;
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_Z_A = 1;
    private static final Integer UPDATED_Z_A = 2;

    private static final Integer DEFAULT_Z_B = 1;
    private static final Integer UPDATED_Z_B = 2;

    private static final Integer DEFAULT_Z_C = 1;
    private static final Integer UPDATED_Z_C = 2;

    private static final Integer DEFAULT_Z_D = 1;
    private static final Integer UPDATED_Z_D = 2;

    private static final Integer DEFAULT_Z_E = 1;
    private static final Integer UPDATED_Z_E = 2;

    private static final Integer DEFAULT_Z_F = 1;
    private static final Integer UPDATED_Z_F = 2;

    private static final Integer DEFAULT_Z_G = 1;
    private static final Integer UPDATED_Z_G = 2;

    private static final Integer DEFAULT_Z_H = 1;
    private static final Integer UPDATED_Z_H = 2;
    private static final String DEFAULT_ADMIN_DESC = "AAAAA";
    private static final String UPDATED_ADMIN_DESC = "BBBBB";

    @Inject
    private RaportRepository raportRepository;

    @Inject
    private RaportSearchRepository raportSearchRepository;

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
        ReflectionTestUtils.setField(raportResource, "raportSearchRepository", raportSearchRepository);
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
                .description(DEFAULT_DESCRIPTION)
                .foto1(DEFAULT_FOTO_1)
                .foto1ContentType(DEFAULT_FOTO_1_CONTENT_TYPE)
                .foto2(DEFAULT_FOTO_2)
                .foto2ContentType(DEFAULT_FOTO_2_CONTENT_TYPE)
                .foto3(DEFAULT_FOTO_3)
                .foto3ContentType(DEFAULT_FOTO_3_CONTENT_TYPE)
                .date(DEFAULT_DATE)
                .z_a(DEFAULT_Z_A)
                .z_b(DEFAULT_Z_B)
                .z_c(DEFAULT_Z_C)
                .z_d(DEFAULT_Z_D)
                .z_e(DEFAULT_Z_E)
                .z_f(DEFAULT_Z_F)
                .z_g(DEFAULT_Z_G)
                .z_h(DEFAULT_Z_H)
                .adminDesc(DEFAULT_ADMIN_DESC);
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
        raportSearchRepository.deleteAll();
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
        assertThat(testRaport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRaport.getFoto1()).isEqualTo(DEFAULT_FOTO_1);
        assertThat(testRaport.getFoto1ContentType()).isEqualTo(DEFAULT_FOTO_1_CONTENT_TYPE);
        assertThat(testRaport.getFoto2()).isEqualTo(DEFAULT_FOTO_2);
        assertThat(testRaport.getFoto2ContentType()).isEqualTo(DEFAULT_FOTO_2_CONTENT_TYPE);
        assertThat(testRaport.getFoto3()).isEqualTo(DEFAULT_FOTO_3);
        assertThat(testRaport.getFoto3ContentType()).isEqualTo(DEFAULT_FOTO_3_CONTENT_TYPE);
        assertThat(testRaport.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRaport.getz_a()).isEqualTo(DEFAULT_Z_A);
        assertThat(testRaport.getz_b()).isEqualTo(DEFAULT_Z_B);
        assertThat(testRaport.getz_c()).isEqualTo(DEFAULT_Z_C);
        assertThat(testRaport.getz_d()).isEqualTo(DEFAULT_Z_D);
        assertThat(testRaport.getz_e()).isEqualTo(DEFAULT_Z_E);
        assertThat(testRaport.getz_f()).isEqualTo(DEFAULT_Z_F);
        assertThat(testRaport.getz_g()).isEqualTo(DEFAULT_Z_G);
        assertThat(testRaport.getz_h()).isEqualTo(DEFAULT_Z_H);
        assertThat(testRaport.getAdminDesc()).isEqualTo(DEFAULT_ADMIN_DESC);

        // Validate the Raport in ElasticSearch
        Raport raportEs = raportSearchRepository.findOne(testRaport.getId());
        assertThat(raportEs).isEqualToComparingFieldByField(testRaport);
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
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].foto1ContentType").value(hasItem(DEFAULT_FOTO_1_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_1))))
                .andExpect(jsonPath("$.[*].foto2ContentType").value(hasItem(DEFAULT_FOTO_2_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_2))))
                .andExpect(jsonPath("$.[*].foto3ContentType").value(hasItem(DEFAULT_FOTO_3_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_3))))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].z_a").value(hasItem(DEFAULT_Z_A)))
                .andExpect(jsonPath("$.[*].z_b").value(hasItem(DEFAULT_Z_B)))
                .andExpect(jsonPath("$.[*].z_c").value(hasItem(DEFAULT_Z_C)))
                .andExpect(jsonPath("$.[*].z_d").value(hasItem(DEFAULT_Z_D)))
                .andExpect(jsonPath("$.[*].z_e").value(hasItem(DEFAULT_Z_E)))
                .andExpect(jsonPath("$.[*].z_f").value(hasItem(DEFAULT_Z_F)))
                .andExpect(jsonPath("$.[*].z_g").value(hasItem(DEFAULT_Z_G)))
                .andExpect(jsonPath("$.[*].z_h").value(hasItem(DEFAULT_Z_H)))
                .andExpect(jsonPath("$.[*].adminDesc").value(hasItem(DEFAULT_ADMIN_DESC.toString())));
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
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.foto1ContentType").value(DEFAULT_FOTO_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto1").value(Base64Utils.encodeToString(DEFAULT_FOTO_1)))
            .andExpect(jsonPath("$.foto2ContentType").value(DEFAULT_FOTO_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto2").value(Base64Utils.encodeToString(DEFAULT_FOTO_2)))
            .andExpect(jsonPath("$.foto3ContentType").value(DEFAULT_FOTO_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto3").value(Base64Utils.encodeToString(DEFAULT_FOTO_3)))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.z_a").value(DEFAULT_Z_A))
            .andExpect(jsonPath("$.z_b").value(DEFAULT_Z_B))
            .andExpect(jsonPath("$.z_c").value(DEFAULT_Z_C))
            .andExpect(jsonPath("$.z_d").value(DEFAULT_Z_D))
            .andExpect(jsonPath("$.z_e").value(DEFAULT_Z_E))
            .andExpect(jsonPath("$.z_f").value(DEFAULT_Z_F))
            .andExpect(jsonPath("$.z_g").value(DEFAULT_Z_G))
            .andExpect(jsonPath("$.z_h").value(DEFAULT_Z_H))
            .andExpect(jsonPath("$.adminDesc").value(DEFAULT_ADMIN_DESC.toString()));
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
        raportSearchRepository.save(raport);
        int databaseSizeBeforeUpdate = raportRepository.findAll().size();

        // Update the raport
        Raport updatedRaport = raportRepository.findOne(raport.getId());
        updatedRaport
                .description(UPDATED_DESCRIPTION)
                .foto1(UPDATED_FOTO_1)
                .foto1ContentType(UPDATED_FOTO_1_CONTENT_TYPE)
                .foto2(UPDATED_FOTO_2)
                .foto2ContentType(UPDATED_FOTO_2_CONTENT_TYPE)
                .foto3(UPDATED_FOTO_3)
                .foto3ContentType(UPDATED_FOTO_3_CONTENT_TYPE)
                .date(UPDATED_DATE)
                .z_a(UPDATED_Z_A)
                .z_b(UPDATED_Z_B)
                .z_c(UPDATED_Z_C)
                .z_d(UPDATED_Z_D)
                .z_e(UPDATED_Z_E)
                .z_f(UPDATED_Z_F)
                .z_g(UPDATED_Z_G)
                .z_h(UPDATED_Z_H)
                .adminDesc(UPDATED_ADMIN_DESC);

        restRaportMockMvc.perform(put("/api/raports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRaport)))
                .andExpect(status().isOk());

        // Validate the Raport in the database
        List<Raport> raports = raportRepository.findAll();
        assertThat(raports).hasSize(databaseSizeBeforeUpdate);
        Raport testRaport = raports.get(raports.size() - 1);
        assertThat(testRaport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRaport.getFoto1()).isEqualTo(UPDATED_FOTO_1);
        assertThat(testRaport.getFoto1ContentType()).isEqualTo(UPDATED_FOTO_1_CONTENT_TYPE);
        assertThat(testRaport.getFoto2()).isEqualTo(UPDATED_FOTO_2);
        assertThat(testRaport.getFoto2ContentType()).isEqualTo(UPDATED_FOTO_2_CONTENT_TYPE);
        assertThat(testRaport.getFoto3()).isEqualTo(UPDATED_FOTO_3);
        assertThat(testRaport.getFoto3ContentType()).isEqualTo(UPDATED_FOTO_3_CONTENT_TYPE);
        assertThat(testRaport.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRaport.getz_a()).isEqualTo(UPDATED_Z_A);
        assertThat(testRaport.getz_b()).isEqualTo(UPDATED_Z_B);
        assertThat(testRaport.getz_c()).isEqualTo(UPDATED_Z_C);
        assertThat(testRaport.getz_d()).isEqualTo(UPDATED_Z_D);
        assertThat(testRaport.getz_e()).isEqualTo(UPDATED_Z_E);
        assertThat(testRaport.getz_f()).isEqualTo(UPDATED_Z_F);
        assertThat(testRaport.getz_g()).isEqualTo(UPDATED_Z_G);
        assertThat(testRaport.getz_h()).isEqualTo(UPDATED_Z_H);
        assertThat(testRaport.getAdminDesc()).isEqualTo(UPDATED_ADMIN_DESC);

        // Validate the Raport in ElasticSearch
        Raport raportEs = raportSearchRepository.findOne(testRaport.getId());
        assertThat(raportEs).isEqualToComparingFieldByField(testRaport);
    }

    @Test
    @Transactional
    public void deleteRaport() throws Exception {
        // Initialize the database
        raportRepository.saveAndFlush(raport);
        raportSearchRepository.save(raport);
        int databaseSizeBeforeDelete = raportRepository.findAll().size();

        // Get the raport
        restRaportMockMvc.perform(delete("/api/raports/{id}", raport.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean raportExistsInEs = raportSearchRepository.exists(raport.getId());
        assertThat(raportExistsInEs).isFalse();

        // Validate the database is empty
        List<Raport> raports = raportRepository.findAll();
        assertThat(raports).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRaport() throws Exception {
        // Initialize the database
        raportRepository.saveAndFlush(raport);
        raportSearchRepository.save(raport);

        // Search the raport
        restRaportMockMvc.perform(get("/api/_search/raports?query=id:" + raport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raport.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].foto1ContentType").value(hasItem(DEFAULT_FOTO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_1))))
            .andExpect(jsonPath("$.[*].foto2ContentType").value(hasItem(DEFAULT_FOTO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_2))))
            .andExpect(jsonPath("$.[*].foto3ContentType").value(hasItem(DEFAULT_FOTO_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_3))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].z_a").value(hasItem(DEFAULT_Z_A)))
            .andExpect(jsonPath("$.[*].z_b").value(hasItem(DEFAULT_Z_B)))
            .andExpect(jsonPath("$.[*].z_c").value(hasItem(DEFAULT_Z_C)))
            .andExpect(jsonPath("$.[*].z_d").value(hasItem(DEFAULT_Z_D)))
            .andExpect(jsonPath("$.[*].z_e").value(hasItem(DEFAULT_Z_E)))
            .andExpect(jsonPath("$.[*].z_f").value(hasItem(DEFAULT_Z_F)))
            .andExpect(jsonPath("$.[*].z_g").value(hasItem(DEFAULT_Z_G)))
            .andExpect(jsonPath("$.[*].z_h").value(hasItem(DEFAULT_Z_H)))
            .andExpect(jsonPath("$.[*].adminDesc").value(hasItem(DEFAULT_ADMIN_DESC.toString())));
    }
}
