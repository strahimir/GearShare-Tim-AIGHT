package com.gearshare.gearshare.services;

import com.gearshare.gearshare.domain.entities.SellerEntity;

import java.util.UUID;

public interface SellerService {
    SellerEntity startSubscriptionNow(UUID sellerUuid);
}
