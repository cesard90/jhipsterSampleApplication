package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Hotel;

import io.github.jhipster.application.repository.HotelRepository;
import io.github.jhipster.application.repository.search.HotelSearchRepository;
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
 * REST controller for managing Hotel.
 */
@RestController
@RequestMapping("/api")
public class HotelResource {

    private final Logger log = LoggerFactory.getLogger(HotelResource.class);

    private static final String ENTITY_NAME = "hotel";

    private final HotelRepository hotelRepository;

    private final HotelSearchRepository hotelSearchRepository;

    public HotelResource(HotelRepository hotelRepository, HotelSearchRepository hotelSearchRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelSearchRepository = hotelSearchRepository;
    }

    /**
     * POST  /hotels : Create a new hotel.
     *
     * @param hotel the hotel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hotel, or with status 400 (Bad Request) if the hotel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hotels")
    @Timed
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to save Hotel : {}", hotel);
        if (hotel.getId() != null) {
            throw new BadRequestAlertException("A new hotel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hotel result = hotelRepository.save(hotel);
        hotelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hotels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hotels : Updates an existing hotel.
     *
     * @param hotel the hotel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hotel,
     * or with status 400 (Bad Request) if the hotel is not valid,
     * or with status 500 (Internal Server Error) if the hotel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hotels")
    @Timed
    public ResponseEntity<Hotel> updateHotel(@Valid @RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to update Hotel : {}", hotel);
        if (hotel.getId() == null) {
            return createHotel(hotel);
        }
        Hotel result = hotelRepository.save(hotel);
        hotelSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hotel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hotels : get all the hotels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hotels in body
     */
    @GetMapping("/hotels")
    @Timed
    public ResponseEntity<List<Hotel>> getAllHotels(Pageable pageable) {
        log.debug("REST request to get a page of Hotels");
        Page<Hotel> page = hotelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hotels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hotels/:id : get the "id" hotel.
     *
     * @param id the id of the hotel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hotel, or with status 404 (Not Found)
     */
    @GetMapping("/hotels/{id}")
    @Timed
    public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
        log.debug("REST request to get Hotel : {}", id);
        Hotel hotel = hotelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hotel));
    }

    /**
     * DELETE  /hotels/:id : delete the "id" hotel.
     *
     * @param id the id of the hotel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hotels/{id}")
    @Timed
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        log.debug("REST request to delete Hotel : {}", id);
        hotelRepository.delete(id);
        hotelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hotels?query=:query : search for the hotel corresponding
     * to the query.
     *
     * @param query the query of the hotel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/hotels")
    @Timed
    public ResponseEntity<List<Hotel>> searchHotels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Hotels for query {}", query);
        Page<Hotel> page = hotelSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hotels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
