package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TipoLlamada;

import io.github.jhipster.application.repository.TipoLlamadaRepository;
import io.github.jhipster.application.repository.search.TipoLlamadaSearchRepository;
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
 * REST controller for managing TipoLlamada.
 */
@RestController
@RequestMapping("/api")
public class TipoLlamadaResource {

    private final Logger log = LoggerFactory.getLogger(TipoLlamadaResource.class);

    private static final String ENTITY_NAME = "tipoLlamada";

    private final TipoLlamadaRepository tipoLlamadaRepository;

    private final TipoLlamadaSearchRepository tipoLlamadaSearchRepository;

    public TipoLlamadaResource(TipoLlamadaRepository tipoLlamadaRepository, TipoLlamadaSearchRepository tipoLlamadaSearchRepository) {
        this.tipoLlamadaRepository = tipoLlamadaRepository;
        this.tipoLlamadaSearchRepository = tipoLlamadaSearchRepository;
    }

    /**
     * POST  /tipo-llamadas : Create a new tipoLlamada.
     *
     * @param tipoLlamada the tipoLlamada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoLlamada, or with status 400 (Bad Request) if the tipoLlamada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-llamadas")
    @Timed
    public ResponseEntity<TipoLlamada> createTipoLlamada(@Valid @RequestBody TipoLlamada tipoLlamada) throws URISyntaxException {
        log.debug("REST request to save TipoLlamada : {}", tipoLlamada);
        if (tipoLlamada.getId() != null) {
            throw new BadRequestAlertException("A new tipoLlamada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoLlamada result = tipoLlamadaRepository.save(tipoLlamada);
        tipoLlamadaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tipo-llamadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-llamadas : Updates an existing tipoLlamada.
     *
     * @param tipoLlamada the tipoLlamada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoLlamada,
     * or with status 400 (Bad Request) if the tipoLlamada is not valid,
     * or with status 500 (Internal Server Error) if the tipoLlamada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-llamadas")
    @Timed
    public ResponseEntity<TipoLlamada> updateTipoLlamada(@Valid @RequestBody TipoLlamada tipoLlamada) throws URISyntaxException {
        log.debug("REST request to update TipoLlamada : {}", tipoLlamada);
        if (tipoLlamada.getId() == null) {
            return createTipoLlamada(tipoLlamada);
        }
        TipoLlamada result = tipoLlamadaRepository.save(tipoLlamada);
        tipoLlamadaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoLlamada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-llamadas : get all the tipoLlamadas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoLlamadas in body
     */
    @GetMapping("/tipo-llamadas")
    @Timed
    public ResponseEntity<List<TipoLlamada>> getAllTipoLlamadas(Pageable pageable) {
        log.debug("REST request to get a page of TipoLlamadas");
        Page<TipoLlamada> page = tipoLlamadaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-llamadas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-llamadas/:id : get the "id" tipoLlamada.
     *
     * @param id the id of the tipoLlamada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoLlamada, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-llamadas/{id}")
    @Timed
    public ResponseEntity<TipoLlamada> getTipoLlamada(@PathVariable Long id) {
        log.debug("REST request to get TipoLlamada : {}", id);
        TipoLlamada tipoLlamada = tipoLlamadaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoLlamada));
    }

    /**
     * DELETE  /tipo-llamadas/:id : delete the "id" tipoLlamada.
     *
     * @param id the id of the tipoLlamada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-llamadas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoLlamada(@PathVariable Long id) {
        log.debug("REST request to delete TipoLlamada : {}", id);
        tipoLlamadaRepository.delete(id);
        tipoLlamadaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-llamadas?query=:query : search for the tipoLlamada corresponding
     * to the query.
     *
     * @param query the query of the tipoLlamada search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-llamadas")
    @Timed
    public ResponseEntity<List<TipoLlamada>> searchTipoLlamadas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoLlamadas for query {}", query);
        Page<TipoLlamada> page = tipoLlamadaSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-llamadas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
