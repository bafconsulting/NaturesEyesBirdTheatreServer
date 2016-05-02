package com.natureseyes.birdtheatre.web.rest;

import com.natureseyes.birdtheatre.BirdtheatreApp;
import com.natureseyes.birdtheatre.domain.BroadcastStream;
import com.natureseyes.birdtheatre.repository.BroadcastStreamRepository;
import com.natureseyes.birdtheatre.service.BroadcastStreamService;
import com.natureseyes.birdtheatre.repository.search.BroadcastStreamSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BroadcastStreamResource REST controller.
 *
 * @see BroadcastStreamResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BirdtheatreApp.class)
@WebAppConfiguration
@IntegrationTest
public class BroadcastStreamResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIMESTAMP_STR = dateTimeFormatter.format(DEFAULT_START_TIMESTAMP);

    private static final ZonedDateTime DEFAULT_END_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_TIMESTAMP_STR = dateTimeFormatter.format(DEFAULT_END_TIMESTAMP);

    @Inject
    private BroadcastStreamRepository broadcastStreamRepository;

    @Inject
    private BroadcastStreamService broadcastStreamService;

    @Inject
    private BroadcastStreamSearchRepository broadcastStreamSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBroadcastStreamMockMvc;

    private BroadcastStream broadcastStream;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BroadcastStreamResource broadcastStreamResource = new BroadcastStreamResource();
        ReflectionTestUtils.setField(broadcastStreamResource, "broadcastStreamService", broadcastStreamService);
        this.restBroadcastStreamMockMvc = MockMvcBuilders.standaloneSetup(broadcastStreamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        broadcastStreamSearchRepository.deleteAll();
        broadcastStream = new BroadcastStream();
        broadcastStream.setName(DEFAULT_NAME);
        broadcastStream.setDescription(DEFAULT_DESCRIPTION);
        broadcastStream.setStartTimestamp(DEFAULT_START_TIMESTAMP);
        broadcastStream.setEndTimestamp(DEFAULT_END_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createBroadcastStream() throws Exception {
        int databaseSizeBeforeCreate = broadcastStreamRepository.findAll().size();

        // Create the BroadcastStream

        restBroadcastStreamMockMvc.perform(post("/api/broadcast-streams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(broadcastStream)))
                .andExpect(status().isCreated());

        // Validate the BroadcastStream in the database
        List<BroadcastStream> broadcastStreams = broadcastStreamRepository.findAll();
        assertThat(broadcastStreams).hasSize(databaseSizeBeforeCreate + 1);
        BroadcastStream testBroadcastStream = broadcastStreams.get(broadcastStreams.size() - 1);
        assertThat(testBroadcastStream.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBroadcastStream.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBroadcastStream.getStartTimestamp()).isEqualTo(DEFAULT_START_TIMESTAMP);
        assertThat(testBroadcastStream.getEndTimestamp()).isEqualTo(DEFAULT_END_TIMESTAMP);

        // Validate the BroadcastStream in ElasticSearch
        BroadcastStream broadcastStreamEs = broadcastStreamSearchRepository.findOne(testBroadcastStream.getId());
        assertThat(broadcastStreamEs).isEqualToComparingFieldByField(testBroadcastStream);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = broadcastStreamRepository.findAll().size();
        // set the field null
        broadcastStream.setName(null);

        // Create the BroadcastStream, which fails.

        restBroadcastStreamMockMvc.perform(post("/api/broadcast-streams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(broadcastStream)))
                .andExpect(status().isBadRequest());

        List<BroadcastStream> broadcastStreams = broadcastStreamRepository.findAll();
        assertThat(broadcastStreams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = broadcastStreamRepository.findAll().size();
        // set the field null
        broadcastStream.setDescription(null);

        // Create the BroadcastStream, which fails.

        restBroadcastStreamMockMvc.perform(post("/api/broadcast-streams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(broadcastStream)))
                .andExpect(status().isBadRequest());

        List<BroadcastStream> broadcastStreams = broadcastStreamRepository.findAll();
        assertThat(broadcastStreams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = broadcastStreamRepository.findAll().size();
        // set the field null
        broadcastStream.setStartTimestamp(null);

        // Create the BroadcastStream, which fails.

        restBroadcastStreamMockMvc.perform(post("/api/broadcast-streams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(broadcastStream)))
                .andExpect(status().isBadRequest());

        List<BroadcastStream> broadcastStreams = broadcastStreamRepository.findAll();
        assertThat(broadcastStreams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = broadcastStreamRepository.findAll().size();
        // set the field null
        broadcastStream.setEndTimestamp(null);

        // Create the BroadcastStream, which fails.

        restBroadcastStreamMockMvc.perform(post("/api/broadcast-streams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(broadcastStream)))
                .andExpect(status().isBadRequest());

        List<BroadcastStream> broadcastStreams = broadcastStreamRepository.findAll();
        assertThat(broadcastStreams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBroadcastStreams() throws Exception {
        // Initialize the database
        broadcastStreamRepository.saveAndFlush(broadcastStream);

        // Get all the broadcastStreams
        restBroadcastStreamMockMvc.perform(get("/api/broadcast-streams?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(broadcastStream.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].startTimestamp").value(hasItem(DEFAULT_START_TIMESTAMP_STR)))
                .andExpect(jsonPath("$.[*].endTimestamp").value(hasItem(DEFAULT_END_TIMESTAMP_STR)));
    }

    @Test
    @Transactional
    public void getBroadcastStream() throws Exception {
        // Initialize the database
        broadcastStreamRepository.saveAndFlush(broadcastStream);

        // Get the broadcastStream
        restBroadcastStreamMockMvc.perform(get("/api/broadcast-streams/{id}", broadcastStream.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(broadcastStream.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startTimestamp").value(DEFAULT_START_TIMESTAMP_STR))
            .andExpect(jsonPath("$.endTimestamp").value(DEFAULT_END_TIMESTAMP_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBroadcastStream() throws Exception {
        // Get the broadcastStream
        restBroadcastStreamMockMvc.perform(get("/api/broadcast-streams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBroadcastStream() throws Exception {
        // Initialize the database
        broadcastStreamService.save(broadcastStream);

        int databaseSizeBeforeUpdate = broadcastStreamRepository.findAll().size();

        // Update the broadcastStream
        BroadcastStream updatedBroadcastStream = new BroadcastStream();
        updatedBroadcastStream.setId(broadcastStream.getId());
        updatedBroadcastStream.setName(UPDATED_NAME);
        updatedBroadcastStream.setDescription(UPDATED_DESCRIPTION);
        updatedBroadcastStream.setStartTimestamp(UPDATED_START_TIMESTAMP);
        updatedBroadcastStream.setEndTimestamp(UPDATED_END_TIMESTAMP);

        restBroadcastStreamMockMvc.perform(put("/api/broadcast-streams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBroadcastStream)))
                .andExpect(status().isOk());

        // Validate the BroadcastStream in the database
        List<BroadcastStream> broadcastStreams = broadcastStreamRepository.findAll();
        assertThat(broadcastStreams).hasSize(databaseSizeBeforeUpdate);
        BroadcastStream testBroadcastStream = broadcastStreams.get(broadcastStreams.size() - 1);
        assertThat(testBroadcastStream.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBroadcastStream.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBroadcastStream.getStartTimestamp()).isEqualTo(UPDATED_START_TIMESTAMP);
        assertThat(testBroadcastStream.getEndTimestamp()).isEqualTo(UPDATED_END_TIMESTAMP);

        // Validate the BroadcastStream in ElasticSearch
        BroadcastStream broadcastStreamEs = broadcastStreamSearchRepository.findOne(testBroadcastStream.getId());
        assertThat(broadcastStreamEs).isEqualToComparingFieldByField(testBroadcastStream);
    }

    @Test
    @Transactional
    public void deleteBroadcastStream() throws Exception {
        // Initialize the database
        broadcastStreamService.save(broadcastStream);

        int databaseSizeBeforeDelete = broadcastStreamRepository.findAll().size();

        // Get the broadcastStream
        restBroadcastStreamMockMvc.perform(delete("/api/broadcast-streams/{id}", broadcastStream.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean broadcastStreamExistsInEs = broadcastStreamSearchRepository.exists(broadcastStream.getId());
        assertThat(broadcastStreamExistsInEs).isFalse();

        // Validate the database is empty
        List<BroadcastStream> broadcastStreams = broadcastStreamRepository.findAll();
        assertThat(broadcastStreams).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBroadcastStream() throws Exception {
        // Initialize the database
        broadcastStreamService.save(broadcastStream);

        // Search the broadcastStream
        restBroadcastStreamMockMvc.perform(get("/api/_search/broadcast-streams?query=id:" + broadcastStream.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(broadcastStream.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startTimestamp").value(hasItem(DEFAULT_START_TIMESTAMP_STR)))
            .andExpect(jsonPath("$.[*].endTimestamp").value(hasItem(DEFAULT_END_TIMESTAMP_STR)));
    }
}
