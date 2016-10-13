package com.costrella.jhipster.repository.search;

import com.costrella.jhipster.domain.Store;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Store entity.
 */
public interface StoreSearchRepository extends ElasticsearchRepository<Store, Long> {
}
