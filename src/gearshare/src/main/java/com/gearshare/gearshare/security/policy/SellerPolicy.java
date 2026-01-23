package com.gearshare.gearshare.security.policy;

import com.gearshare.gearshare.services.SellerSubscriptionService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("sellerPolicy")
public class SellerPolicy {

    private final SellerSubscriptionService sellerSubscriptionService;

    public SellerPolicy(SellerSubscriptionService sellerSubscriptionService) {
        this.sellerSubscriptionService = sellerSubscriptionService;
    }

    public boolean canCreateListing(UUID clientUuid) {
        return sellerSubscriptionService.hasActiveSubscription(clientUuid);
    }

    public boolean canEditOrDeleteListing(UUID clientUuid) {
        return sellerSubscriptionService.hasActiveSubscription(clientUuid);
    }
}
