package com.gearshare.gearshare.repositories.readOnly;

import com.gearshare.gearshare.domain.entities.immutable.AvailableListingEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface AvailableListingRepository extends ReadOnlyRepository<AvailableListingEntity, UUID>, JpaSpecificationExecutor<AvailableListingEntity> {
    List<AvailableListingEntity> findBySeller_ClientUUID(UUID sellerUUID);
}
