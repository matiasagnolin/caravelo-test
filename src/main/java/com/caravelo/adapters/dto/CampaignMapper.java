package com.caravelo.adapters.dto;

import com.caravelo.domain.Flight;
import com.caravelo.domain.campaign.Campaign;

public class CampaignMapper {
    public static Campaign mapToCampaign(CampaignRequest request) {
        return new Campaign(request.getCampaignType(), request.getOversell(), request.getOversellFactor());
    }

    public static Flight mapToFlight(CampaignRequest request) {
        Flight flight = new Flight(request.getFlightCapacity(), request.getFlightSold());
        flight.setLongHaul(request.isLongHaul());
        return flight;
    }
}
