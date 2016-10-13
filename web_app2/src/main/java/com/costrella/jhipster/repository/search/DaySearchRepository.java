package com.costrella.jhipster.repository.search;

import com.costrella.jhipster.domain.Day;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Day entity.
 */
public interface DaySearchRepository extends ElasticsearchRepository<Day, Long> {
}
