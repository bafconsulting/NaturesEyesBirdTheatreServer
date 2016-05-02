package com.natureseyes.birdtheatre.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.natureseyes.birdtheatre.domain.CameraConfig;
import com.natureseyes.birdtheatre.repository.CameraConfigRepository;
import com.natureseyes.birdtheatre.repository.search.CameraConfigSearchRepository;
import com.natureseyes.birdtheatre.web.rest.util.HeaderUtil;
import com.natureseyes.birdtheatre.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CameraConfig.
 */
@RestController
@RequestMapping("/api")
public class CameraConfigResource {

    private final Logger log = LoggerFactory.getLogger(CameraConfigResource.class);
        
    @Inject
    private CameraConfigRepository cameraConfigRepository;
    
    @Inject
    private CameraConfigSearchRepository cameraConfigSearchRepository;
    
    /**
     * POST  /camera-configs : Create a new cameraConfig.
     *
     * @param cameraConfig the cameraConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cameraConfig, or with status 400 (Bad Request) if the cameraConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/camera-configs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CameraConfig> createCameraConfig(@Valid @RequestBody CameraConfig cameraConfig) throws URISyntaxException {
        log.debug("REST request to save CameraConfig : {}", cameraConfig);
        if (cameraConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cameraConfig", "idexists", "A new cameraConfig cannot already have an ID")).body(null);
        }
        CameraConfig result = cameraConfigRepository.save(cameraConfig);
        cameraConfigSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/camera-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cameraConfig", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /camera-configs : Updates an existing cameraConfig.
     *
     * @param cameraConfig the cameraConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cameraConfig,
     * or with status 400 (Bad Request) if the cameraConfig is not valid,
     * or with status 500 (Internal Server Error) if the cameraConfig couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/camera-configs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CameraConfig> updateCameraConfig(@Valid @RequestBody CameraConfig cameraConfig) throws URISyntaxException {
        log.debug("REST request to update CameraConfig : {}", cameraConfig);
        if (cameraConfig.getId() == null) {
            return createCameraConfig(cameraConfig);
        }
        CameraConfig result = cameraConfigRepository.save(cameraConfig);
        cameraConfigSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cameraConfig", cameraConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /camera-configs : get all the cameraConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cameraConfigs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/camera-configs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CameraConfig>> getAllCameraConfigs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CameraConfigs");
        Page<CameraConfig> page = cameraConfigRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/camera-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /camera-configs/:id : get the "id" cameraConfig.
     *
     * @param id the id of the cameraConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cameraConfig, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/camera-configs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CameraConfig> getCameraConfig(@PathVariable Long id) {
        log.debug("REST request to get CameraConfig : {}", id);
        CameraConfig cameraConfig = cameraConfigRepository.findOne(id);
        return Optional.ofNullable(cameraConfig)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /camera-configs/:id : delete the "id" cameraConfig.
     *
     * @param id the id of the cameraConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/camera-configs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCameraConfig(@PathVariable Long id) {
        log.debug("REST request to delete CameraConfig : {}", id);
        cameraConfigRepository.delete(id);
        cameraConfigSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cameraConfig", id.toString())).build();
    }

    /**
     * SEARCH  /_search/camera-configs?query=:query : search for the cameraConfig corresponding
     * to the query.
     *
     * @param query the query of the cameraConfig search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/camera-configs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CameraConfig>> searchCameraConfigs(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of CameraConfigs for query {}", query);
        Page<CameraConfig> page = cameraConfigSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/camera-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
