package com.caravelo.adapters.campaign;

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
public class CampaignC implements CampaignStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CampaignC.class);
    private final EventPublisher eventPublisher;

    @Autowired
    public CampaignC(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Outcome execute(Campaign campaign, Flight flight, String email) {
        logger.info("Executing Campaign C");
        Outcome.Builder outcome = new Outcome.Builder();

        if (campaign.isOverBookedLoadFactor(flight)) {
            logger.info("Campaign C with target {} is over booked calculated by load factor.",
                    flight.getCapacity());
            outcome.withTarget(flight.getCapacity());
        } else {
            logger.info("Campaign C with target {} is not over booked calculated by load factor.",
                    flight.getCapacity());
            eventPublisher.dispatchCleaningEvent(flight);
        }

        return outcome.build();
    }
}
