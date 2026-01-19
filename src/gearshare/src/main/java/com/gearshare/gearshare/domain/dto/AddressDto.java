package com.gearshare.gearshare.domain.dto;

import com.gearshare.gearshare.domain.entities.ListingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private UUID addressUUID;

    private ListingEntity listing;

    private CoordinatesDto coordinates;

    private String streetName;

    private String streetNumber;

    private String aptNumber;

    private String listingPostalCode;

    private String listingCountryCode;
}
