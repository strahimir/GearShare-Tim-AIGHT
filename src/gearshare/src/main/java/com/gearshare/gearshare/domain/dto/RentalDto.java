package com.gearshare.gearshare.domain.dto;

import com.gearshare.gearshare.domain.dto.ClientDto;
import com.gearshare.gearshare.domain.dto.ListingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalDto {

    private UUID rentalUUID;

    private ClientDto client;
    
    private ClientDto seller;

    private ListingDto listing;

    private LocalDateTime rentingStartDateTime;

    private LocalDateTime rentingEndDateTime;

    private int rating;

    private String review;
}
