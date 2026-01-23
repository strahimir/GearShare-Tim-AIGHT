package com.gearshare.gearshare.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
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
public class ClientDto {

    private UUID clientUUID;

    private String username;

    private String email;

    private String phoneNumber;

    private Date dateJoined;

    private String firstName;

    private String lastName;

    private String providerId;

    private String provider;

    private int reportCount;

    private String role;


}
