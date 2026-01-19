package com.gearshare.gearshare.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "address" )
public class AddressEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID addressUUID;

    @OneToOne//( fetch = FetchType.EAGER )
    @JoinColumn( name = "listingaddressuuid", referencedColumnName = "listinguuid")
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ListingEntity listing;

    @NotNull
    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point coordinates;

    private String streetName;

    private String streetNumber;

    private String aptNumber;

    private String listingPostalCode;

    private String listingCountryCode;

}
