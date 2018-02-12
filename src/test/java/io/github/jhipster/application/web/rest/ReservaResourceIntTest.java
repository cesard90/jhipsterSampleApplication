package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Reserva;
import io.github.jhipster.application.domain.TipoHabitacion;
import io.github.jhipster.application.repository.ReservaRepository;
import io.github.jhipster.application.repository.search.ReservaSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.sameInstant;
import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReservaResource REST controller.
 *
 * @see ReservaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ReservaResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_RESERVA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_RESERVA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CAMA_ADICIONAL = false;
    private static final Boolean UPDATED_CAMA_ADICIONAL = true;

    private static final Double DEFAULT_PRECIO_VENTA_RESERVA = 1D;
    private static final Double UPDATED_PRECIO_VENTA_RESERVA = 2D;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaSearchRepository reservaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReservaMockMvc;

    private Reserva reserva;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReservaResource reservaResource = new ReservaResource(reservaRepository, reservaSearchRepository);
        this.restReservaMockMvc = MockMvcBuilders.standaloneSetup(reservaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reserva createEntity(EntityManager em) {
        Reserva reserva = new Reserva()
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .fechaReserva(DEFAULT_FECHA_RESERVA)
            .camaAdicional(DEFAULT_CAMA_ADICIONAL)
            .precioVentaReserva(DEFAULT_PRECIO_VENTA_RESERVA);
        // Add required entity
        TipoHabitacion tipoHabitacion = TipoHabitacionResourceIntTest.createEntity(em);
        em.persist(tipoHabitacion);
        em.flush();
        reserva.setTipoHabitacion(tipoHabitacion);
        return reserva;
    }

    @Before
    public void initTest() {
        reservaSearchRepository.deleteAll();
        reserva = createEntity(em);
    }

    @Test
    @Transactional
    public void createReserva() throws Exception {
        int databaseSizeBeforeCreate = reservaRepository.findAll().size();

        // Create the Reserva
        restReservaMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isCreated());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeCreate + 1);
        Reserva testReserva = reservaList.get(reservaList.size() - 1);
        assertThat(testReserva.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testReserva.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testReserva.getFechaReserva()).isEqualTo(DEFAULT_FECHA_RESERVA);
        assertThat(testReserva.isCamaAdicional()).isEqualTo(DEFAULT_CAMA_ADICIONAL);
        assertThat(testReserva.getPrecioVentaReserva()).isEqualTo(DEFAULT_PRECIO_VENTA_RESERVA);

        // Validate the Reserva in Elasticsearch
        Reserva reservaEs = reservaSearchRepository.findOne(testReserva.getId());
        assertThat(testReserva.getFechaInicio()).isEqualTo(testReserva.getFechaInicio());
        assertThat(testReserva.getFechaFin()).isEqualTo(testReserva.getFechaFin());
        assertThat(testReserva.getFechaReserva()).isEqualTo(testReserva.getFechaReserva());
        assertThat(reservaEs).isEqualToIgnoringGivenFields(testReserva, "fechaInicio", "fechaFin", "fechaReserva");
    }

    @Test
    @Transactional
    public void createReservaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservaRepository.findAll().size();

        // Create the Reserva with an existing ID
        reserva.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservaMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isBadRequest());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservaRepository.findAll().size();
        // set the field null
        reserva.setFechaInicio(null);

        // Create the Reserva, which fails.

        restReservaMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isBadRequest());

        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservaRepository.findAll().size();
        // set the field null
        reserva.setFechaFin(null);

        // Create the Reserva, which fails.

        restReservaMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isBadRequest());

        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReservas() throws Exception {
        // Initialize the database
        reservaRepository.saveAndFlush(reserva);

        // Get all the reservaList
        restReservaMockMvc.perform(get("/api/reservas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reserva.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(sameInstant(DEFAULT_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(sameInstant(DEFAULT_FECHA_FIN))))
            .andExpect(jsonPath("$.[*].fechaReserva").value(hasItem(sameInstant(DEFAULT_FECHA_RESERVA))))
            .andExpect(jsonPath("$.[*].camaAdicional").value(hasItem(DEFAULT_CAMA_ADICIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].precioVentaReserva").value(hasItem(DEFAULT_PRECIO_VENTA_RESERVA.doubleValue())));
    }

    @Test
    @Transactional
    public void getReserva() throws Exception {
        // Initialize the database
        reservaRepository.saveAndFlush(reserva);

        // Get the reserva
        restReservaMockMvc.perform(get("/api/reservas/{id}", reserva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reserva.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(sameInstant(DEFAULT_FECHA_INICIO)))
            .andExpect(jsonPath("$.fechaFin").value(sameInstant(DEFAULT_FECHA_FIN)))
            .andExpect(jsonPath("$.fechaReserva").value(sameInstant(DEFAULT_FECHA_RESERVA)))
            .andExpect(jsonPath("$.camaAdicional").value(DEFAULT_CAMA_ADICIONAL.booleanValue()))
            .andExpect(jsonPath("$.precioVentaReserva").value(DEFAULT_PRECIO_VENTA_RESERVA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReserva() throws Exception {
        // Get the reserva
        restReservaMockMvc.perform(get("/api/reservas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReserva() throws Exception {
        // Initialize the database
        reservaRepository.saveAndFlush(reserva);
        reservaSearchRepository.save(reserva);
        int databaseSizeBeforeUpdate = reservaRepository.findAll().size();

        // Update the reserva
        Reserva updatedReserva = reservaRepository.findOne(reserva.getId());
        // Disconnect from session so that the updates on updatedReserva are not directly saved in db
        em.detach(updatedReserva);
        updatedReserva
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .fechaReserva(UPDATED_FECHA_RESERVA)
            .camaAdicional(UPDATED_CAMA_ADICIONAL)
            .precioVentaReserva(UPDATED_PRECIO_VENTA_RESERVA);

        restReservaMockMvc.perform(put("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReserva)))
            .andExpect(status().isOk());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeUpdate);
        Reserva testReserva = reservaList.get(reservaList.size() - 1);
        assertThat(testReserva.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReserva.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testReserva.getFechaReserva()).isEqualTo(UPDATED_FECHA_RESERVA);
        assertThat(testReserva.isCamaAdicional()).isEqualTo(UPDATED_CAMA_ADICIONAL);
        assertThat(testReserva.getPrecioVentaReserva()).isEqualTo(UPDATED_PRECIO_VENTA_RESERVA);

        // Validate the Reserva in Elasticsearch
        Reserva reservaEs = reservaSearchRepository.findOne(testReserva.getId());
        assertThat(testReserva.getFechaInicio()).isEqualTo(testReserva.getFechaInicio());
        assertThat(testReserva.getFechaFin()).isEqualTo(testReserva.getFechaFin());
        assertThat(testReserva.getFechaReserva()).isEqualTo(testReserva.getFechaReserva());
        assertThat(reservaEs).isEqualToIgnoringGivenFields(testReserva, "fechaInicio", "fechaFin", "fechaReserva");
    }

    @Test
    @Transactional
    public void updateNonExistingReserva() throws Exception {
        int databaseSizeBeforeUpdate = reservaRepository.findAll().size();

        // Create the Reserva

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReservaMockMvc.perform(put("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isCreated());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReserva() throws Exception {
        // Initialize the database
        reservaRepository.saveAndFlush(reserva);
        reservaSearchRepository.save(reserva);
        int databaseSizeBeforeDelete = reservaRepository.findAll().size();

        // Get the reserva
        restReservaMockMvc.perform(delete("/api/reservas/{id}", reserva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean reservaExistsInEs = reservaSearchRepository.exists(reserva.getId());
        assertThat(reservaExistsInEs).isFalse();

        // Validate the database is empty
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReserva() throws Exception {
        // Initialize the database
        reservaRepository.saveAndFlush(reserva);
        reservaSearchRepository.save(reserva);

        // Search the reserva
        restReservaMockMvc.perform(get("/api/_search/reservas?query=id:" + reserva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reserva.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(sameInstant(DEFAULT_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(sameInstant(DEFAULT_FECHA_FIN))))
            .andExpect(jsonPath("$.[*].fechaReserva").value(hasItem(sameInstant(DEFAULT_FECHA_RESERVA))))
            .andExpect(jsonPath("$.[*].camaAdicional").value(hasItem(DEFAULT_CAMA_ADICIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].precioVentaReserva").value(hasItem(DEFAULT_PRECIO_VENTA_RESERVA.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reserva.class);
        Reserva reserva1 = new Reserva();
        reserva1.setId(1L);
        Reserva reserva2 = new Reserva();
        reserva2.setId(reserva1.getId());
        assertThat(reserva1).isEqualTo(reserva2);
        reserva2.setId(2L);
        assertThat(reserva1).isNotEqualTo(reserva2);
        reserva1.setId(null);
        assertThat(reserva1).isNotEqualTo(reserva2);
    }
}
