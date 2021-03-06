package com.costrella.jhipster.repository.search;

import com.costrella.jhipster.domain.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Person entity.
 */
public interface PersonSearchRepository extends ElasticsearchRepository<Person, Long> {
}
