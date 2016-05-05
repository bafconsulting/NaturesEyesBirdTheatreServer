package com.natureseyes.birdtheatre.repository;

import com.natureseyes.birdtheatre.domain.BroadcastStream;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BroadcastStream entity.
 */
public interface BroadcastStreamRepository extends JpaRepository<BroadcastStream,Long> {

}
