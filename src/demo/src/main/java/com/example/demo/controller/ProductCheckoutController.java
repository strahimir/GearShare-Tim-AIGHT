package com.example.demo.controller;

import com.example.demo.dto.ListingRequest;
import com.example.demo.dto.StripeResponse;
import com.example.demo.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/najamOpreme")
public class ProductCheckoutController {

    private final StripeService stripeService;

    public ProductCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(
            @RequestBody ListingRequest listingRequest
    ) {

        //TODO: provjera da listing postoji sa listingIdOm


        // TODO: provjera jesu li su datumi ispravni/dostupni


        StripeResponse stripeResponse =
                stripeService.checkoutProducts(listingRequest);

        return ResponseEntity.ok(stripeResponse);
    }
}

