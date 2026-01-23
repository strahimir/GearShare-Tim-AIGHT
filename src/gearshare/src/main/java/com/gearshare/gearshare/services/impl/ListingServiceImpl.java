package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.repositories.ClientRepository;
import com.gearshare.gearshare.repositories.ListingRepository;
import com.gearshare.gearshare.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final ClientRepository clientRepository;

    public ListingServiceImpl(ListingRepository listingRepository, ClientRepository clientRepository) {
        this.listingRepository = listingRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public ListingEntity createOrUpdateListing(ListingEntity listing, UUID sellerUUID) {

        ClientEntity seller = clientRepository.findById(sellerUUID)
                .orElseThrow(() -> new IllegalArgumentException("SELLER NOT FOUND"));
        listing.setSeller(seller);
        return listingRepository.save(listing);
    }

    @Override
    public List<ListingEntity> findAllListings() {
        return StreamSupport
                .stream(
                        listingRepository
                                .findAll()
                                .spliterator(),
                        false
                )
                .collect(Collectors.toList());

    }

    public Page<ListingEntity> findAllListingsPageable(Pageable pageable, Specification<ListingEntity> specification) {
        return listingRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<ListingEntity> findListingWithUUID(UUID listingUUID) {
        return listingRepository.findById(listingUUID);
    }

    @Override
    public boolean exists(UUID listingUUID) {
        return listingRepository.existsById(listingUUID);
    }

    @Override
    public void deleteListingWithUUID(UUID listingUUID) {
        listingRepository.deleteById(listingUUID);
    }

    @Override
    public ListingEntity updateListingWithUUID(UUID listingUUID, ListingEntity listingEntity) {
        return listingRepository.findById(listingUUID)
                .map(existingListing -> {
                    Optional.ofNullable(listingEntity.getTitle()).ifPresent(existingListing::setTitle);
                    Optional.ofNullable(listingEntity.getDescription()).ifPresent(existingListing::setDescription);
                    Optional.ofNullable(listingEntity.getPricePerMinimumPeriod()).ifPresent(existingListing::setPricePerMinimumPeriod);
                    Optional.ofNullable(listingEntity.getEquipmentType()).ifPresent(existingListing::setEquipmentType);
                    Optional.ofNullable(listingEntity.getEquipmentCondition()).ifPresent(existingListing::setEquipmentCondition);
                    Optional.ofNullable(listingEntity.getAvailabilityPeriodStart()).ifPresent(existingListing::setAvailabilityPeriodStart);
                    Optional.ofNullable(listingEntity.getAvailabilityPeriodEnd()).ifPresent(existingListing::setAvailabilityPeriodEnd);
                    return listingRepository.save(existingListing);
                })
                .orElseThrow(() -> new RuntimeException(
                        String.format("Listing with UUID [%s] doesn't exist!", listingUUID.toString())
                ));
}

    @Override
    public List<ListingEntity> findAllListingsFromSeller(UUID sellerUUID) {
        return listingRepository.findBySeller_ClientUUID(sellerUUID);
    }

}
