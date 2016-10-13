package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.CechiniApp;
import com.costrella.jhipster.domain.Day;
import com.costrella.jhipster.repository.DayRepository;
import com.costrella.jhipster.repository.search.DaySearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DayResource REST controller.
 *
 * @see DayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CechiniApp.class)
public class DayResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private DayRepository dayRepository;

    @Inject
    private DaySearchRepository daySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDayMockMvc;

    private Day day;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DayResource dayResource = new DayResource();
        ReflectionTestUtils.setField(dayResource, "daySearchRepository", daySearchRepository);
        ReflectionTestUtils.setField(dayResource, "dayRepository", dayRepository);
        this.restDayMockMvc = MockMvcBuilders.standaloneSetup(dayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Day createEntity(EntityManager em) {
        Day day = new Day();
        day = new Day()
                .name(DEFAULT_NAME);
        return day;
    }

    @Before
    public void initTest() {
        daySearchRepository.deleteAll();
        day = createEntity(em);
    }

    @Test
    @Transactional
    public void createDay() throws Exception {
        int databaseSizeBeforeCreate = dayRepository.findAll().size();

        // Create the Day

        restDayMockMvc.perform(post("/api/days")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(day)))
                .andExpect(status().isCreated());

        // Validate the Day in the database
        List<Day> days = dayRepository.findAll();
        assertThat(days).hasSize(databaseSizeBeforeCreate + 1);
        Day testDay = days.get(days.size() - 1);
        assertThat(testDay.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDay.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the Day in ElasticSearch
        Day dayEs = daySearchRepository.findOne(testDay.getId());
        assertThat(dayEs).isEqualToComparingFieldByField(testDay);
    }

    @Test
    @Transactional
    public void getAllDays() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the days
        restDayMockMvc.perform(get("/api/days?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(day.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get the day
        restDayMockMvc.perform(get("/api/days/{id}", day.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(day.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDay() throws Exception {
        // Get the day
        restDayMockMvc.perform(get("/api/days/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);
        daySearchRepository.save(day);
        int databaseSizeBeforeUpdate = dayRepository.findAll().size();

        // Update the day
        Day updatedDay = dayRepository.findOne(day.getId());
        updatedDay
                .name(UPDATED_NAME);

        restDayMockMvc.perform(put("/api/days")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDay)))
                .andExpect(status().isOk());

        // Validate the Day in the database
        List<Day> days = dayRepository.findAll();
        assertThat(days).hasSize(databaseSizeBeforeUpdate);
        Day testDay = days.get(days.size() - 1);
        assertThat(testDay.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDay.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the Day in ElasticSearch
        Day dayEs = daySearchRepository.findOne(testDay.getId());
        assertThat(dayEs).isEqualToComparingFieldByField(testDay);
    }

    @Test
    @Transactional
    public void deleteDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);
        daySearchRepository.save(day);
        int databaseSizeBeforeDelete = dayRepository.findAll().size();

        // Get the day
        restDayMockMvc.perform(delete("/api/days/{id}", day.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean dayExistsInEs = daySearchRepository.exists(day.getId());
        assertThat(dayExistsInEs).isFalse();

        // Validate the database is empty
        List<Day> days = dayRepository.findAll();
        assertThat(days).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);
        daySearchRepository.save(day);

        // Search the day
        restDayMockMvc.perform(get("/api/_search/days?query=id:" + day.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(day.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
}
