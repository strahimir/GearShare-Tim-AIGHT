package com.gearshare.gearshare.services;

import com.gearshare.gearshare.domain.dto.AddressDto;
import com.gearshare.gearshare.domain.entities.AddressEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {

    AddressEntity createOrUpdateAddress(AddressEntity addressEntity, UUID listingUUID);

    Optional<AddressEntity> findAddressFromListing(UUID listingUUID);

    List<AddressEntity> findAllAddressesFromCityInCountry(String postalCode, String countryCode);

    List<AddressEntity> findAllListingsWithinRadius(double latitude, double longitude, double radius);
}
