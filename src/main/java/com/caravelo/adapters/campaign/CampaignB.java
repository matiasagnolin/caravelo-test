package com.caravelo.adapters.campaign;

import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.Flight;
import com.caravelo.domain.FlightInventoryStatus;
import com.caravelo.domain.Outcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.caravelo.ports.CampaignStrategy;
import com.caravelo.ports.EventPublisher;

@Component
public class CampaignB implements CampaignStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CampaignB.class);

    private final EventPublisher eventPublisher;

    @Autowired
    public CampaignB(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Override
    public Outcome execute(Campaign campaign, Flight flight, String email) {
        logger.info("Executing Campaign B");

        Outcome.Builder outcome = new Outcome.Builder();
        int target = campaign.calculateRelativeTarget(flight);

        outcome.withTarget(target);

        if (campaign.needsReview(flight, target)) {
            logger.info("Campaign B with target {} and flight status Closed needs review.", target);
            outcome.withInventoryStatus(FlightInventoryStatus.CLOSED);
            eventPublisher.dispatchReviewEvent(flight);
        } else {
            logger.info("Campaign B with target {} and flight status Open finished.", target);
            outcome.withInventoryStatus(FlightInventoryStatus.OPEN);
        }

        return outcome.build();
    }
}
