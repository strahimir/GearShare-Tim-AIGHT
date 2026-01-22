package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, UUID> {
    List<RentalEntity> findByClient_ClientUUID(UUID clientUUID);

    @Query( value = "SELECT * FROM rental WHERE listinguuid = ?1 AND rating IS NULL", nativeQuery = true )
    List<RentalEntity> findReservations(UUID listingUUID);
}
