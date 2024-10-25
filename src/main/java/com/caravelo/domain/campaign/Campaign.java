package com.caravelo.domain.campaign;

import com.caravelo.domain.Flight;

public class Campaign {
    private final CampaignType type;
    private final int oversell;
    private final double oversellFactor;

    public Campaign(CampaignType type, int oversell, float oversellFactor) {
        this.type = type;
        this.oversell = oversell;
        this.oversellFactor = oversellFactor;
    }

    // 1. Determine absolute target
    public int calculateAbsoluteTarget(Flight flight) {
        return flight.getCapacity() + oversell;
    }

    // 1. Determine relative target
    public int calculateRelativeTarget(Flight flight) {
        double result = flight.getCapacity() + (flight.getCapacity() * oversellFactor);
        return (int) Math.round(result); // Rounds to the nearest whole number of passengers
    }

    // 1. Determine target by load factor
    public boolean isOverBookedLoadFactor(Flight flight) {
        return flight.getSold().doubleValue() / flight.getCapacity() > 1; // Cast to double for division
    }

    public CampaignType getType() {
        return type;
    }

    public boolean isClosed(Flight flight) {
        return flight.getSold() >= flight.getCapacity();
    }

    public boolean needsReview(Flight flight, int target) {
        int safetyMargin = flight.isLongHaul() ? 40 : 10;
        return (flight.getCapacity() - safetyMargin < flight.getSold() + (target - flight.getCapacity()));
    }
}
