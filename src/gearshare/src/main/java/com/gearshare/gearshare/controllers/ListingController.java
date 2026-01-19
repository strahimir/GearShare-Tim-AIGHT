package com.gearshare.gearshare.controllers;

import com.gearshare.gearshare.domain.dto.ListingDto;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.filters.ListingsFilter;
import com.gearshare.gearshare.mappers.Mapper;
import com.gearshare.gearshare.services.ListingService;
import com.gearshare.gearshare.specifications.ListingSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final Mapper<ListingEntity, ListingDto> listingMapper;

    private final ListingService listingService;

    public ListingController(Mapper<ListingEntity, ListingDto> listingMapper, ListingService listingService) {
        this.listingMapper = listingMapper;
        this.listingService = listingService;
    }

    @PostMapping(path = "/seller/{sellerUUID}")
    public ResponseEntity<ListingDto> createListing(@RequestBody ListingDto listing,
                                                    @PathVariable("sellerUUID") UUID sellerUUID) {
        ListingEntity listingEntity = listingMapper.mapFrom(listing);
        ListingEntity savedListingEntity = listingService.createOrUpdateListing(listingEntity, sellerUUID);
        return new ResponseEntity<>(listingMapper.mapTo(savedListingEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/seller/{sellerUUID}")
    public ResponseEntity<List<ListingDto>> getListingsBySeller(@PathVariable("sellerUUID") UUID sellerUUID) {

        List<ListingEntity> listingEntities = listingService.findAllListingsFromSeller(sellerUUID);
        List<ListingDto> listingDtos =
                listingEntities
                        .stream()
                        .map(listingMapper::mapTo)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(listingDtos, HttpStatus.OK);
    }

//    @GetMapping(path = "/all")
//    public List<ListingDto> getAllListings() {
//        List<ListingEntity> listings = listingService.findAllListings();
//        return listings
//                .stream()
//                .map(listingMapper::mapTo)
//                .collect(Collectors.toList());
//    }

    @GetMapping(path = "/filtered")
    public ResponseEntity<Page<ListingDto>> getAllListingsPageable(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime availabilityStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime availabilityEnd,
            @RequestParam(required = false) Integer minRentalDays,
            @RequestParam(required = false) Integer maxRentalDays,
            @RequestParam(required = false) BigDecimal minPricePerDay,
            @RequestParam(required = false) BigDecimal maxPricePerDay,
            @RequestParam(required = false) Set<String> seasons,
            @RequestParam(required = false) Set<String> equipmentTypes,
            @RequestParam(required = false) Set<String> equipmentConditions,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "50") int listingCount,
            @RequestParam(required = false) String[] sortBy) {

        List<Order> sortOrder;

        if (sortBy == null || sortBy.length == 0) {
            sortOrder = List.of(new Order(Sort.Direction.DESC, "availabilityPeriodStart"));
        } else {
            sortOrder = Arrays.stream(sortBy)
                    .map(sort -> {
                        String[] params = sort.split(",");
                        String sortParam = params[0].trim();
                        Sort.Direction sortDirection =
                                (params.length > 1 && "ASC".equalsIgnoreCase(params[1]))
                                        ? Sort.Direction.ASC
                                        : Sort.Direction.DESC;
                        return new Order(sortDirection, sortParam);
                    })
                    .toList();
        }

        Pageable pageable = PageRequest.of(pageNo - 1, listingCount, Sort.by(sortOrder));

        ListingsFilter filter = new ListingsFilter(
                availabilityStart,
                availabilityEnd,
                minRentalDays,
                maxRentalDays,
                minPricePerDay,
                maxPricePerDay,
                seasons,
                equipmentTypes,
                equipmentConditions
        );

        Specification<ListingEntity> specification = ListingSpecification.filterBy(filter);

        Page<ListingEntity> listings = listingService.findAllListingsPageable(pageable, specification);
        return new ResponseEntity<>(listings
                .map(listingMapper::mapTo), HttpStatus.OK);
    }

    @GetMapping(path = "/{listingUUID}")
    public ResponseEntity<ListingDto> getListingByUUID(
            @PathVariable("listingUUID") UUID listingUUID) {
        Optional<ListingEntity> listing = listingService.findListingWithUUID(listingUUID);
        return listing.map(
                listingEntity -> new ResponseEntity<>(
                        listingMapper.mapTo(listingEntity),
                        HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{listingUUID}")
    public ResponseEntity<ListingDto> fullUpdateListing(@PathVariable("listingUUID") UUID listingUUID,
                                                        @RequestBody ListingDto listing) {

        if (!listingService.exists(listingUUID))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        listing.setListingUUID(listingUUID);
        ListingEntity listingEntity = listingMapper.mapFrom(listing);
        ListingEntity savedListingEntity = listingService.createOrUpdateListing(listingEntity, listing.getSeller().getClientUUID());

        return new ResponseEntity<>(listingMapper.mapTo(savedListingEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/{listingUUID}")
    public ResponseEntity<ListingDto> partialUpdateListing(@RequestBody ListingDto listingDto,
                                                           @PathVariable("listingUUID") UUID listingUUID) {

        if (!listingService.exists(listingUUID))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        listingDto.setListingUUID(listingUUID);
        ListingEntity listingEntity = listingMapper.mapFrom(listingDto);
        ListingEntity updatedListingEntity = listingService.updateListingWithUUID(listingUUID, listingEntity);

        return new ResponseEntity<>(listingMapper.mapTo(updatedListingEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{listingUUID}")
    public ResponseEntity<Void> deleteListingByUUID(@PathVariable("listingUUID") UUID listingUUID) {

        if (!listingService.exists(listingUUID))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        listingService.deleteListingWithUUID(listingUUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

