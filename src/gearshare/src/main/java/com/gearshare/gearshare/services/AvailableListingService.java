package com.gearshare.gearshare.services;

import com.gearshare.gearshare.domain.entities.immutable.AvailableListingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailableListingService {
    List<AvailableListingEntity> findAllListingsFromSeller(UUID sellerUUID);

    Page<AvailableListingEntity> findAllListingsPageable(Pageable pageable, Specification<AvailableListingEntity> specification);

    Optional<AvailableListingEntity> findListingWithUUID(UUID listingUUID);
}
