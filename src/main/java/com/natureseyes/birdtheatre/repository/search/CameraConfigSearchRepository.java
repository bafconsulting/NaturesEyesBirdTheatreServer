package com.natureseyes.birdtheatre.repository.search;

import com.natureseyes.birdtheatre.domain.CameraConfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CameraConfig entity.
 */
public interface CameraConfigSearchRepository extends ElasticsearchRepository<CameraConfig, Long> {
}
