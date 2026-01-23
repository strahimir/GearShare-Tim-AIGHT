package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    Optional<AddressEntity> findByListing_ListingUUID(UUID listingUUID);

    List<AddressEntity> findByListingPostalCodeAndListingCountryCode(String postalCode, String countryCode);

    @Query( value = "SELECT * FROM address WHERE ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(?1, ?2), 4326),?3)", nativeQuery = true )
    List<AddressEntity> findWithinRadius(double latitude, double longitude, double radius);
}
