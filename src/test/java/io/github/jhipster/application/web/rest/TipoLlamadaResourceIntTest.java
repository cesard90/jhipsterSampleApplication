package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TipoLlamada;
import io.github.jhipster.application.repository.TipoLlamadaRepository;
import io.github.jhipster.application.repository.search.TipoLlamadaSearchRepository;
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
 * Test class for the TipoLlamadaResource REST controller.
 *
 * @see TipoLlamadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TipoLlamadaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private TipoLlamadaRepository tipoLlamadaRepository;

    @Autowired
    private TipoLlamadaSearchRepository tipoLlamadaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoLlamadaMockMvc;

    private TipoLlamada tipoLlamada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoLlamadaResource tipoLlamadaResource = new TipoLlamadaResource(tipoLlamadaRepository, tipoLlamadaSearchRepository);
        this.restTipoLlamadaMockMvc = MockMvcBuilders.standaloneSetup(tipoLlamadaResource)
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
    public static TipoLlamada createEntity(EntityManager em) {
        TipoLlamada tipoLlamada = new TipoLlamada()
            .nombre(DEFAULT_NOMBRE)
            .precio(DEFAULT_PRECIO);
        return tipoLlamada;
    }

    @Before
    public void initTest() {
        tipoLlamadaSearchRepository.deleteAll();
        tipoLlamada = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoLlamada() throws Exception {
        int databaseSizeBeforeCreate = tipoLlamadaRepository.findAll().size();

        // Create the TipoLlamada
        restTipoLlamadaMockMvc.perform(post("/api/tipo-llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoLlamada)))
            .andExpect(status().isCreated());

        // Validate the TipoLlamada in the database
        List<TipoLlamada> tipoLlamadaList = tipoLlamadaRepository.findAll();
        assertThat(tipoLlamadaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoLlamada testTipoLlamada = tipoLlamadaList.get(tipoLlamadaList.size() - 1);
        assertThat(testTipoLlamada.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoLlamada.getPrecio()).isEqualTo(DEFAULT_PRECIO);

        // Validate the TipoLlamada in Elasticsearch
        TipoLlamada tipoLlamadaEs = tipoLlamadaSearchRepository.findOne(testTipoLlamada.getId());
        assertThat(tipoLlamadaEs).isEqualToIgnoringGivenFields(testTipoLlamada);
    }

    @Test
    @Transactional
    public void createTipoLlamadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoLlamadaRepository.findAll().size();

        // Create the TipoLlamada with an existing ID
        tipoLlamada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoLlamadaMockMvc.perform(post("/api/tipo-llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoLlamada)))
            .andExpect(status().isBadRequest());

        // Validate the TipoLlamada in the database
        List<TipoLlamada> tipoLlamadaList = tipoLlamadaRepository.findAll();
        assertThat(tipoLlamadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoLlamadaRepository.findAll().size();
        // set the field null
        tipoLlamada.setNombre(null);

        // Create the TipoLlamada, which fails.

        restTipoLlamadaMockMvc.perform(post("/api/tipo-llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoLlamada)))
            .andExpect(status().isBadRequest());

        List<TipoLlamada> tipoLlamadaList = tipoLlamadaRepository.findAll();
        assertThat(tipoLlamadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoLlamadas() throws Exception {
        // Initialize the database
        tipoLlamadaRepository.saveAndFlush(tipoLlamada);

        // Get all the tipoLlamadaList
        restTipoLlamadaMockMvc.perform(get("/api/tipo-llamadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoLlamada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getTipoLlamada() throws Exception {
        // Initialize the database
        tipoLlamadaRepository.saveAndFlush(tipoLlamada);

        // Get the tipoLlamada
        restTipoLlamadaMockMvc.perform(get("/api/tipo-llamadas/{id}", tipoLlamada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoLlamada.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoLlamada() throws Exception {
        // Get the tipoLlamada
        restTipoLlamadaMockMvc.perform(get("/api/tipo-llamadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoLlamada() throws Exception {
        // Initialize the database
        tipoLlamadaRepository.saveAndFlush(tipoLlamada);
        tipoLlamadaSearchRepository.save(tipoLlamada);
        int databaseSizeBeforeUpdate = tipoLlamadaRepository.findAll().size();

        // Update the tipoLlamada
        TipoLlamada updatedTipoLlamada = tipoLlamadaRepository.findOne(tipoLlamada.getId());
        // Disconnect from session so that the updates on updatedTipoLlamada are not directly saved in db
        em.detach(updatedTipoLlamada);
        updatedTipoLlamada
            .nombre(UPDATED_NOMBRE)
            .precio(UPDATED_PRECIO);

        restTipoLlamadaMockMvc.perform(put("/api/tipo-llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoLlamada)))
            .andExpect(status().isOk());

        // Validate the TipoLlamada in the database
        List<TipoLlamada> tipoLlamadaList = tipoLlamadaRepository.findAll();
        assertThat(tipoLlamadaList).hasSize(databaseSizeBeforeUpdate);
        TipoLlamada testTipoLlamada = tipoLlamadaList.get(tipoLlamadaList.size() - 1);
        assertThat(testTipoLlamada.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoLlamada.getPrecio()).isEqualTo(UPDATED_PRECIO);

        // Validate the TipoLlamada in Elasticsearch
        TipoLlamada tipoLlamadaEs = tipoLlamadaSearchRepository.findOne(testTipoLlamada.getId());
        assertThat(tipoLlamadaEs).isEqualToIgnoringGivenFields(testTipoLlamada);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoLlamada() throws Exception {
        int databaseSizeBeforeUpdate = tipoLlamadaRepository.findAll().size();

        // Create the TipoLlamada

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoLlamadaMockMvc.perform(put("/api/tipo-llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoLlamada)))
            .andExpect(status().isCreated());

        // Validate the TipoLlamada in the database
        List<TipoLlamada> tipoLlamadaList = tipoLlamadaRepository.findAll();
        assertThat(tipoLlamadaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoLlamada() throws Exception {
        // Initialize the database
        tipoLlamadaRepository.saveAndFlush(tipoLlamada);
        tipoLlamadaSearchRepository.save(tipoLlamada);
        int databaseSizeBeforeDelete = tipoLlamadaRepository.findAll().size();

        // Get the tipoLlamada
        restTipoLlamadaMockMvc.perform(delete("/api/tipo-llamadas/{id}", tipoLlamada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tipoLlamadaExistsInEs = tipoLlamadaSearchRepository.exists(tipoLlamada.getId());
        assertThat(tipoLlamadaExistsInEs).isFalse();

        // Validate the database is empty
        List<TipoLlamada> tipoLlamadaList = tipoLlamadaRepository.findAll();
        assertThat(tipoLlamadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTipoLlamada() throws Exception {
        // Initialize the database
        tipoLlamadaRepository.saveAndFlush(tipoLlamada);
        tipoLlamadaSearchRepository.save(tipoLlamada);

        // Search the tipoLlamada
        restTipoLlamadaMockMvc.perform(get("/api/_search/tipo-llamadas?query=id:" + tipoLlamada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoLlamada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoLlamada.class);
        TipoLlamada tipoLlamada1 = new TipoLlamada();
        tipoLlamada1.setId(1L);
        TipoLlamada tipoLlamada2 = new TipoLlamada();
        tipoLlamada2.setId(tipoLlamada1.getId());
        assertThat(tipoLlamada1).isEqualTo(tipoLlamada2);
        tipoLlamada2.setId(2L);
        assertThat(tipoLlamada1).isNotEqualTo(tipoLlamada2);
        tipoLlamada1.setId(null);
        assertThat(tipoLlamada1).isNotEqualTo(tipoLlamada2);
    }
}
