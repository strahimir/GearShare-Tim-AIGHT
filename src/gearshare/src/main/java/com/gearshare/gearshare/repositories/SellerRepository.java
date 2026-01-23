package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.SellerEntity;
import com.gearshare.gearshare.domain.entities.SellerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, SellerId> {

    Optional<SellerEntity> findTopByIdSellerUuidOrderByIdSubscriptionStartDateTimeDesc(UUID sellerUuid);

    boolean existsByIdSellerUuid(UUID sellerUuid);
}
