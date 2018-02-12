package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TipoLlamada;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoLlamada entity.
 */
public interface TipoLlamadaSearchRepository extends ElasticsearchRepository<TipoLlamada, Long> {
}
