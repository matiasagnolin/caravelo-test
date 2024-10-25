package com.caravelo.support;

import com.caravelo.domain.event.CampaignEvent;
import com.google.common.eventbus.Subscribe;

public class DummyEventListener {
    private CampaignEvent lastEvent;

    @Subscribe
    public void on(CampaignEvent event) {
        this.lastEvent = event;
    }

    public Class<? extends CampaignEvent> getLastEventType() {
        return lastEvent.getClass();
    }

    public <T extends CampaignEvent> T getLastEvent() {
        return (T) lastEvent;
    }
}
