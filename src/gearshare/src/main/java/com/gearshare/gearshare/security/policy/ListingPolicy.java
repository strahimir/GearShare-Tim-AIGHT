package com.gearshare.gearshare.security.policy;

import com.gearshare.gearshare.repositories.ListingRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("listingPolicy")
public class ListingPolicy {

    private final ListingRepository listingRepository;

    public ListingPolicy(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    // ABAC: must own the listing
    public boolean isOwner(UUID listingUuid, UUID clientUuid) {
        return listingRepository.existsByListingUUIDAndSeller_ClientUUID(listingUuid, clientUuid);
    }
}
