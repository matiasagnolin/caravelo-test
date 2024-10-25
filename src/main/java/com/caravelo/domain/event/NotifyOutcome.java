package com.caravelo.domain.event;

import com.caravelo.domain.campaign.CampaignType;
import com.caravelo.domain.Outcome;


public class NotifyOutcome implements CampaignEvent{
    private final Outcome outcome;
    private final String email;

    public NotifyOutcome(CampaignType type, String email, Outcome outcome) {
        this.email = email;
        this.outcome = outcome;
        //out of scope

    }

    public String getEmail() {
        return email;
    }

    public Outcome getOutcome() {
        return outcome;
    }
}
