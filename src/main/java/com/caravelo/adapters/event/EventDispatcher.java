package com.caravelo.adapters.event;

import com.google.common.eventbus.EventBus;
import com.caravelo.domain.Flight;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.event.NotifyOutcome;
import com.caravelo.domain.event.ReviewFlight;
import com.caravelo.domain.event.ToCleaningQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.caravelo.ports.EventPublisher;

@Component
public class EventDispatcher implements EventPublisher {

    private final EventBus eventBus;

    @Autowired
    public EventDispatcher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void dispatchOutcomeEvent(String email, Outcome outcome, Campaign campaign) {
        eventBus.post(new NotifyOutcome(campaign.getType(), email, outcome));
    }

    @Override
    public void dispatchReviewEvent(Flight flight) {
        eventBus.post(new ReviewFlight(flight));
    }

    @Override
    public void dispatchCleaningEvent(Flight flight) {
        eventBus.post(new ToCleaningQueue(flight));
    }
}
