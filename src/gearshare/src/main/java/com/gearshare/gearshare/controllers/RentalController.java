package com.gearshare.gearshare.controllers;

import com.gearshare.gearshare.domain.dto.DateTimeIntervalDto;
import com.gearshare.gearshare.domain.dto.RentalDto;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.domain.entities.RentalEntity;
import com.gearshare.gearshare.mappers.impl.RentalMapperImpl;
import com.gearshare.gearshare.services.ClientService;
import com.gearshare.gearshare.services.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;
    private final ClientService clientService;

    private final RentalMapperImpl rentalMapper;
    public RentalController(RentalService rentalService, ClientService clientService, RentalMapperImpl rentalMapper) {
        this.rentalService = rentalService;
        this.clientService = clientService;
        this.rentalMapper = rentalMapper;
    }


    @PostMapping
    public ResponseEntity<RentalDto> createRental(
            @RequestParam("rentalUUID") UUID listingUUID,
            @RequestParam("clientUUID") UUID clientUUID,
            @RequestParam("sellerUUID") UUID sellerUUID,
            @RequestBody RentalDto rentalDto) {
        RentalEntity rentalEntity = rentalMapper.mapFrom(rentalDto);
        RentalEntity savedRentalEntity = rentalService.createOrUpdateRental(rentalEntity, listingUUID, clientUUID, sellerUUID);
        return new ResponseEntity<>(rentalMapper.mapTo(savedRentalEntity), HttpStatus.CREATED);
    }

//    @GetMapping(path = "/{clientUUID}")
//    public ResponseEntity<List<RentalDto>> getRentalsByClient(@PathVariable("clientUUID") UUID clientUUID) {
//
//        List<RentalEntity> rentalEntities = rentalService.findAllRentalsByClient(clientUUID);
//        List<RentalDto> rentalDtos =
//                rentalEntities
//                        .stream()
//                        .map(rentalMapper::mapTo)
//                        .collect(Collectors.toList());
//
//        return new ResponseEntity<>(rentalDtos, HttpStatus.OK);
//    }

    @GetMapping(path = "/availability")
    public ResponseEntity<List<DateTimeIntervalDto>> getAvailabilityPeriod(@RequestParam("listingUUID") UUID listingUUID) {


        List<RentalEntity> rentalEntities = rentalService.findAllReservationsByListing(listingUUID);

        List<DateTimeIntervalDto> intervals =
                rentalEntities
                        .stream()
                        .map(rental -> new DateTimeIntervalDto(
                                rental.getRentingStartDateTime(),
                                rental.getRentingEndDateTime())
                        ).toList();


        return new ResponseEntity<>(intervals, HttpStatus.OK);
    }

    @PatchMapping(path = "/{rentalUUID}")
    public ResponseEntity<RentalDto> rateAndReviewRental(@RequestBody RentalDto rentalDto,
                                                           @PathVariable("rentalUUID") UUID rentalUUID) {

        if (!rentalService.exists(rentalUUID))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        rentalDto.setRentalUUID(rentalUUID);
        RentalEntity rentalEntity = rentalMapper.mapFrom(rentalDto);
        RentalEntity updatedRentalEntity = rentalService.updateRentalWithUUID(rentalUUID, rentalEntity);

        return new ResponseEntity<>(rentalMapper.mapTo(updatedRentalEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/{clientUUID}")
    public ResponseEntity<List<RentalDto>> rateAndReviewRentals(@PathVariable("clientUUID") UUID clientUUID) {

        if (!clientService.exists(clientUUID))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<RentalEntity> rentalEntities = rentalService.getUnreviewedRentals(clientUUID);

        List<RentalDto> rentals =
                rentalEntities
                        .stream()
                        .map(rentalMapper::mapTo)
                        .toList();

        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

}
