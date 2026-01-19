package com.gearshare.gearshare.services.geocoding;

import lombok.Data;

import java.util.List;

@Data
public class RadarResponse {

    private List<RadarAddress> addresses;
}
