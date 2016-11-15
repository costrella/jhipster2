package com.costrella.jhipster.repository.search;

import com.costrella.jhipster.domain.Warehouse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Warehouse entity.
 */
public interface WarehouseSearchRepository extends ElasticsearchRepository<Warehouse, Long> {
}
