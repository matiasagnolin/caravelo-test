package com.caravelo.adapters.config;

import com.caravelo.adapters.campaign.CampaignA;
import com.caravelo.adapters.campaign.CampaignB;
import com.caravelo.adapters.campaign.CampaignC;
import com.caravelo.domain.campaign.CampaignType;
import com.caravelo.ports.CampaignStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CampaignConfiguration {
    @Bean
    public Map<CampaignType, CampaignStrategy> campaignStrategies(CampaignA campaignA, CampaignB campaignB, CampaignC campaignC) {
        Map<CampaignType, CampaignStrategy> strategies = new HashMap<>();
        strategies.put(CampaignType.A, campaignA);
        strategies.put(CampaignType.B, campaignB);
        strategies.put(CampaignType.C, campaignC);
        return strategies;
    }
}
