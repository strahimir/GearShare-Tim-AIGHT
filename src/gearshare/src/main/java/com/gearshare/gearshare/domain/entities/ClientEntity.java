package com.gearshare.gearshare.domain.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "client" )
public class ClientEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID clientUUID;

    @NotBlank
    @Pattern( regexp = "^[A-Za-z_][A-Za-z0-9._]*$" )
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;

    @Column(insertable = false, updatable = false)
    private Date dateJoined;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime subscriptionStartDateTime;
//
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime subscriptionEndDateTime;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String providerId;

    @Builder.Default
    @Column(nullable = false)
    private String provider = "google";

    @Builder.Default
    @Column(nullable = false)
    private short reportCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private String role = "USER";


}
