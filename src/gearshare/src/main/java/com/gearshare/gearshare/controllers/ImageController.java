package com.gearshare.gearshare.controllers;

import com.gearshare.gearshare.domain.dto.ImageDto;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.domain.entities.ImageEntity;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.mappers.impl.ImageMapperImpl;
import com.gearshare.gearshare.services.ClientService;
import com.gearshare.gearshare.services.ImageService;
import com.gearshare.gearshare.services.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final ClientService clientService;
    private final ListingService listingService;
    private final ImageMapperImpl imageMapper;

    public ImageController(ImageService imageService, ClientService clientService, ListingService listingService, ImageMapperImpl imageMapper) {
        this.imageService = imageService;
        this.clientService = clientService;
        this.listingService = listingService;
        this.imageMapper = imageMapper;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SELLER') and @sellerPolicy.canCreateListing(authentication.principal.clientUUID)")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> addImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("clientUUID") UUID clientUUID,
            @RequestParam(value = "listingUUID", required = false) UUID listingUUID
    ) throws IOException {
        ClientEntity client = clientService.findClientWithUUID(clientUUID)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        ImageEntity image = new ImageEntity();
        image.setClient(client);
        image.setFilename(file.getOriginalFilename());
        image.setContent(file.getBytes());

        if (listingUUID != null) {
            ListingEntity listing = listingService.findListingWithUUID(listingUUID)
                    .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

            if (imageService.countImagesForListing(listingUUID) >= 5) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            image.setListing(listing);
        }

        ImageEntity saved = imageService.createOrUpdateImage(image);
        return new ResponseEntity<>(imageMapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping("/client/{clientUUID}")
    public ResponseEntity<ImageDto> getProfilePicture(@PathVariable UUID clientUUID) {
        Optional<ClientEntity> client = clientService.findClientWithUUID(clientUUID);
        if (client.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return imageService.getProfilePicture(clientUUID)
                .map(image -> new ResponseEntity<>(imageMapper.mapTo(image), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/listing/{listingUUID}")
    public ResponseEntity<List<ImageDto>> getListingImages(@PathVariable UUID listingUUID) {
        Optional<ListingEntity> listing = listingService.findListingWithUUID(listingUUID);
        if (listing.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<ImageDto> images = imageService.getListingImages(listingUUID)
                .stream()
                .map(imageMapper::mapTo)
                .collect(Collectors.toList());

        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @DeleteMapping("/{imageUUID}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID imageUUID) {
        if (!imageService.exists(imageUUID)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        imageService.deleteImageWithUUID(imageUUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
