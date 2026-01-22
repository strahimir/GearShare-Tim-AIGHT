package com.gearshare.gearshare.services;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerSubscriptionService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * @return true if seller has a currently active subscription
     */
    public boolean hasActiveSubscription(UUID sellerUuid) {
        String sql = """
            SELECT COALESCE((
                SELECT (s.subscriptionenddatetime > NOW())
                FROM seller s
                WHERE s.selleruuid = ?
                ORDER BY s.subscriptionstartdatetime DESC
                LIMIT 1
            ), FALSE)
            """;

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                sellerUuid
        );

        return Boolean.TRUE.equals(result);
    }
}
