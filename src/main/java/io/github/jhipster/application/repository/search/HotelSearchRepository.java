package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Hotel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Hotel entity.
 */
public interface HotelSearchRepository extends ElasticsearchRepository<Hotel, Long> {
}
