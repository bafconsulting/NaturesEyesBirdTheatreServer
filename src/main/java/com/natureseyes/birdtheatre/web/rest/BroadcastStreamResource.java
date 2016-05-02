package com.natureseyes.birdtheatre.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.natureseyes.birdtheatre.domain.BroadcastStream;
import com.natureseyes.birdtheatre.service.BroadcastStreamService;
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
 * REST controller for managing BroadcastStream.
 */
@RestController
@RequestMapping("/api")
public class BroadcastStreamResource {

    private final Logger log = LoggerFactory.getLogger(BroadcastStreamResource.class);
        
    @Inject
    private BroadcastStreamService broadcastStreamService;
    
    /**
     * POST  /broadcast-streams : Create a new broadcastStream.
     *
     * @param broadcastStream the broadcastStream to create
     * @return the ResponseEntity with status 201 (Created) and with body the new broadcastStream, or with status 400 (Bad Request) if the broadcastStream has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/broadcast-streams",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BroadcastStream> createBroadcastStream(@Valid @RequestBody BroadcastStream broadcastStream) throws URISyntaxException {
        log.debug("REST request to save BroadcastStream : {}", broadcastStream);
        if (broadcastStream.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("broadcastStream", "idexists", "A new broadcastStream cannot already have an ID")).body(null);
        }
        BroadcastStream result = broadcastStreamService.save(broadcastStream);
        return ResponseEntity.created(new URI("/api/broadcast-streams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("broadcastStream", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /broadcast-streams : Updates an existing broadcastStream.
     *
     * @param broadcastStream the broadcastStream to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated broadcastStream,
     * or with status 400 (Bad Request) if the broadcastStream is not valid,
     * or with status 500 (Internal Server Error) if the broadcastStream couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/broadcast-streams",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BroadcastStream> updateBroadcastStream(@Valid @RequestBody BroadcastStream broadcastStream) throws URISyntaxException {
        log.debug("REST request to update BroadcastStream : {}", broadcastStream);
        if (broadcastStream.getId() == null) {
            return createBroadcastStream(broadcastStream);
        }
        BroadcastStream result = broadcastStreamService.save(broadcastStream);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("broadcastStream", broadcastStream.getId().toString()))
            .body(result);
    }

    /**
     * GET  /broadcast-streams : get all the broadcastStreams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of broadcastStreams in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/broadcast-streams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BroadcastStream>> getAllBroadcastStreams(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BroadcastStreams");
        Page<BroadcastStream> page = broadcastStreamService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/broadcast-streams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /broadcast-streams/:id : get the "id" broadcastStream.
     *
     * @param id the id of the broadcastStream to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the broadcastStream, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/broadcast-streams/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BroadcastStream> getBroadcastStream(@PathVariable Long id) {
        log.debug("REST request to get BroadcastStream : {}", id);
        BroadcastStream broadcastStream = broadcastStreamService.findOne(id);
        return Optional.ofNullable(broadcastStream)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /broadcast-streams/:id : delete the "id" broadcastStream.
     *
     * @param id the id of the broadcastStream to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/broadcast-streams/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBroadcastStream(@PathVariable Long id) {
        log.debug("REST request to delete BroadcastStream : {}", id);
        broadcastStreamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("broadcastStream", id.toString())).build();
    }

    /**
     * SEARCH  /_search/broadcast-streams?query=:query : search for the broadcastStream corresponding
     * to the query.
     *
     * @param query the query of the broadcastStream search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/broadcast-streams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BroadcastStream>> searchBroadcastStreams(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of BroadcastStreams for query {}", query);
        Page<BroadcastStream> page = broadcastStreamService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/broadcast-streams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
