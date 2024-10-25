package com.caravelo.domain;


import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.campaign.CampaignType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CampaignTest {

    private Campaign campaign;
    private Flight flight;

    @BeforeEach
    void setUp() {
        campaign = new Campaign(CampaignType.A, 10, 0.15f);
        flight = mock(Flight.class);
    }

    @Test
    void when_calculate_absolute_target_should_be_equal_capacity_plus_oversell() {
        // Given
        when(flight.getCapacity()).thenReturn(100);

        // When
        int target = campaign.calculateAbsoluteTarget(flight);

        // Then
        assertEquals(110, target, "Absolute target should equal flight capacity + oversell");
    }

    @Test
    void when_calculate_relative_target_should_be_equals_to_flight_capacity_plus_oversell_factor_applied() {
        // Given
        when(flight.getCapacity()).thenReturn(100);

        // When
        int target = campaign.calculateRelativeTarget(flight);

        // Then
        assertEquals(115, target, "Relative target should equal flight capacity + oversell factor applied");
    }

    @Test
    void when_calculate_over_booked_based_on_load_factor_should_be_true() {
        // Given
        when(flight.getCapacity()).thenReturn(100);
        when(flight.getSold()).thenReturn(110);

        // When
        boolean isOverBooked = campaign.isOverBookedLoadFactor(flight);

        // Then
        assertTrue(isOverBooked, "Flight should be considered overbooked based on load factor");
    }

    @Test
    void when_calculate_over_booked_based_on_load_factor_should_be_false() {
        // Given
        when(flight.getCapacity()).thenReturn(100);
        when(flight.getSold()).thenReturn(90);

        // When
        boolean isOverBooked = campaign.isOverBookedLoadFactor(flight);

        // Then
        assertFalse(isOverBooked, "Flight should not be considered overbooked based on load factor");
    }

    @Test
    void when_sold_over_capacity_then_flight_closed() {
        // Given
        when(flight.getCapacity()).thenReturn(100);
        when(flight.getSold()).thenReturn(100);

        // When
        boolean isClosed = campaign.isClosed(flight);

        // Then
        assertTrue(isClosed, "Flight should be considered closed when sold equals capacity");
    }

    @Test
    void when_sold_over_capacity_then_flight_open() {
        // Given
        when(flight.getCapacity()).thenReturn(100);
        when(flight.getSold()).thenReturn(90);

        // When
        boolean isClosed = campaign.isClosed(flight);

        // Then
        assertFalse(isClosed, "Flight should not be considered closed when sold is less than capacity");
    }

    @Test
    void when_long_haul_and_over_capacity_then_flight_needs_review() {
        // Given
        when(flight.getCapacity()).thenReturn(100);
        when(flight.getSold()).thenReturn(60);
        when(flight.isLongHaul()).thenReturn(true);
        int target = campaign.calculateRelativeTarget(flight);

        // When
        boolean needsReview = campaign.needsReview(flight, target);

        // Then
        assertTrue(needsReview, "Flight should need review when sold plus target exceeds capacity - safety margin");
    }

    @Test
    void when_long_haul_and_below_capacity_flight_should_not_need_review() {
        // Given
        when(flight.getCapacity()).thenReturn(100);
        when(flight.getSold()).thenReturn(60);
        when(flight.isLongHaul()).thenReturn(false);
        int target = campaign.calculateRelativeTarget(flight);

        // When
        boolean needsReview = campaign.needsReview(flight, target);

        // Then
        assertFalse(needsReview, "Flight should not need review when sold plus target does not exceed capacity - safety margin");
    }
}
