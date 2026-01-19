package com.gearshare.gearshare.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "listing" )
public class ListingEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID listingUUID;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn( name = "selleruuid", referencedColumnName = "clientuuid", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity seller;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(insertable = false, updatable = false)
    private LocalDateTime postedDateTime;

    @NotNull
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availabilityPeriodStart;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availabilityPeriodEnd;

    @Column(nullable = false)
    @Min(1)
    private Integer minimumRentalDays;

    @Column(nullable = false)
    @DecimalMin("0.0")
    private BigDecimal pricePerMinimumPeriod = BigDecimal.valueOf(0.0);

    private String season;

    @NotBlank
    @Column(nullable = false)
    private String equipmentType;

    @NotBlank
    @Column(nullable = false)
    private String equipmentCondition;


}
