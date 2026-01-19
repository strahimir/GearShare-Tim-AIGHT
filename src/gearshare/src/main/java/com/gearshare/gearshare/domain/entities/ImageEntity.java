package com.gearshare.gearshare.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "image" )
public class ImageEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID imageUUID;

    private String filename;

    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "listinguuid", referencedColumnName = "listinguuid")
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ListingEntity listing;

    @ManyToOne
    @JoinColumn(name = "clientuuid", referencedColumnName = "clientuuid")
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity client;

}
