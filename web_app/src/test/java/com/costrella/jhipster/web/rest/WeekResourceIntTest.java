package com.costrella.jhipster.web.rest;

import com.costrella.jhipster.JhipsterApp;
import com.costrella.jhipster.domain.Week;
import com.costrella.jhipster.repository.WeekRepository;

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
 * Test class for the WeekResource REST controller.
 *
 * @see WeekResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class WeekResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE_BEFORE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BEFORE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_AFTER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AFTER = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_WEEK_OF_YEAR = 1;
    private static final Integer UPDATED_WEEK_OF_YEAR = 2;

    @Inject
    private WeekRepository weekRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWeekMockMvc;

    private Week week;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WeekResource weekResource = new WeekResource();
        ReflectionTestUtils.setField(weekResource, "weekRepository", weekRepository);
        this.restWeekMockMvc = MockMvcBuilders.standaloneSetup(weekResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Week createEntity(EntityManager em) {
        Week week = new Week();
        week = new Week()
                .name(DEFAULT_NAME)
                .dateBefore(DEFAULT_DATE_BEFORE)
                .dateAfter(DEFAULT_DATE_AFTER)
                .weekOfYear(DEFAULT_WEEK_OF_YEAR);
        return week;
    }

    @Before
    public void initTest() {
        week = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeek() throws Exception {
        int databaseSizeBeforeCreate = weekRepository.findAll().size();

        // Create the Week

        restWeekMockMvc.perform(post("/api/weeks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(week)))
                .andExpect(status().isCreated());

        // Validate the Week in the database
        List<Week> weeks = weekRepository.findAll();
        assertThat(weeks).hasSize(databaseSizeBeforeCreate + 1);
        Week testWeek = weeks.get(weeks.size() - 1);
        assertThat(testWeek.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWeek.getDateBefore()).isEqualTo(DEFAULT_DATE_BEFORE);
        assertThat(testWeek.getDateAfter()).isEqualTo(DEFAULT_DATE_AFTER);
        assertThat(testWeek.getWeekOfYear()).isEqualTo(DEFAULT_WEEK_OF_YEAR);
    }

    @Test
    @Transactional
    public void getAllWeeks() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        // Get all the weeks
        restWeekMockMvc.perform(get("/api/weeks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(week.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateBefore").value(hasItem(DEFAULT_DATE_BEFORE.toString())))
                .andExpect(jsonPath("$.[*].dateAfter").value(hasItem(DEFAULT_DATE_AFTER.toString())))
                .andExpect(jsonPath("$.[*].weekOfYear").value(hasItem(DEFAULT_WEEK_OF_YEAR)));
    }

    @Test
    @Transactional
    public void getWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        // Get the week
        restWeekMockMvc.perform(get("/api/weeks/{id}", week.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(week.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateBefore").value(DEFAULT_DATE_BEFORE.toString()))
            .andExpect(jsonPath("$.dateAfter").value(DEFAULT_DATE_AFTER.toString()))
            .andExpect(jsonPath("$.weekOfYear").value(DEFAULT_WEEK_OF_YEAR));
    }

    @Test
    @Transactional
    public void getNonExistingWeek() throws Exception {
        // Get the week
        restWeekMockMvc.perform(get("/api/weeks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);
        int databaseSizeBeforeUpdate = weekRepository.findAll().size();

        // Update the week
        Week updatedWeek = weekRepository.findOne(week.getId());
        updatedWeek
                .name(UPDATED_NAME)
                .dateBefore(UPDATED_DATE_BEFORE)
                .dateAfter(UPDATED_DATE_AFTER)
                .weekOfYear(UPDATED_WEEK_OF_YEAR);

        restWeekMockMvc.perform(put("/api/weeks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWeek)))
                .andExpect(status().isOk());

        // Validate the Week in the database
        List<Week> weeks = weekRepository.findAll();
        assertThat(weeks).hasSize(databaseSizeBeforeUpdate);
        Week testWeek = weeks.get(weeks.size() - 1);
        assertThat(testWeek.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWeek.getDateBefore()).isEqualTo(UPDATED_DATE_BEFORE);
        assertThat(testWeek.getDateAfter()).isEqualTo(UPDATED_DATE_AFTER);
        assertThat(testWeek.getWeekOfYear()).isEqualTo(UPDATED_WEEK_OF_YEAR);
    }

    @Test
    @Transactional
    public void deleteWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);
        int databaseSizeBeforeDelete = weekRepository.findAll().size();

        // Get the week
        restWeekMockMvc.perform(delete("/api/weeks/{id}", week.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Week> weeks = weekRepository.findAll();
        assertThat(weeks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
