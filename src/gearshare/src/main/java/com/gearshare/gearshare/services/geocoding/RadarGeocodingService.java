package com.gearshare.gearshare.services.geocoding;

import com.gearshare.gearshare.config.GeometryUtils;
import com.gearshare.gearshare.domain.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.locationtech.jts.geom.Point;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;



@Service
@RequiredArgsConstructor
public class RadarGeocodingService {

    @Value("${RADAR_API_KEY}")
    private String radarApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Point radarGeocodeForward(AddressDto addressDto) {

        String query = String.format(
                "%s+%s+%s+%s",
                String.join("+", addressDto.getStreetName().split(" ")),
                addressDto.getStreetNumber(),
                addressDto.getListingPostalCode(),
                addressDto.getListingCountryCode()
        );

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString("https://api.radar.io/v1/geocode/forward")
                .queryParam("query", query);

        uriComponentsBuilder.build(true);

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", radarApiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<RadarResponse> response =
                restTemplate.exchange(
                        uriComponentsBuilder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        RadarResponse.class
                );

        RadarResponse radarResponse = response.getBody();

        if( radarResponse == null || radarResponse.getAddresses().isEmpty() )
            throw new IllegalStateException("Unable to geocode provided address.");

        RadarAddress radarAddress = radarResponse.getAddresses().get(0);

        return GeometryUtils.toPoint(
                radarAddress.getLatitude(),
                radarAddress.getLongitude()
        );

    }

}
