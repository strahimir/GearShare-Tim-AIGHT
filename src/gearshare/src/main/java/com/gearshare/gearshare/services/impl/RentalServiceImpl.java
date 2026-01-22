package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.domain.entities.RentalEntity;
import com.gearshare.gearshare.repositories.ClientRepository;
import com.gearshare.gearshare.repositories.ListingRepository;
import com.gearshare.gearshare.repositories.RentalRepository;
import com.gearshare.gearshare.services.RentalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    private final ClientRepository clientRepository;
    private final ListingRepository listingRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, ClientRepository clientRepository, ListingRepository listingRepository) {
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public RentalEntity createOrUpdateRental(RentalEntity rentalEntity, UUID listingUUID, UUID clientUUID, UUID sellerUUID) {

        ListingEntity listing = listingRepository.findById(listingUUID)
                .orElseThrow(() -> new IllegalArgumentException("SELLER NOT FOUND"));

        ClientEntity client = clientRepository.findById(clientUUID)
                .orElseThrow(() -> new IllegalArgumentException("SELLER NOT FOUND"));

        ClientEntity seller = clientRepository.findById(sellerUUID)
                .orElseThrow(() -> new IllegalArgumentException("SELLER NOT FOUND"));

        return rentalRepository.save(rentalEntity);
    }

    @Override
    public List<RentalEntity> findAllRentalsByClient(UUID clientUUID) {
        return rentalRepository.findByClient_ClientUUID(clientUUID);
    }

    @Override
    public List<RentalEntity> findAllReservationsByListing(UUID listingUUID) {
        return rentalRepository.findReservations(listingUUID);
    }

    @Override
    public boolean exists(UUID rentalUUID) {
        return rentalRepository.existsById(rentalUUID);
    }

    @Override
    public RentalEntity updateRentalWithUUID(UUID rentalUUID, RentalEntity rentalEntity) {

            return rentalRepository.findById(rentalUUID)
                    .map(existingRental -> {
                        Optional.of(rentalEntity.getRating())
                                .ifPresent(existingRental::setRating);
                        Optional.ofNullable(rentalEntity.getReview())
                                .ifPresent(existingRental::setReview);

                        return rentalRepository.save(existingRental);
                    }).orElseThrow(() -> new RuntimeException(String.format("Client with UUID [%s] doesn't exist!", rentalUUID)));


    }
}
