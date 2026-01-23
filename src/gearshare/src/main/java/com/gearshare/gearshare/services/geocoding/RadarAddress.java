package com.gearshare.gearshare.services.geocoding;

import lombok.Data;

@Data
public class RadarAddress {

    private double latitude;

    private double longitude;

    private String formattedAddress;

}
