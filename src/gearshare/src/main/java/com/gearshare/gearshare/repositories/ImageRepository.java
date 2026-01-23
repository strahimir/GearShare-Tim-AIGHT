package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {

    Optional<ImageEntity> findByClient_ClientUUIDAndListing_ListingUUID(UUID clientUUID, UUID listingUUID);

    List<ImageEntity> findAllByListing_ListingUUID(UUID listingUUID);

    int countByListing_ListingUUID(UUID listingUUID);
}
