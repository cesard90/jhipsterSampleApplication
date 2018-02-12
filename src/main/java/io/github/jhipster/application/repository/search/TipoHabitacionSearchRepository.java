package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TipoHabitacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoHabitacion entity.
 */
public interface TipoHabitacionSearchRepository extends ElasticsearchRepository<TipoHabitacion, Long> {
}
