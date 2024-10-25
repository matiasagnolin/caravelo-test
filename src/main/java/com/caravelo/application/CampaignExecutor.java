package com.caravelo.application;

import com.caravelo.domain.Flight;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.campaign.CampaignType;
import com.caravelo.domain.exception.UnsupportedCampaignType;
import com.caravelo.ports.CampaignStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CampaignExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CampaignExecutor.class);

    private final Map<CampaignType, CampaignStrategy> campaigns;

    @Autowired
    public CampaignExecutor(Map<CampaignType, CampaignStrategy> campaigns) {
        this.campaigns = campaigns;
    }

    public Outcome execute(Campaign campaign, Flight flight, String email) {
        logger.info("Executing campaign of type: {}", campaign.getType());

        //As answered in QUESTIONS.md, I like to implement strategy pattern and Inversion of control
        //which gives more readable code, flexibility, SOLID principles, reusability and low maintenance
        Optional<CampaignStrategy> strategy = Optional.ofNullable(campaigns.get(campaign.getType()));

        if (strategy.isPresent()) {
            logger.info("strategy found for campaign type: {}", campaign.getType());

            final Outcome outcome = strategy.get().execute(campaign, flight, email);

            logger.info("Campaign execution completed for type: {}", campaign.getType());
            return outcome;
        } else {
            logger.error("No strategy found for campaign type: {}", campaign.getType());
            throw new UnsupportedCampaignType("Campaign type: " + campaign.getType() + " is not supported.");
        }
    }
}
