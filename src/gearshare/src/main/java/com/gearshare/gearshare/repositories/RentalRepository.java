package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, UUID> {
}
