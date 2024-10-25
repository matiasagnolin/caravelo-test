package com.caravelo.ports;


import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.Flight;
import com.caravelo.domain.Outcome;

public interface CampaignStrategy {
    Outcome execute(Campaign campaign, Flight flight, String email);
}
