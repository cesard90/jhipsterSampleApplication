package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Llamada;
import io.github.jhipster.application.domain.TipoLlamada;
import io.github.jhipster.application.repository.LlamadaRepository;
import io.github.jhipster.application.repository.search.LlamadaSearchRepository;
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
 * Test class for the LlamadaResource REST controller.
 *
 * @see LlamadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class LlamadaResourceIntTest {

    private static final Double DEFAULT_MINUTOS = 1D;
    private static final Double UPDATED_MINUTOS = 2D;

    @Autowired
    private LlamadaRepository llamadaRepository;

    @Autowired
    private LlamadaSearchRepository llamadaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLlamadaMockMvc;

    private Llamada llamada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LlamadaResource llamadaResource = new LlamadaResource(llamadaRepository, llamadaSearchRepository);
        this.restLlamadaMockMvc = MockMvcBuilders.standaloneSetup(llamadaResource)
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
    public static Llamada createEntity(EntityManager em) {
        Llamada llamada = new Llamada()
            .minutos(DEFAULT_MINUTOS);
        // Add required entity
        TipoLlamada tipoLlamada = TipoLlamadaResourceIntTest.createEntity(em);
        em.persist(tipoLlamada);
        em.flush();
        llamada.setTipoLlamada(tipoLlamada);
        return llamada;
    }

    @Before
    public void initTest() {
        llamadaSearchRepository.deleteAll();
        llamada = createEntity(em);
    }

    @Test
    @Transactional
    public void createLlamada() throws Exception {
        int databaseSizeBeforeCreate = llamadaRepository.findAll().size();

        // Create the Llamada
        restLlamadaMockMvc.perform(post("/api/llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(llamada)))
            .andExpect(status().isCreated());

        // Validate the Llamada in the database
        List<Llamada> llamadaList = llamadaRepository.findAll();
        assertThat(llamadaList).hasSize(databaseSizeBeforeCreate + 1);
        Llamada testLlamada = llamadaList.get(llamadaList.size() - 1);
        assertThat(testLlamada.getMinutos()).isEqualTo(DEFAULT_MINUTOS);

        // Validate the Llamada in Elasticsearch
        Llamada llamadaEs = llamadaSearchRepository.findOne(testLlamada.getId());
        assertThat(llamadaEs).isEqualToIgnoringGivenFields(testLlamada);
    }

    @Test
    @Transactional
    public void createLlamadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = llamadaRepository.findAll().size();

        // Create the Llamada with an existing ID
        llamada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLlamadaMockMvc.perform(post("/api/llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(llamada)))
            .andExpect(status().isBadRequest());

        // Validate the Llamada in the database
        List<Llamada> llamadaList = llamadaRepository.findAll();
        assertThat(llamadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLlamadas() throws Exception {
        // Initialize the database
        llamadaRepository.saveAndFlush(llamada);

        // Get all the llamadaList
        restLlamadaMockMvc.perform(get("/api/llamadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(llamada.getId().intValue())))
            .andExpect(jsonPath("$.[*].minutos").value(hasItem(DEFAULT_MINUTOS.doubleValue())));
    }

    @Test
    @Transactional
    public void getLlamada() throws Exception {
        // Initialize the database
        llamadaRepository.saveAndFlush(llamada);

        // Get the llamada
        restLlamadaMockMvc.perform(get("/api/llamadas/{id}", llamada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(llamada.getId().intValue()))
            .andExpect(jsonPath("$.minutos").value(DEFAULT_MINUTOS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLlamada() throws Exception {
        // Get the llamada
        restLlamadaMockMvc.perform(get("/api/llamadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLlamada() throws Exception {
        // Initialize the database
        llamadaRepository.saveAndFlush(llamada);
        llamadaSearchRepository.save(llamada);
        int databaseSizeBeforeUpdate = llamadaRepository.findAll().size();

        // Update the llamada
        Llamada updatedLlamada = llamadaRepository.findOne(llamada.getId());
        // Disconnect from session so that the updates on updatedLlamada are not directly saved in db
        em.detach(updatedLlamada);
        updatedLlamada
            .minutos(UPDATED_MINUTOS);

        restLlamadaMockMvc.perform(put("/api/llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLlamada)))
            .andExpect(status().isOk());

        // Validate the Llamada in the database
        List<Llamada> llamadaList = llamadaRepository.findAll();
        assertThat(llamadaList).hasSize(databaseSizeBeforeUpdate);
        Llamada testLlamada = llamadaList.get(llamadaList.size() - 1);
        assertThat(testLlamada.getMinutos()).isEqualTo(UPDATED_MINUTOS);

        // Validate the Llamada in Elasticsearch
        Llamada llamadaEs = llamadaSearchRepository.findOne(testLlamada.getId());
        assertThat(llamadaEs).isEqualToIgnoringGivenFields(testLlamada);
    }

    @Test
    @Transactional
    public void updateNonExistingLlamada() throws Exception {
        int databaseSizeBeforeUpdate = llamadaRepository.findAll().size();

        // Create the Llamada

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLlamadaMockMvc.perform(put("/api/llamadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(llamada)))
            .andExpect(status().isCreated());

        // Validate the Llamada in the database
        List<Llamada> llamadaList = llamadaRepository.findAll();
        assertThat(llamadaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLlamada() throws Exception {
        // Initialize the database
        llamadaRepository.saveAndFlush(llamada);
        llamadaSearchRepository.save(llamada);
        int databaseSizeBeforeDelete = llamadaRepository.findAll().size();

        // Get the llamada
        restLlamadaMockMvc.perform(delete("/api/llamadas/{id}", llamada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean llamadaExistsInEs = llamadaSearchRepository.exists(llamada.getId());
        assertThat(llamadaExistsInEs).isFalse();

        // Validate the database is empty
        List<Llamada> llamadaList = llamadaRepository.findAll();
        assertThat(llamadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLlamada() throws Exception {
        // Initialize the database
        llamadaRepository.saveAndFlush(llamada);
        llamadaSearchRepository.save(llamada);

        // Search the llamada
        restLlamadaMockMvc.perform(get("/api/_search/llamadas?query=id:" + llamada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(llamada.getId().intValue())))
            .andExpect(jsonPath("$.[*].minutos").value(hasItem(DEFAULT_MINUTOS.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Llamada.class);
        Llamada llamada1 = new Llamada();
        llamada1.setId(1L);
        Llamada llamada2 = new Llamada();
        llamada2.setId(llamada1.getId());
        assertThat(llamada1).isEqualTo(llamada2);
        llamada2.setId(2L);
        assertThat(llamada1).isNotEqualTo(llamada2);
        llamada1.setId(null);
        assertThat(llamada1).isNotEqualTo(llamada2);
    }
}
