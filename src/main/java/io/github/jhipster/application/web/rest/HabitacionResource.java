package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Habitacion;

import io.github.jhipster.application.repository.HabitacionRepository;
import io.github.jhipster.application.repository.search.HabitacionSearchRepository;
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
 * REST controller for managing Habitacion.
 */
@RestController
@RequestMapping("/api")
public class HabitacionResource {

    private final Logger log = LoggerFactory.getLogger(HabitacionResource.class);

    private static final String ENTITY_NAME = "habitacion";

    private final HabitacionRepository habitacionRepository;

    private final HabitacionSearchRepository habitacionSearchRepository;

    public HabitacionResource(HabitacionRepository habitacionRepository, HabitacionSearchRepository habitacionSearchRepository) {
        this.habitacionRepository = habitacionRepository;
        this.habitacionSearchRepository = habitacionSearchRepository;
    }

    /**
     * POST  /habitacions : Create a new habitacion.
     *
     * @param habitacion the habitacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new habitacion, or with status 400 (Bad Request) if the habitacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/habitacions")
    @Timed
    public ResponseEntity<Habitacion> createHabitacion(@Valid @RequestBody Habitacion habitacion) throws URISyntaxException {
        log.debug("REST request to save Habitacion : {}", habitacion);
        if (habitacion.getId() != null) {
            throw new BadRequestAlertException("A new habitacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Habitacion result = habitacionRepository.save(habitacion);
        habitacionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/habitacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /habitacions : Updates an existing habitacion.
     *
     * @param habitacion the habitacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated habitacion,
     * or with status 400 (Bad Request) if the habitacion is not valid,
     * or with status 500 (Internal Server Error) if the habitacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/habitacions")
    @Timed
    public ResponseEntity<Habitacion> updateHabitacion(@Valid @RequestBody Habitacion habitacion) throws URISyntaxException {
        log.debug("REST request to update Habitacion : {}", habitacion);
        if (habitacion.getId() == null) {
            return createHabitacion(habitacion);
        }
        Habitacion result = habitacionRepository.save(habitacion);
        habitacionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, habitacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /habitacions : get all the habitacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of habitacions in body
     */
    @GetMapping("/habitacions")
    @Timed
    public ResponseEntity<List<Habitacion>> getAllHabitacions(Pageable pageable) {
        log.debug("REST request to get a page of Habitacions");
        Page<Habitacion> page = habitacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/habitacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /habitacions/:id : get the "id" habitacion.
     *
     * @param id the id of the habitacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the habitacion, or with status 404 (Not Found)
     */
    @GetMapping("/habitacions/{id}")
    @Timed
    public ResponseEntity<Habitacion> getHabitacion(@PathVariable Long id) {
        log.debug("REST request to get Habitacion : {}", id);
        Habitacion habitacion = habitacionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(habitacion));
    }

    /**
     * DELETE  /habitacions/:id : delete the "id" habitacion.
     *
     * @param id the id of the habitacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/habitacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteHabitacion(@PathVariable Long id) {
        log.debug("REST request to delete Habitacion : {}", id);
        habitacionRepository.delete(id);
        habitacionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/habitacions?query=:query : search for the habitacion corresponding
     * to the query.
     *
     * @param query the query of the habitacion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/habitacions")
    @Timed
    public ResponseEntity<List<Habitacion>> searchHabitacions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Habitacions for query {}", query);
        Page<Habitacion> page = habitacionSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/habitacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
