package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingRequest {

    private Long listingId;
    private Long pricePerDay;      // cijena po danu u centima, ovo bi trebali iz baze ovo je sam kad sam testirao

    private LocalDateTime rentStart;
    private LocalDateTime rentEnd;

    private String name;
    private String currency;
}

