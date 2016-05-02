package com.natureseyes.birdtheatre.repository.search;

import com.natureseyes.birdtheatre.domain.BroadcastStream;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BroadcastStream entity.
 */
public interface BroadcastStreamSearchRepository extends ElasticsearchRepository<BroadcastStream, Long> {
}
