package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Habitacion;
import io.github.jhipster.application.domain.Categoria;
import io.github.jhipster.application.domain.Llamada;
import io.github.jhipster.application.domain.TipoHabitacion;
import io.github.jhipster.application.repository.HabitacionRepository;
import io.github.jhipster.application.repository.search.HabitacionSearchRepository;
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
 * Test class for the HabitacionResource REST controller.
 *
 * @see HabitacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class HabitacionResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_PISO = "AAAAAAAAAA";
    private static final String UPDATED_PISO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OCUPADA = false;
    private static final Boolean UPDATED_OCUPADA = true;

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HabitacionSearchRepository habitacionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHabitacionMockMvc;

    private Habitacion habitacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HabitacionResource habitacionResource = new HabitacionResource(habitacionRepository, habitacionSearchRepository);
        this.restHabitacionMockMvc = MockMvcBuilders.standaloneSetup(habitacionResource)
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
    public static Habitacion createEntity(EntityManager em) {
        Habitacion habitacion = new Habitacion()
            .descripcion(DEFAULT_DESCRIPCION)
            .piso(DEFAULT_PISO)
            .ocupada(DEFAULT_OCUPADA)
            .codigo(DEFAULT_CODIGO);
        // Add required entity
        Categoria categoria = CategoriaResourceIntTest.createEntity(em);
        em.persist(categoria);
        em.flush();
        habitacion.setCategoria(categoria);
        // Add required entity
        Llamada llamada = LlamadaResourceIntTest.createEntity(em);
        em.persist(llamada);
        em.flush();
        habitacion.setLlamada(llamada);
        // Add required entity
        TipoHabitacion tipoHabitacion = TipoHabitacionResourceIntTest.createEntity(em);
        em.persist(tipoHabitacion);
        em.flush();
        habitacion.setTipoHabitacion(tipoHabitacion);
        return habitacion;
    }

    @Before
    public void initTest() {
        habitacionSearchRepository.deleteAll();
        habitacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createHabitacion() throws Exception {
        int databaseSizeBeforeCreate = habitacionRepository.findAll().size();

        // Create the Habitacion
        restHabitacionMockMvc.perform(post("/api/habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isCreated());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeCreate + 1);
        Habitacion testHabitacion = habitacionList.get(habitacionList.size() - 1);
        assertThat(testHabitacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testHabitacion.getPiso()).isEqualTo(DEFAULT_PISO);
        assertThat(testHabitacion.isOcupada()).isEqualTo(DEFAULT_OCUPADA);
        assertThat(testHabitacion.getCodigo()).isEqualTo(DEFAULT_CODIGO);

        // Validate the Habitacion in Elasticsearch
        Habitacion habitacionEs = habitacionSearchRepository.findOne(testHabitacion.getId());
        assertThat(habitacionEs).isEqualToIgnoringGivenFields(testHabitacion);
    }

    @Test
    @Transactional
    public void createHabitacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = habitacionRepository.findAll().size();

        // Create the Habitacion with an existing ID
        habitacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHabitacionMockMvc.perform(post("/api/habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isBadRequest());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = habitacionRepository.findAll().size();
        // set the field null
        habitacion.setDescripcion(null);

        // Create the Habitacion, which fails.

        restHabitacionMockMvc.perform(post("/api/habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isBadRequest());

        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPisoIsRequired() throws Exception {
        int databaseSizeBeforeTest = habitacionRepository.findAll().size();
        // set the field null
        habitacion.setPiso(null);

        // Create the Habitacion, which fails.

        restHabitacionMockMvc.perform(post("/api/habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isBadRequest());

        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHabitacions() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        // Get all the habitacionList
        restHabitacionMockMvc.perform(get("/api/habitacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(habitacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].piso").value(hasItem(DEFAULT_PISO.toString())))
            .andExpect(jsonPath("$.[*].ocupada").value(hasItem(DEFAULT_OCUPADA.booleanValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }

    @Test
    @Transactional
    public void getHabitacion() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        // Get the habitacion
        restHabitacionMockMvc.perform(get("/api/habitacions/{id}", habitacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(habitacion.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.piso").value(DEFAULT_PISO.toString()))
            .andExpect(jsonPath("$.ocupada").value(DEFAULT_OCUPADA.booleanValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHabitacion() throws Exception {
        // Get the habitacion
        restHabitacionMockMvc.perform(get("/api/habitacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHabitacion() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);
        habitacionSearchRepository.save(habitacion);
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();

        // Update the habitacion
        Habitacion updatedHabitacion = habitacionRepository.findOne(habitacion.getId());
        // Disconnect from session so that the updates on updatedHabitacion are not directly saved in db
        em.detach(updatedHabitacion);
        updatedHabitacion
            .descripcion(UPDATED_DESCRIPCION)
            .piso(UPDATED_PISO)
            .ocupada(UPDATED_OCUPADA)
            .codigo(UPDATED_CODIGO);

        restHabitacionMockMvc.perform(put("/api/habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHabitacion)))
            .andExpect(status().isOk());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
        Habitacion testHabitacion = habitacionList.get(habitacionList.size() - 1);
        assertThat(testHabitacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testHabitacion.getPiso()).isEqualTo(UPDATED_PISO);
        assertThat(testHabitacion.isOcupada()).isEqualTo(UPDATED_OCUPADA);
        assertThat(testHabitacion.getCodigo()).isEqualTo(UPDATED_CODIGO);

        // Validate the Habitacion in Elasticsearch
        Habitacion habitacionEs = habitacionSearchRepository.findOne(testHabitacion.getId());
        assertThat(habitacionEs).isEqualToIgnoringGivenFields(testHabitacion);
    }

    @Test
    @Transactional
    public void updateNonExistingHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();

        // Create the Habitacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHabitacionMockMvc.perform(put("/api/habitacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isCreated());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHabitacion() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);
        habitacionSearchRepository.save(habitacion);
        int databaseSizeBeforeDelete = habitacionRepository.findAll().size();

        // Get the habitacion
        restHabitacionMockMvc.perform(delete("/api/habitacions/{id}", habitacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean habitacionExistsInEs = habitacionSearchRepository.exists(habitacion.getId());
        assertThat(habitacionExistsInEs).isFalse();

        // Validate the database is empty
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHabitacion() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);
        habitacionSearchRepository.save(habitacion);

        // Search the habitacion
        restHabitacionMockMvc.perform(get("/api/_search/habitacions?query=id:" + habitacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(habitacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].piso").value(hasItem(DEFAULT_PISO.toString())))
            .andExpect(jsonPath("$.[*].ocupada").value(hasItem(DEFAULT_OCUPADA.booleanValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Habitacion.class);
        Habitacion habitacion1 = new Habitacion();
        habitacion1.setId(1L);
        Habitacion habitacion2 = new Habitacion();
        habitacion2.setId(habitacion1.getId());
        assertThat(habitacion1).isEqualTo(habitacion2);
        habitacion2.setId(2L);
        assertThat(habitacion1).isNotEqualTo(habitacion2);
        habitacion1.setId(null);
        assertThat(habitacion1).isNotEqualTo(habitacion2);
    }
}
