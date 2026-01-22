package com.gearshare.gearshare.interfaces;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ListingInterface {

    LocalDateTime getAvailabilityPeriodStart();

    LocalDateTime getAvailabilityPeriodEnd();

    Integer getMinimumRentalDays();

    BigDecimal getPricePerMinimumPeriod();

    String getSeason();

    String getEquipmentType();

    String getEquipmentCondition();

}
