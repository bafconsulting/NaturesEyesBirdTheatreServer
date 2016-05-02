package com.natureseyes.birdtheatre.repository;

import com.natureseyes.birdtheatre.domain.BroadcastStream;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BroadcastStream entity.
 */
public interface BroadcastStreamRepository extends JpaRepository<BroadcastStream,Long> {

    @Query("select distinct broadcastStream from BroadcastStream broadcastStream left join fetch broadcastStream.cameraConfigs")
    List<BroadcastStream> findAllWithEagerRelationships();

    @Query("select broadcastStream from BroadcastStream broadcastStream left join fetch broadcastStream.cameraConfigs where broadcastStream.id =:id")
    BroadcastStream findOneWithEagerRelationships(@Param("id") Long id);

}
