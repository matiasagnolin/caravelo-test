package com.caravelo.adapters.campaign;

import com.caravelo.domain.FlightInventoryStatus;
import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.Flight;
import com.caravelo.domain.Outcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.caravelo.ports.CampaignStrategy;
import com.caravelo.ports.EventPublisher;

@Component
public class CampaignA implements CampaignStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CampaignA.class);

    private final EventPublisher eventPublisher;

    @Autowired
    public CampaignA(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Outcome execute(Campaign campaign, Flight flight, String email) {
        logger.info("Executing Campaign A ");

        int target = campaign.calculateAbsoluteTarget(flight);
        FlightInventoryStatus flightStatus =
                campaign.isClosed(flight) ? FlightInventoryStatus.CLOSED : FlightInventoryStatus.OPEN;

        Outcome outcome = new Outcome.Builder()
                .withTarget(target)
                .withInventoryStatus(flightStatus)
                .build();

        logger.info("Campaign A with target {} and flight status {}", target, flightStatus);

        eventPublisher.dispatchOutcomeEvent(email, outcome, campaign);
        return outcome;
    }
}
