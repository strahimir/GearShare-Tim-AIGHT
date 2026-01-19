package com.gearshare.gearshare.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name = "suspension" )
public class SuspensionEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID suspensionUUID;

    @ManyToOne
    @JoinColumn( name = "clientuuid", referencedColumnName = "clientuuid", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity client;

    @NotNull
    private LocalDateTime suspensionStartDateTime;

    @NotNull
    private int suspensionLength;

}
