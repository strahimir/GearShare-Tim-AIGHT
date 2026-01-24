package com.gearshare.gearshare;

import com.gearshare.gearshare.domain.dto.ClientDto;
import com.gearshare.gearshare.domain.dto.ListingDto;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.domain.entities.ListingEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static ClientEntity createTestClientEntityA() {
        return ClientEntity.builder()
                .username("testclientA")
                .email("testA@client.com")
                .phoneNumber("123-456-7890")
                .isSuspended(false)
                .suspensionLength(null)
                .suspensionStartDateTime(null)
                .dateJoined(java.sql.Date.valueOf("2025-01-01"))
                .subscriptionStartDateTime(LocalDateTime.now())
                .subscriptionEndDateTime(LocalDateTime.now().plusYears(1))
                .build();
    }

    public static ClientEntity createTestClientEntityB() {
        return ClientEntity.builder()
                .username("testclientB")
                .email("testB@client.com")
                .phoneNumber("234-567-8901")
                .isSuspended(false)
                .suspensionLength(null)
                .suspensionStartDateTime(null)
                .dateJoined(java.sql.Date.valueOf("2025-02-01"))
                .subscriptionStartDateTime(LocalDateTime.now())
                .subscriptionEndDateTime(LocalDateTime.now().plusYears(1))
                .build();
    }

    public static ClientEntity createTestClientEntityC() {
        return ClientEntity.builder()
                .username("testclientC")
                .email("testC@client.com")
                .phoneNumber("345-678-9012")
                .isSuspended(false)
                .suspensionLength(null)
                .suspensionStartDateTime(null)
                .dateJoined(java.sql.Date.valueOf("2025-03-01"))
                .subscriptionStartDateTime(LocalDateTime.now())
                .subscriptionEndDateTime(null)
                .build();
    }

    public static ListingEntity createTestListingEntityA(final ClientEntity client) {
        return ListingEntity.builder()
                .title("ListingEntity A")
                .seller(client)
                .description("Description for Listing A")
                .postedDateTime(LocalDateTime.now())
                .availabilityPeriodStart(LocalDateTime.now())
                .availabilityPeriodEnd(LocalDateTime.now().plusDays(7))
                .minimumRentalDays(1)
                .pricePerMinimumPeriod(new BigDecimal("100.00"))
                .season("Summer")
                .equipmentType("Camera")
                .equipmentCondition("Good")
                .build();
    }

    public static ListingEntity createTestListingEntityB(final ClientEntity client) {
        return ListingEntity.builder()
                .title("ListingEntity B")
                .seller(client)
                .description("Description for Listing B")
                .postedDateTime(LocalDateTime.now())
                .availabilityPeriodStart(LocalDateTime.now())
                .availabilityPeriodEnd(LocalDateTime.now().plusDays(14))
                .minimumRentalDays(2)
                .pricePerMinimumPeriod(new BigDecimal("200.00"))
                .season("Winter")
                .equipmentType("Ski")
                .equipmentCondition("Excellent")
                .build();
    }

    public static ListingEntity createTestListingEntityC(final ClientEntity client) {
        return ListingEntity.builder()
                .title("ListingEntity C")
                .seller(client)
                .description("Description for Listing C")
                .postedDateTime(LocalDateTime.now())
                .availabilityPeriodStart(LocalDateTime.now())
                .availabilityPeriodEnd(LocalDateTime.now().plusDays(10))
                .minimumRentalDays(1)
                .pricePerMinimumPeriod(new BigDecimal("150.00"))
                .season("Spring")
                .equipmentType("Bicycle")
                .equipmentCondition("Fair")
                .build();
    }


    public static ClientDto createTestClientDtoA() {
        return ClientDto.builder()
                .username("testclientA")
                .email("testA@client.com")
                .phoneNumber("123-456-7890")
                .isSuspended(false)
                .suspensionLength(null)
                .suspensionStartDateTime(null)
                .dateJoined(Date.valueOf("2025-01-01"))
                .subscriptionStartDateTime(LocalDateTime.now())
                .subscriptionEndDateTime(LocalDateTime.now().plusYears(1))
                .build();
    }

    public static ClientDto createTestClientDtoB() {
        return ClientDto.builder()
                .username("testclientB")
                .email("testB@client.com")
                .phoneNumber("234-567-8901")
                .isSuspended(false)
                .suspensionLength(null)
                .suspensionStartDateTime(null)
                .dateJoined(Date.valueOf("2025-02-01"))
                .subscriptionStartDateTime(LocalDateTime.now())
                .subscriptionEndDateTime(LocalDateTime.now().plusYears(1))
                .build();
    }

    public static ClientDto createTestClientDtoC() {
        return ClientDto.builder()
                .username("testclientC")
                .email("testC@client.com")
                .phoneNumber("345-678-9012")
                .isSuspended(false)
                .suspensionLength(null)
                .suspensionStartDateTime(null)
                .dateJoined(Date.valueOf("2025-03-01"))
                .subscriptionStartDateTime(LocalDateTime.now())
                .subscriptionEndDateTime(null)
                .build();
    }

    public static ListingDto createTestListingDtoA(final ClientDto client) {
        return ListingDto.builder()
                .title("ListingDto A")
                .seller(client)
                .description("Description for Listing A")
                .postedDateTime(LocalDateTime.now())
                .availabilityPeriodStart(LocalDateTime.now())
                .availabilityPeriodEnd(LocalDateTime.now().plusDays(7))
                .minimumRentalDays(1)
                .pricePerMinimumPeriod(new BigDecimal("100.00"))
                .season("Summer")
                .equipmentType("Camera")
                .equipmentCondition("Good")
                .build();
    }

    public static ListingDto createTestListingDtoB(final ClientDto client) {
        return ListingDto.builder()
                .title("ListingDto B")
                .seller(client)
                .description("Description for Listing B")
                .postedDateTime(LocalDateTime.now())
                .availabilityPeriodStart(LocalDateTime.now())
                .availabilityPeriodEnd(LocalDateTime.now().plusDays(14))
                .minimumRentalDays(2)
                .pricePerMinimumPeriod(new BigDecimal("200.00"))
                .season("Winter")
                .equipmentType("Ski")
                .equipmentCondition("Excellent")
                .build();
    }

    public static ListingDto createTestListingDtoC(final ClientDto client) {
        return ListingDto.builder()
                .title("ListingDto C")
                .seller(client)
                .description("Description for Listing C")
                .postedDateTime(LocalDateTime.now())
                .availabilityPeriodStart(LocalDateTime.now())
                .availabilityPeriodEnd(LocalDateTime.now().plusDays(10))
                .minimumRentalDays(1)
                .pricePerMinimumPeriod(new BigDecimal("150.00"))
                .season("Spring")
                .equipmentType("Bicycle")
                .equipmentCondition("Fair")
                .build();
    }

}
