package com.costrella.jhipster.repository.search;

import com.costrella.jhipster.domain.Storegroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Storegroup entity.
 */
public interface StoregroupSearchRepository extends ElasticsearchRepository<Storegroup, Long> {
}
