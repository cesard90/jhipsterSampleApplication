package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Habitacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Habitacion entity.
 */
public interface HabitacionSearchRepository extends ElasticsearchRepository<Habitacion, Long> {
}
