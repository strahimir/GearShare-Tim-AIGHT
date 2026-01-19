package com.gearshare.gearshare.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
@Table( name = "report" )
public class ReportEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID )
    private UUID reportUUID;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn( name = "selleruuid", referencedColumnName = "clientuuid", nullable = false )
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity reporter;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn( name = "clientuuid", referencedColumnName = "clientuuid", nullable = false )
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity reportee;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime reportDateTime;

    private String reason;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn( name = "adminuuid", referencedColumnName = "clientuuid", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE )
    private ClientEntity reviewer;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime reviewDateTime;

    private Boolean outcome;
}
