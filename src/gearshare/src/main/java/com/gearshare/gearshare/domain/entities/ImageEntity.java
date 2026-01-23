package com.gearshare.gearshare.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageUUID;

    private String filename;

    // NE vraćaj bytea u JSON response (ali dozvoli da se pošalje u requestu)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "content", columnDefinition = "bytea")
    private byte[] content;

    // Prekini serializaciju (i spriječi ogromne/nested objekte)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listinguuid", referencedColumnName = "listinguuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ListingEntity listing;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientuuid", referencedColumnName = "clientuuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClientEntity client;
}
