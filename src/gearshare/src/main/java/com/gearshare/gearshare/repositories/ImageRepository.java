package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
}
