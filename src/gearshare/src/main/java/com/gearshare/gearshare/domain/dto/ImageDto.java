package com.gearshare.gearshare.domain.dto;

import com.gearshare.gearshare.domain.entities.ListingEntity;
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
public class ImageDto {

    private UUID imageUUID;

    private String filename;

    private byte[] content;

    private ListingDto listing;

    private ClientDto client;

}
