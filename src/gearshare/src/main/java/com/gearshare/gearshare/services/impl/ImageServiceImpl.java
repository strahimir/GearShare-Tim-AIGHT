package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.ImageEntity;
import com.gearshare.gearshare.repositories.ImageRepository;
import com.gearshare.gearshare.services.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public ImageEntity createOrUpdateImage(ImageEntity imageEntity) {
        return imageRepository.save(imageEntity);
    }

    @Override
    public Optional<ImageEntity> getProfilePicture(UUID clientUUID) {
        return imageRepository.findByClient_ClientUUIDAndListing_ListingUUID(clientUUID, null);
    }

    @Override
    public List<ImageEntity> getListingImages(UUID listingUUID) {
        return imageRepository.findAllByListing_ListingUUID(listingUUID);
    }

    @Override
    public boolean exists(UUID imageUUID) {
        return imageRepository.existsById(imageUUID);
    }

    @Override
    public void deleteImageWithUUID(UUID imageUUID) {
        imageRepository.deleteById(imageUUID);
    }

    @Override
    public int countImagesForListing(UUID listingUUID) {
        return imageRepository.countByListing_ListingUUID(listingUUID);
    }

}
