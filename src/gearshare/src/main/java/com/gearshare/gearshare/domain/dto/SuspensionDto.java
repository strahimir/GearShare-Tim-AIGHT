package com.gearshare.gearshare.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuspensionDto {

    private UUID suspensionUUID;

    private ClientEntity client;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime suspensionStartDateTime;

    private int suspensionLength;

}
