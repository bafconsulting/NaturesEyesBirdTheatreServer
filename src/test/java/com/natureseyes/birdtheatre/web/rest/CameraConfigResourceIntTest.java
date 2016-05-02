package com.natureseyes.birdtheatre.web.rest;

import com.natureseyes.birdtheatre.BirdtheatreApp;
import com.natureseyes.birdtheatre.domain.CameraConfig;
import com.natureseyes.birdtheatre.repository.CameraConfigRepository;
import com.natureseyes.birdtheatre.repository.search.CameraConfigSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CameraConfigResource REST controller.
 *
 * @see CameraConfigResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BirdtheatreApp.class)
@WebAppConfiguration
@IntegrationTest
public class CameraConfigResourceIntTest {

    private static final String DEFAULT_HOSTNAME = "AAA";
    private static final String UPDATED_HOSTNAME = "BBB";
    private static final String DEFAULT_IPADDRESS = "AAA";
    private static final String UPDATED_IPADDRESS = "BBB";

    @Inject
    private CameraConfigRepository cameraConfigRepository;

    @Inject
    private CameraConfigSearchRepository cameraConfigSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCameraConfigMockMvc;

    private CameraConfig cameraConfig;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CameraConfigResource cameraConfigResource = new CameraConfigResource();
        ReflectionTestUtils.setField(cameraConfigResource, "cameraConfigSearchRepository", cameraConfigSearchRepository);
        ReflectionTestUtils.setField(cameraConfigResource, "cameraConfigRepository", cameraConfigRepository);
        this.restCameraConfigMockMvc = MockMvcBuilders.standaloneSetup(cameraConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cameraConfigSearchRepository.deleteAll();
        cameraConfig = new CameraConfig();
        cameraConfig.setHostname(DEFAULT_HOSTNAME);
        cameraConfig.setIpaddress(DEFAULT_IPADDRESS);
    }

    @Test
    @Transactional
    public void createCameraConfig() throws Exception {
        int databaseSizeBeforeCreate = cameraConfigRepository.findAll().size();

        // Create the CameraConfig

        restCameraConfigMockMvc.perform(post("/api/camera-configs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cameraConfig)))
                .andExpect(status().isCreated());

        // Validate the CameraConfig in the database
        List<CameraConfig> cameraConfigs = cameraConfigRepository.findAll();
        assertThat(cameraConfigs).hasSize(databaseSizeBeforeCreate + 1);
        CameraConfig testCameraConfig = cameraConfigs.get(cameraConfigs.size() - 1);
        assertThat(testCameraConfig.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testCameraConfig.getIpaddress()).isEqualTo(DEFAULT_IPADDRESS);

        // Validate the CameraConfig in ElasticSearch
        CameraConfig cameraConfigEs = cameraConfigSearchRepository.findOne(testCameraConfig.getId());
        assertThat(cameraConfigEs).isEqualToComparingFieldByField(testCameraConfig);
    }

    @Test
    @Transactional
    public void checkHostnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraConfigRepository.findAll().size();
        // set the field null
        cameraConfig.setHostname(null);

        // Create the CameraConfig, which fails.

        restCameraConfigMockMvc.perform(post("/api/camera-configs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cameraConfig)))
                .andExpect(status().isBadRequest());

        List<CameraConfig> cameraConfigs = cameraConfigRepository.findAll();
        assertThat(cameraConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIpaddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraConfigRepository.findAll().size();
        // set the field null
        cameraConfig.setIpaddress(null);

        // Create the CameraConfig, which fails.

        restCameraConfigMockMvc.perform(post("/api/camera-configs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cameraConfig)))
                .andExpect(status().isBadRequest());

        List<CameraConfig> cameraConfigs = cameraConfigRepository.findAll();
        assertThat(cameraConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCameraConfigs() throws Exception {
        // Initialize the database
        cameraConfigRepository.saveAndFlush(cameraConfig);

        // Get all the cameraConfigs
        restCameraConfigMockMvc.perform(get("/api/camera-configs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cameraConfig.getId().intValue())))
                .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME.toString())))
                .andExpect(jsonPath("$.[*].ipaddress").value(hasItem(DEFAULT_IPADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getCameraConfig() throws Exception {
        // Initialize the database
        cameraConfigRepository.saveAndFlush(cameraConfig);

        // Get the cameraConfig
        restCameraConfigMockMvc.perform(get("/api/camera-configs/{id}", cameraConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cameraConfig.getId().intValue()))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME.toString()))
            .andExpect(jsonPath("$.ipaddress").value(DEFAULT_IPADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCameraConfig() throws Exception {
        // Get the cameraConfig
        restCameraConfigMockMvc.perform(get("/api/camera-configs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCameraConfig() throws Exception {
        // Initialize the database
        cameraConfigRepository.saveAndFlush(cameraConfig);
        cameraConfigSearchRepository.save(cameraConfig);
        int databaseSizeBeforeUpdate = cameraConfigRepository.findAll().size();

        // Update the cameraConfig
        CameraConfig updatedCameraConfig = new CameraConfig();
        updatedCameraConfig.setId(cameraConfig.getId());
        updatedCameraConfig.setHostname(UPDATED_HOSTNAME);
        updatedCameraConfig.setIpaddress(UPDATED_IPADDRESS);

        restCameraConfigMockMvc.perform(put("/api/camera-configs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCameraConfig)))
                .andExpect(status().isOk());

        // Validate the CameraConfig in the database
        List<CameraConfig> cameraConfigs = cameraConfigRepository.findAll();
        assertThat(cameraConfigs).hasSize(databaseSizeBeforeUpdate);
        CameraConfig testCameraConfig = cameraConfigs.get(cameraConfigs.size() - 1);
        assertThat(testCameraConfig.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testCameraConfig.getIpaddress()).isEqualTo(UPDATED_IPADDRESS);

        // Validate the CameraConfig in ElasticSearch
        CameraConfig cameraConfigEs = cameraConfigSearchRepository.findOne(testCameraConfig.getId());
        assertThat(cameraConfigEs).isEqualToComparingFieldByField(testCameraConfig);
    }

    @Test
    @Transactional
    public void deleteCameraConfig() throws Exception {
        // Initialize the database
        cameraConfigRepository.saveAndFlush(cameraConfig);
        cameraConfigSearchRepository.save(cameraConfig);
        int databaseSizeBeforeDelete = cameraConfigRepository.findAll().size();

        // Get the cameraConfig
        restCameraConfigMockMvc.perform(delete("/api/camera-configs/{id}", cameraConfig.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean cameraConfigExistsInEs = cameraConfigSearchRepository.exists(cameraConfig.getId());
        assertThat(cameraConfigExistsInEs).isFalse();

        // Validate the database is empty
        List<CameraConfig> cameraConfigs = cameraConfigRepository.findAll();
        assertThat(cameraConfigs).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCameraConfig() throws Exception {
        // Initialize the database
        cameraConfigRepository.saveAndFlush(cameraConfig);
        cameraConfigSearchRepository.save(cameraConfig);

        // Search the cameraConfig
        restCameraConfigMockMvc.perform(get("/api/_search/camera-configs?query=id:" + cameraConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cameraConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME.toString())))
            .andExpect(jsonPath("$.[*].ipaddress").value(hasItem(DEFAULT_IPADDRESS.toString())));
    }
}
