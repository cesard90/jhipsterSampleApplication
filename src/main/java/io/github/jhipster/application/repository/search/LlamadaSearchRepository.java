package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Llamada;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Llamada entity.
 */
public interface LlamadaSearchRepository extends ElasticsearchRepository<Llamada, Long> {
}
