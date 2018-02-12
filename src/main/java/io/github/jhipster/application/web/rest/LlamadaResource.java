package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Llamada;

import io.github.jhipster.application.repository.LlamadaRepository;
import io.github.jhipster.application.repository.search.LlamadaSearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Llamada.
 */
@RestController
@RequestMapping("/api")
public class LlamadaResource {

    private final Logger log = LoggerFactory.getLogger(LlamadaResource.class);

    private static final String ENTITY_NAME = "llamada";

    private final LlamadaRepository llamadaRepository;

    private final LlamadaSearchRepository llamadaSearchRepository;

    public LlamadaResource(LlamadaRepository llamadaRepository, LlamadaSearchRepository llamadaSearchRepository) {
        this.llamadaRepository = llamadaRepository;
        this.llamadaSearchRepository = llamadaSearchRepository;
    }

    /**
     * POST  /llamadas : Create a new llamada.
     *
     * @param llamada the llamada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new llamada, or with status 400 (Bad Request) if the llamada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/llamadas")
    @Timed
    public ResponseEntity<Llamada> createLlamada(@Valid @RequestBody Llamada llamada) throws URISyntaxException {
        log.debug("REST request to save Llamada : {}", llamada);
        if (llamada.getId() != null) {
            throw new BadRequestAlertException("A new llamada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Llamada result = llamadaRepository.save(llamada);
        llamadaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/llamadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /llamadas : Updates an existing llamada.
     *
     * @param llamada the llamada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated llamada,
     * or with status 400 (Bad Request) if the llamada is not valid,
     * or with status 500 (Internal Server Error) if the llamada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/llamadas")
    @Timed
    public ResponseEntity<Llamada> updateLlamada(@Valid @RequestBody Llamada llamada) throws URISyntaxException {
        log.debug("REST request to update Llamada : {}", llamada);
        if (llamada.getId() == null) {
            return createLlamada(llamada);
        }
        Llamada result = llamadaRepository.save(llamada);
        llamadaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, llamada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /llamadas : get all the llamadas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of llamadas in body
     */
    @GetMapping("/llamadas")
    @Timed
    public ResponseEntity<List<Llamada>> getAllLlamadas(Pageable pageable) {
        log.debug("REST request to get a page of Llamadas");
        Page<Llamada> page = llamadaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/llamadas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /llamadas/:id : get the "id" llamada.
     *
     * @param id the id of the llamada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the llamada, or with status 404 (Not Found)
     */
    @GetMapping("/llamadas/{id}")
    @Timed
    public ResponseEntity<Llamada> getLlamada(@PathVariable Long id) {
        log.debug("REST request to get Llamada : {}", id);
        Llamada llamada = llamadaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(llamada));
    }

    /**
     * DELETE  /llamadas/:id : delete the "id" llamada.
     *
     * @param id the id of the llamada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/llamadas/{id}")
    @Timed
    public ResponseEntity<Void> deleteLlamada(@PathVariable Long id) {
        log.debug("REST request to delete Llamada : {}", id);
        llamadaRepository.delete(id);
        llamadaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/llamadas?query=:query : search for the llamada corresponding
     * to the query.
     *
     * @param query the query of the llamada search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/llamadas")
    @Timed
    public ResponseEntity<List<Llamada>> searchLlamadas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Llamadas for query {}", query);
        Page<Llamada> page = llamadaSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/llamadas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
