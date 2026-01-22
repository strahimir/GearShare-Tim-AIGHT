package com.gearshare.gearshare.services;

import com.gearshare.gearshare.domain.entities.RentalEntity;

import java.util.List;
import java.util.UUID;

public interface RentalService {
    RentalEntity createOrUpdateRental(RentalEntity rentalEntity, UUID listingUUID, UUID clientUUID, UUID sellerUUID);

    List<RentalEntity> findAllRentalsByClient(UUID clientUUID);

    List<RentalEntity> findAllReservationsByListing(UUID listingUUID);

    boolean exists(UUID rentalUUID);

    RentalEntity updateRentalWithUUID(UUID rentalUUID, RentalEntity rentalEntity);
}
