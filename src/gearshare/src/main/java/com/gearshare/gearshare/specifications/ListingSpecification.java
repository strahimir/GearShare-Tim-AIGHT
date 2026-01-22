package com.gearshare.gearshare.specifications;

import com.gearshare.gearshare.filters.ListingsFilter;
import com.gearshare.gearshare.interfaces.ListingInterface;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public final class ListingSpecification<T extends ListingInterface> {

    public static final String AVAILABILITY_PERIOD_START = "availabilityPeriodStart";
    public static final String AVAILABILITY_PERIOD_END = "availabilityPeriodEnd";
    public static final String MINIMUM_RENTAL_DAYS = "minimumRentalDays";
    public static final String PRICE_PER_MINIMUM_PERIOD = "pricePerMinimumPeriod";
    public static final String SEASON = "season";
    public static final String EQUIPMENT_TYPE = "equipmentType";
    public static final String EQUIPMENT_CONDITION = "equipmentCondition";

    private ListingSpecification(){}

    public static <T extends ListingInterface> Specification<T> filterBy(ListingsFilter listingsFilter){
        Specification<T> spec = hasAvailabilityWindowOverlap(listingsFilter.availabilityPeriodStart(), listingsFilter.availabilityPeriodEnd());

        spec = spec.and(hasPricePerDayInRange(listingsFilter.minPricePerDay(), listingsFilter.maxPricePerDay()));
        spec = spec.and(hasMinimumRentalDaysInRange(listingsFilter.minRentalDays(), listingsFilter.maxRentalDays()));
        spec = spec.and(hasSeasons(listingsFilter.seasons()));
        spec = spec.and(hasEquipmentTypes(listingsFilter.equipmentTypes()));
        spec = spec.and(hasEquipmentConditions(listingsFilter.equipmentConditions()));

        return spec;
    }



    private static <T extends ListingInterface> Specification<T> hasEquipmentConditions(Set<String> equipmentConditions) {
        return (root, query, cb) -> {
            if (equipmentConditions == null || equipmentConditions.isEmpty()) return cb.conjunction();
            return root.get(EQUIPMENT_CONDITION).in(equipmentConditions);
        };
    }

    private static <T extends ListingInterface> Specification<T> hasEquipmentTypes(Set<String> equipmentTypes) {
        return (root, query, cb) -> {
            if (equipmentTypes == null || equipmentTypes.isEmpty()) return cb.conjunction();
            return root.get(EQUIPMENT_TYPE).in(equipmentTypes);
        };
    }

    private static <T extends ListingInterface> Specification<T> hasSeasons(Set<String> seasons) {
        return (root, query, cb) -> {
            if (seasons == null || seasons.isEmpty()) return cb.conjunction();
            return root.get(SEASON).in(seasons);
        };
    }

    private static <T extends ListingInterface> Specification<T> hasMinimumRentalDaysInRange(Integer minDays, Integer maxDays) {
        return (root, query, cb) -> {
            if (minDays == null && maxDays == null) return cb.conjunction();
            Path<Integer> minimumRentalDays = root.get(MINIMUM_RENTAL_DAYS);
            if (minDays != null && maxDays != null) return cb.between(minimumRentalDays, minDays, maxDays);
            if (minDays != null) return cb.greaterThanOrEqualTo(minimumRentalDays, minDays);
            return cb.lessThanOrEqualTo(minimumRentalDays, maxDays);
        };
    }

    private static <T extends ListingInterface> Specification<T> hasPricePerDayInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return cb.conjunction();
            Expression<BigDecimal> pricePerDay = cb.quot(root.get(PRICE_PER_MINIMUM_PERIOD), root.get(MINIMUM_RENTAL_DAYS)).as(BigDecimal.class);
            if (minPrice != null && maxPrice != null) return cb.between(pricePerDay, minPrice, maxPrice);
            if (minPrice != null) return cb.greaterThanOrEqualTo(pricePerDay, minPrice);
            return cb.lessThanOrEqualTo(pricePerDay, maxPrice);
        };
    }

    private static <T extends ListingInterface> Specification<T> hasAvailabilityWindowOverlap(LocalDateTime queryStart, LocalDateTime queryEnd) {
        return (root, query, cb) -> {
            if (queryStart == null && queryEnd == null) return cb.conjunction();
            Path<LocalDateTime> listingStart = root.get(AVAILABILITY_PERIOD_START);
            Path<LocalDateTime> listingEnd = root.get(AVAILABILITY_PERIOD_END);
            if (queryStart != null && queryEnd != null)
                return cb.and(cb.greaterThanOrEqualTo(listingEnd, queryStart),
                        cb.lessThanOrEqualTo(listingStart, queryEnd));
            if (queryStart != null) return cb.greaterThanOrEqualTo(listingEnd, queryStart);
            return cb.lessThanOrEqualTo(listingStart, queryEnd);
        };
    }
}
