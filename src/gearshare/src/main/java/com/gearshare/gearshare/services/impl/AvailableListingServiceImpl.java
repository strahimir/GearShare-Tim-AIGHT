package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.domain.entities.immutable.AvailableListingEntity;
import com.gearshare.gearshare.repositories.readOnly.AvailableListingRepository;
import com.gearshare.gearshare.services.AvailableListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvailableListingServiceImpl implements AvailableListingService {

    private final AvailableListingRepository availableListingRepository;

    public AvailableListingServiceImpl(AvailableListingRepository availableListingRepository) {
        this.availableListingRepository = availableListingRepository;
    }

    @Override
    public List<AvailableListingEntity> findAllListingsFromSeller(UUID sellerUUID) {
        return availableListingRepository.findBySeller_ClientUUID(sellerUUID);
    }

    @Override
    public Page<AvailableListingEntity> findAllListingsPageable(Pageable pageable, Specification<AvailableListingEntity> specification) {
        return availableListingRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<AvailableListingEntity> findListingWithUUID(UUID listingUUID) {

        return availableListingRepository.findById(listingUUID);
    }


}
