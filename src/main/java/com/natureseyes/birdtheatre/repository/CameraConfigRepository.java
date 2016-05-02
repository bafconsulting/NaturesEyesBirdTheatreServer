package com.natureseyes.birdtheatre.repository;

import com.natureseyes.birdtheatre.domain.CameraConfig;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CameraConfig entity.
 */
public interface CameraConfigRepository extends JpaRepository<CameraConfig,Long> {

}
