package com.gearshare.gearshare.controllers;


import com.gearshare.gearshare.domain.dto.AddressDto;
import com.gearshare.gearshare.domain.dto.CoordinatesDto;
import com.gearshare.gearshare.domain.entities.AddressEntity;
import com.gearshare.gearshare.mappers.Mapper;
import com.gearshare.gearshare.services.AddressService;
import com.gearshare.gearshare.services.ListingService;
import com.gearshare.gearshare.services.geocoding.RadarGeocodingService;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/listings")
public class AddressController {

    private final AddressService addressService;

    private final ListingService listingService;

    private final Mapper<AddressEntity, AddressDto> addressMapper;

    private final RadarGeocodingService radarGeocodingService;

    public AddressController(AddressService addressService, ListingService listingService, Mapper<AddressEntity, AddressDto> addressMapper, RadarGeocodingService radarGeocodingService) {
        this.addressService = addressService;
        this.listingService = listingService;
        this.addressMapper = addressMapper;
        this.radarGeocodingService = radarGeocodingService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('SELLER') and @sellerPolicy.canCreateListing(authentication.principal.clientUUID)")
    @PostMapping(path = "/{listingUUID}/address")
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto address,
                                                    @PathVariable("listingUUID") UUID listingUUID) {

        Point point = radarGeocodingService.radarGeocodeForward(address);

        address.setCoordinates(
                new CoordinatesDto(
                        point.getY(), // latitude
                        point.getX()  // longitude
                )
        );

        AddressEntity addressEntity = addressMapper.mapFrom(address);
        AddressEntity savedAddressEntity = addressService.createOrUpdateAddress(addressEntity, listingUUID);
        return new ResponseEntity<>(addressMapper.mapTo(savedAddressEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{listingUUID}/address")
    public ResponseEntity<AddressDto> getAddressByListingUUID(@PathVariable("listingUUID") UUID listingUUID) {
        Optional<AddressEntity> address = addressService.findAddressFromListing(listingUUID);
        return address
                .map(
                        addressEntity -> new ResponseEntity<>(addressMapper.mapTo(addressEntity), HttpStatus.OK)
                )
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{listingUUID}/address")
    public ResponseEntity<AddressDto> fullUpdateAddress(
            @PathVariable("listingUUID") UUID listingUUID,
            @RequestBody AddressDto address
    ) {

        if (!listingService.exists(listingUUID))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AddressEntity addressEntity = addressMapper.mapFrom(address);
        AddressEntity savedAddressEntity = addressService.createOrUpdateAddress(addressEntity, listingUUID);

        return new ResponseEntity<>(addressMapper.mapTo(savedAddressEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/city")
    public ResponseEntity<List<AddressDto>> getListingsByCity(
            @RequestParam("postalCode") String postalCode,
            @RequestParam("countryCode") String countryCode
    ) {
        List<AddressEntity> addressEntities = addressService.findAllAddressesFromCityInCountry(postalCode, countryCode);

        List<AddressDto> addressDtos =
                addressEntities
                        .stream()
                        .map(addressMapper::mapTo)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    @PostMapping(path = "/nearby")
    public ResponseEntity<List<AddressDto>> getListingsInRadius(
            @RequestBody(required = false) AddressDto address,
            @RequestParam(name = "latitude", required = false, defaultValue = "91") double latitude,
            @RequestParam(name = "longitude", required = false, defaultValue = "181") double longitude,
            @RequestParam("radius") double radius) {

        boolean flag = latitude == 91 || longitude == 181 || !(latitude <= Math.abs(180) && longitude <= Math.abs(90));

        if (address == null && flag)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        double lat_param, long_param;

        if (address != null) {

            Point point = radarGeocodingService.radarGeocodeForward(address);

            lat_param = point.getY();
            long_param = point.getX();

        }
        else {
            lat_param = latitude;
            long_param = longitude;
        }

        List<AddressEntity> addressEntities = addressService.findAllListingsWithinRadius(lat_param, long_param, radius);

        List<AddressDto> addressDtos =
                addressEntities
                        .stream()
                        .map(addressMapper::mapTo)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }


}
