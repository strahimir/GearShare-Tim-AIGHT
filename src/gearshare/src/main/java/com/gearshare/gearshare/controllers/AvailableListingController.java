package com.gearshare.gearshare.controllers;

import com.gearshare.gearshare.domain.dto.ListingDto;
import com.gearshare.gearshare.domain.entities.immutable.AvailableListingEntity;
import com.gearshare.gearshare.filters.ListingsFilter;
import com.gearshare.gearshare.mappers.Mapper;
import com.gearshare.gearshare.services.AvailableListingService;
import com.gearshare.gearshare.specifications.ListingSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping( path = "/api/listings/available")
public class AvailableListingController {

    private final Mapper<AvailableListingEntity, ListingDto> availableListingMapper;

    private final AvailableListingService availableListingService;


    public AvailableListingController(Mapper<AvailableListingEntity, ListingDto> availableListingMapper, AvailableListingService availableListingService) {
        this.availableListingMapper = availableListingMapper;
        this.availableListingService = availableListingService;
    }

    @GetMapping(path = "/seller/{sellerUUID}")
    public ResponseEntity<List<ListingDto>> getListingsBySeller(@PathVariable("sellerUUID") UUID sellerUUID) {

        List<AvailableListingEntity> listingEntities = availableListingService.findAllListingsFromSeller(sellerUUID);
        List<ListingDto> listingDtos =
                listingEntities
                        .stream()
                        .map(availableListingMapper::mapTo)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(listingDtos, HttpStatus.OK);
    }

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

        List<Sort.Order> sortOrder;

        if (sortBy == null || sortBy.length == 0) {
            sortOrder = List.of(new Sort.Order(Sort.Direction.DESC, "availabilityPeriodStart"));
        } else {
            sortOrder = Arrays.stream(sortBy)
                    .map(sort -> {
                        String[] params = sort.split(",");
                        String sortParam = params[0].trim();
                        Sort.Direction sortDirection =
                                (params.length > 1 && "ASC".equalsIgnoreCase(params[1]))
                                        ? Sort.Direction.ASC
                                        : Sort.Direction.DESC;
                        return new Sort.Order(sortDirection, sortParam);
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

        Specification<AvailableListingEntity> specification = ListingSpecification.filterBy(filter);

        Page<AvailableListingEntity> listings = availableListingService.findAllListingsPageable(pageable, specification);
        return new ResponseEntity<>(
                listings
                .map(availableListingMapper::mapTo),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{listingUUID}")
    public ResponseEntity<ListingDto> getListingByUUID(
            @PathVariable("listingUUID") UUID listingUUID) {
        Optional<AvailableListingEntity> listing = availableListingService.findListingWithUUID(listingUUID);
        return listing.map(
                listingEntity -> new ResponseEntity<>(
                        availableListingMapper.mapTo(listingEntity),
                        HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
