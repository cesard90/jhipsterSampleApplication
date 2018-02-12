package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Reserva;

import io.github.jhipster.application.repository.ReservaRepository;
import io.github.jhipster.application.repository.search.ReservaSearchRepository;
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
 * REST controller for managing Reserva.
 */
@RestController
@RequestMapping("/api")
public class ReservaResource {

    private final Logger log = LoggerFactory.getLogger(ReservaResource.class);

    private static final String ENTITY_NAME = "reserva";

    private final ReservaRepository reservaRepository;

    private final ReservaSearchRepository reservaSearchRepository;

    public ReservaResource(ReservaRepository reservaRepository, ReservaSearchRepository reservaSearchRepository) {
        this.reservaRepository = reservaRepository;
        this.reservaSearchRepository = reservaSearchRepository;
    }

    /**
     * POST  /reservas : Create a new reserva.
     *
     * @param reserva the reserva to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reserva, or with status 400 (Bad Request) if the reserva has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reservas")
    @Timed
    public ResponseEntity<Reserva> createReserva(@Valid @RequestBody Reserva reserva) throws URISyntaxException {
        log.debug("REST request to save Reserva : {}", reserva);
        if (reserva.getId() != null) {
            throw new BadRequestAlertException("A new reserva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reserva result = reservaRepository.save(reserva);
        reservaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reservas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reservas : Updates an existing reserva.
     *
     * @param reserva the reserva to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reserva,
     * or with status 400 (Bad Request) if the reserva is not valid,
     * or with status 500 (Internal Server Error) if the reserva couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reservas")
    @Timed
    public ResponseEntity<Reserva> updateReserva(@Valid @RequestBody Reserva reserva) throws URISyntaxException {
        log.debug("REST request to update Reserva : {}", reserva);
        if (reserva.getId() == null) {
            return createReserva(reserva);
        }
        Reserva result = reservaRepository.save(reserva);
        reservaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reserva.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reservas : get all the reservas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reservas in body
     */
    @GetMapping("/reservas")
    @Timed
    public ResponseEntity<List<Reserva>> getAllReservas(Pageable pageable) {
        log.debug("REST request to get a page of Reservas");
        Page<Reserva> page = reservaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reservas/:id : get the "id" reserva.
     *
     * @param id the id of the reserva to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reserva, or with status 404 (Not Found)
     */
    @GetMapping("/reservas/{id}")
    @Timed
    public ResponseEntity<Reserva> getReserva(@PathVariable Long id) {
        log.debug("REST request to get Reserva : {}", id);
        Reserva reserva = reservaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reserva));
    }

    /**
     * DELETE  /reservas/:id : delete the "id" reserva.
     *
     * @param id the id of the reserva to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reservas/{id}")
    @Timed
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        log.debug("REST request to delete Reserva : {}", id);
        reservaRepository.delete(id);
        reservaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reservas?query=:query : search for the reserva corresponding
     * to the query.
     *
     * @param query the query of the reserva search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/reservas")
    @Timed
    public ResponseEntity<List<Reserva>> searchReservas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Reservas for query {}", query);
        Page<Reserva> page = reservaSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/reservas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
