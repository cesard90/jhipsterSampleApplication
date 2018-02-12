package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Reserva;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reserva entity.
 */
public interface ReservaSearchRepository extends ElasticsearchRepository<Reserva, Long> {
}
