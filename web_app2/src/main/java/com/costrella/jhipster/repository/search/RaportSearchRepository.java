package com.costrella.jhipster.repository.search;

import com.costrella.jhipster.domain.Raport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Raport entity.
 */
public interface RaportSearchRepository extends ElasticsearchRepository<Raport, Long> {
}
