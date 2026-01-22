package com.gearshare.gearshare.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "rental" )
public class RentalEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID rentalUUID;

    @ManyToOne
    @JoinColumn( name = "clientuuid", referencedColumnName = "clientuuid", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity client;

    @ManyToOne
    @JoinColumn( name = "selleruuid", referencedColumnName = "clientuuid", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity seller;

    @ManyToOne
    @JoinColumn( name = "listing", referencedColumnName = "listinguuid", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ListingEntity listing;

    private LocalDateTime rentingStartDateTime;

    private LocalDateTime rentingEndDateTime;

    private int rating;

    private String review;

}
