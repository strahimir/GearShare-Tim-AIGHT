package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.AddressEntity;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.repositories.AddressRepository;
import com.gearshare.gearshare.repositories.ListingRepository;
import com.gearshare.gearshare.services.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ListingRepository listingRepository;

    public AddressServiceImpl(AddressRepository addressRepository, ListingRepository listingRepository) {
        this.addressRepository = addressRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public AddressEntity createOrUpdateAddress(AddressEntity addressEntity, UUID listingUUID) {
        ListingEntity listing = listingRepository.findById(listingUUID)
                .orElseThrow(() -> new IllegalArgumentException("LISTING NOT FOUND"));
        addressEntity.setListing(listing);
        return addressRepository.save(addressEntity);
    }

    @Override
    public Optional<AddressEntity> findAddressFromListing(UUID listingUUID) {
        return addressRepository.findByListing_ListingUUID(listingUUID);
    }

    @Override
    public List<AddressEntity> findAllAddressesFromCityInCountry(String postalCode, String countryCode) {
        return addressRepository.findByListingPostalCodeAndListingCountryCode(postalCode, countryCode);
    }

    @Override
    public List<AddressEntity> findAllListingsWithinRadius(double latitude, double longitude, double radius) {
        return addressRepository.findWithinRadius(latitude, longitude, radius);
    }
}
