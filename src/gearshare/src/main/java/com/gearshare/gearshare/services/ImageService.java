package com.gearshare.gearshare.services;

import com.gearshare.gearshare.domain.entities.ImageEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageService {

    ImageEntity createOrUpdateImage(ImageEntity imageEntity);

    Optional<ImageEntity> getProfilePicture(UUID clientUUID);

    List<ImageEntity> getListingImages(UUID listingUUID);

    boolean exists(UUID imageUUID);

    void deleteImageWithUUID(UUID imageUUID);

    int countImagesForListing(UUID listingUUID);
}
