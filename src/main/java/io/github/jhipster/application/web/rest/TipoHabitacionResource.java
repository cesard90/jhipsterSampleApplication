package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TipoHabitacion;

import io.github.jhipster.application.repository.TipoHabitacionRepository;
import io.github.jhipster.application.repository.search.TipoHabitacionSearchRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TipoHabitacion.
 */
@RestController
@RequestMapping("/api")
public class TipoHabitacionResource {

    private final Logger log = LoggerFactory.getLogger(TipoHabitacionResource.class);

    private static final String ENTITY_NAME = "tipoHabitacion";

    private final TipoHabitacionRepository tipoHabitacionRepository;

    private final TipoHabitacionSearchRepository tipoHabitacionSearchRepository;

    public TipoHabitacionResource(TipoHabitacionRepository tipoHabitacionRepository, TipoHabitacionSearchRepository tipoHabitacionSearchRepository) {
        this.tipoHabitacionRepository = tipoHabitacionRepository;
        this.tipoHabitacionSearchRepository = tipoHabitacionSearchRepository;
    }

    /**
     * POST  /tipo-habitacions : Create a new tipoHabitacion.
     *
     * @param tipoHabitacion the tipoHabitacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoHabitacion, or with status 400 (Bad Request) if the tipoHabitacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-habitacions")
    @Timed
    public ResponseEntity<TipoHabitacion> createTipoHabitacion(@RequestBody TipoHabitacion tipoHabitacion) throws URISyntaxException {
        log.debug("REST request to save TipoHabitacion : {}", tipoHabitacion);
        if (tipoHabitacion.getId() != null) {
            throw new BadRequestAlertException("A new tipoHabitacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoHabitacion result = tipoHabitacionRepository.save(tipoHabitacion);
        tipoHabitacionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tipo-habitacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-habitacions : Updates an existing tipoHabitacion.
     *
     * @param tipoHabitacion the tipoHabitacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoHabitacion,
     * or with status 400 (Bad Request) if the tipoHabitacion is not valid,
     * or with status 500 (Internal Server Error) if the tipoHabitacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-habitacions")
    @Timed
    public ResponseEntity<TipoHabitacion> updateTipoHabitacion(@RequestBody TipoHabitacion tipoHabitacion) throws URISyntaxException {
        log.debug("REST request to update TipoHabitacion : {}", tipoHabitacion);
        if (tipoHabitacion.getId() == null) {
            return createTipoHabitacion(tipoHabitacion);
        }
        TipoHabitacion result = tipoHabitacionRepository.save(tipoHabitacion);
        tipoHabitacionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoHabitacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-habitacions : get all the tipoHabitacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoHabitacions in body
     */
    @GetMapping("/tipo-habitacions")
    @Timed
    public ResponseEntity<List<TipoHabitacion>> getAllTipoHabitacions(Pageable pageable) {
        log.debug("REST request to get a page of TipoHabitacions");
        Page<TipoHabitacion> page = tipoHabitacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-habitacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-habitacions/:id : get the "id" tipoHabitacion.
     *
     * @param id the id of the tipoHabitacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoHabitacion, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-habitacions/{id}")
    @Timed
    public ResponseEntity<TipoHabitacion> getTipoHabitacion(@PathVariable Long id) {
        log.debug("REST request to get TipoHabitacion : {}", id);
        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoHabitacion));
    }

    /**
     * DELETE  /tipo-habitacions/:id : delete the "id" tipoHabitacion.
     *
     * @param id the id of the tipoHabitacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-habitacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoHabitacion(@PathVariable Long id) {
        log.debug("REST request to delete TipoHabitacion : {}", id);
        tipoHabitacionRepository.delete(id);
        tipoHabitacionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-habitacions?query=:query : search for the tipoHabitacion corresponding
     * to the query.
     *
     * @param query the query of the tipoHabitacion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-habitacions")
    @Timed
    public ResponseEntity<List<TipoHabitacion>> searchTipoHabitacions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoHabitacions for query {}", query);
        Page<TipoHabitacion> page = tipoHabitacionSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-habitacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
