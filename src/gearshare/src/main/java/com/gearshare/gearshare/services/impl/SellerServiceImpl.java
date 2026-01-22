package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.SellerEntity;
import com.gearshare.gearshare.domain.entities.SellerId;
import com.gearshare.gearshare.repositories.SellerRepository;
import com.gearshare.gearshare.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public SellerEntity startSubscriptionNow(UUID sellerUuid) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMonths(1);

        SellerEntity seller = SellerEntity.builder()
                .id(new SellerId(sellerUuid, start))
                .subscriptionEndDateTime(end)
                .autoRenewal(false)
                .build();

        return sellerRepository.save(seller);
    }
}
