package com.gearshare.gearshare.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seller")
public class SellerEntity {

    @EmbeddedId
    private SellerId id;

    @Column(name = "subscriptionenddatetime", nullable = false)
    private LocalDateTime subscriptionEndDateTime;

    @Builder.Default
    @Column(name = "autorenewal", nullable = false)
    private boolean autoRenewal = false;

    // Convenience getters (optional)
    public UUID getSellerUuid() {
        return id.getSellerUuid();
    }

    public LocalDateTime getSubscriptionStartDateTime() {
        return id.getSubscriptionStartDateTime();
    }
}
