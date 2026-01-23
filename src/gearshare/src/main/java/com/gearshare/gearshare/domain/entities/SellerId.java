package com.gearshare.gearshare.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SellerId implements Serializable {

    @Column(name = "selleruuid", nullable = false)
    private UUID sellerUuid;

    @Column(name = "subscriptionstartdatetime", nullable = false)
    private LocalDateTime subscriptionStartDateTime;
}
