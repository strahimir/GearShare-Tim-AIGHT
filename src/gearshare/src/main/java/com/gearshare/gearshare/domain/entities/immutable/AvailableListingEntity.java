package com.gearshare.gearshare.domain.entities.immutable;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.interfaces.ListingInterface;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Subselect("""
    SELECT *
    FROM available_listing
""")
@Immutable
public class AvailableListingEntity implements ListingInterface {

    @Id
    private UUID listingUUID;

    private String title;

    @ManyToOne
    @JoinColumn( name = "selleruuid", referencedColumnName = "clientuuid", nullable = false, insertable = false, updatable = false)
    private ClientEntity seller;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime postedDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availabilityPeriodStart;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availabilityPeriodEnd;

    private Integer minimumRentalDays;

    private BigDecimal pricePerMinimumPeriod;

    private String season;

    private String equipmentType;

    private String equipmentCondition;

}
