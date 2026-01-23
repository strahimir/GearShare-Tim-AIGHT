package com.gearshare.gearshare.repositories;


import com.gearshare.gearshare.domain.entities.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ListingRepository extends JpaRepository<ListingEntity, UUID>, JpaSpecificationExecutor<ListingEntity> {

    @Query("SELECT l FROM ListingEntity l JOIN FETCH l.seller")
    List<ListingEntity> findAllWithSeller();

    List<ListingEntity> findBySeller_ClientUUID(UUID sellerUUID);

    boolean existsByListingUUIDAndSeller_ClientUUID(UUID listingUUID, UUID clientUUID);
    
}
