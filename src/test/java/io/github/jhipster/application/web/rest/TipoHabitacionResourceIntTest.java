package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TipoHabitacion;
import io.github.jhipster.application.repository.TipoHabitacionRepository;
import io.github.jhipster.application.repository.search.TipoHabitacionSearchRepository;
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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoHabitacionResource REST controller.
 *
 * @see TipoHabitacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TipoHabitacionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private TipoHabitacionRepository tipoHabitacionRepository;

    @Autowired
    private TipoHabitacionSearchRepository tipoHabitacionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoHabitacionMockMvc;

    private TipoHabitacion tipoHabitacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoHabitacionResource tipoHabitacionResource = new TipoHabitacionResource(tipoHabitacionRepository, tipoHabitacionSearchRepository);
        this.restTipoHabitacionMockMvc = MockMvcBuilders.standaloneSetup(tipoHabitacionResource)
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
    public static TipoHabitacion createEntity(EntityManager em) {
        TipoHabitacion tipoHabitacion = new TipoHabitacion()
            .nombre(DEFAULT_NOMBRE)
            .precio(DEFAULT_PRECIO);
        return tipoHabitacion;
    }

    @Before
    public void initTest() {
        tipoHabitacionSearchRepository.deleteAll();
        tipoHabitacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoHabitacion() throws Exception {
        int databaseSizeBeforeCreate = tipoHabitacionRepository.findAll().size();

        // Create the TipoHabitacion
        restTipoHabitacionMockMvc.perform(post("/api/tipo-habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoHabitacion)))
            .andExpect(status().isCreated());

        // Validate the TipoHabitacion in the database
        List<TipoHabitacion> tipoHabitacionList = tipoHabitacionRepository.findAll();
        assertThat(tipoHabitacionList).hasSize(databaseSizeBeforeCreate + 1);
        TipoHabitacion testTipoHabitacion = tipoHabitacionList.get(tipoHabitacionList.size() - 1);
        assertThat(testTipoHabitacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoHabitacion.getPrecio()).isEqualTo(DEFAULT_PRECIO);

        // Validate the TipoHabitacion in Elasticsearch
        TipoHabitacion tipoHabitacionEs = tipoHabitacionSearchRepository.findOne(testTipoHabitacion.getId());
        assertThat(tipoHabitacionEs).isEqualToIgnoringGivenFields(testTipoHabitacion);
    }

    @Test
    @Transactional
    public void createTipoHabitacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoHabitacionRepository.findAll().size();

        // Create the TipoHabitacion with an existing ID
        tipoHabitacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoHabitacionMockMvc.perform(post("/api/tipo-habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoHabitacion)))
            .andExpect(status().isBadRequest());

        // Validate the TipoHabitacion in the database
        List<TipoHabitacion> tipoHabitacionList = tipoHabitacionRepository.findAll();
        assertThat(tipoHabitacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoHabitacions() throws Exception {
        // Initialize the database
        tipoHabitacionRepository.saveAndFlush(tipoHabitacion);

        // Get all the tipoHabitacionList
        restTipoHabitacionMockMvc.perform(get("/api/tipo-habitacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoHabitacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getTipoHabitacion() throws Exception {
        // Initialize the database
        tipoHabitacionRepository.saveAndFlush(tipoHabitacion);

        // Get the tipoHabitacion
        restTipoHabitacionMockMvc.perform(get("/api/tipo-habitacions/{id}", tipoHabitacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoHabitacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoHabitacion() throws Exception {
        // Get the tipoHabitacion
        restTipoHabitacionMockMvc.perform(get("/api/tipo-habitacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoHabitacion() throws Exception {
        // Initialize the database
        tipoHabitacionRepository.saveAndFlush(tipoHabitacion);
        tipoHabitacionSearchRepository.save(tipoHabitacion);
        int databaseSizeBeforeUpdate = tipoHabitacionRepository.findAll().size();

        // Update the tipoHabitacion
        TipoHabitacion updatedTipoHabitacion = tipoHabitacionRepository.findOne(tipoHabitacion.getId());
        // Disconnect from session so that the updates on updatedTipoHabitacion are not directly saved in db
        em.detach(updatedTipoHabitacion);
        updatedTipoHabitacion
            .nombre(UPDATED_NOMBRE)
            .precio(UPDATED_PRECIO);

        restTipoHabitacionMockMvc.perform(put("/api/tipo-habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoHabitacion)))
            .andExpect(status().isOk());

        // Validate the TipoHabitacion in the database
        List<TipoHabitacion> tipoHabitacionList = tipoHabitacionRepository.findAll();
        assertThat(tipoHabitacionList).hasSize(databaseSizeBeforeUpdate);
        TipoHabitacion testTipoHabitacion = tipoHabitacionList.get(tipoHabitacionList.size() - 1);
        assertThat(testTipoHabitacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoHabitacion.getPrecio()).isEqualTo(UPDATED_PRECIO);

        // Validate the TipoHabitacion in Elasticsearch
        TipoHabitacion tipoHabitacionEs = tipoHabitacionSearchRepository.findOne(testTipoHabitacion.getId());
        assertThat(tipoHabitacionEs).isEqualToIgnoringGivenFields(testTipoHabitacion);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = tipoHabitacionRepository.findAll().size();

        // Create the TipoHabitacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoHabitacionMockMvc.perform(put("/api/tipo-habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoHabitacion)))
            .andExpect(status().isCreated());

        // Validate the TipoHabitacion in the database
        List<TipoHabitacion> tipoHabitacionList = tipoHabitacionRepository.findAll();
        assertThat(tipoHabitacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoHabitacion() throws Exception {
        // Initialize the database
        tipoHabitacionRepository.saveAndFlush(tipoHabitacion);
        tipoHabitacionSearchRepository.save(tipoHabitacion);
        int databaseSizeBeforeDelete = tipoHabitacionRepository.findAll().size();

        // Get the tipoHabitacion
        restTipoHabitacionMockMvc.perform(delete("/api/tipo-habitacions/{id}", tipoHabitacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tipoHabitacionExistsInEs = tipoHabitacionSearchRepository.exists(tipoHabitacion.getId());
        assertThat(tipoHabitacionExistsInEs).isFalse();

        // Validate the database is empty
        List<TipoHabitacion> tipoHabitacionList = tipoHabitacionRepository.findAll();
        assertThat(tipoHabitacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTipoHabitacion() throws Exception {
        // Initialize the database
        tipoHabitacionRepository.saveAndFlush(tipoHabitacion);
        tipoHabitacionSearchRepository.save(tipoHabitacion);

        // Search the tipoHabitacion
        restTipoHabitacionMockMvc.perform(get("/api/_search/tipo-habitacions?query=id:" + tipoHabitacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoHabitacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoHabitacion.class);
        TipoHabitacion tipoHabitacion1 = new TipoHabitacion();
        tipoHabitacion1.setId(1L);
        TipoHabitacion tipoHabitacion2 = new TipoHabitacion();
        tipoHabitacion2.setId(tipoHabitacion1.getId());
        assertThat(tipoHabitacion1).isEqualTo(tipoHabitacion2);
        tipoHabitacion2.setId(2L);
        assertThat(tipoHabitacion1).isNotEqualTo(tipoHabitacion2);
        tipoHabitacion1.setId(null);
        assertThat(tipoHabitacion1).isNotEqualTo(tipoHabitacion2);
    }
}
