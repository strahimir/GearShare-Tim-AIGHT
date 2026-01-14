package com.example.demo.service;


import com.example.demo.dto.ListingRequest;
import com.example.demo.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    // hardkodano samo za test account u sandboxu - Potjeh
    private static final String vendorStripeId = "acct_1SpMPaIg6elrWBfB";


    public StripeResponse checkoutProducts(ListingRequest listingRequest) {

        Stripe.apiKey = secretKey;


        // TODO: ovdje kasnije provjeriti datum
        long rentalDays = Duration.between(
                listingRequest.getRentStart(),
                listingRequest.getRentEnd()
        ).toDays();


        // cijena (BTW u centima)
        long totalAmount = rentalDays * listingRequest.getPricePerDay();

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(listingRequest.getName() + " (" + rentalDays + " dana)") // mogli bi u ime staviti datume?
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(
                                listingRequest.getCurrency() != null
                                        ? listingRequest.getCurrency()
                                        : "EUR"
                        )
                        .setUnitAmount(totalAmount)
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/success")
                        .setCancelUrl("http://localhost:8080/cancel")
                        .addLineItem(lineItem)
                        .setPaymentIntentData(
                                SessionCreateParams.PaymentIntentData.builder()
                                        .setTransferData(
                                                SessionCreateParams.PaymentIntentData.TransferData.builder()
                                                        .setDestination(vendorStripeId)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        return StripeResponse.builder()
                .status("Success")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
