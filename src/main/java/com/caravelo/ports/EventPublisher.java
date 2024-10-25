package com.caravelo.ports;

import com.caravelo.domain.Flight;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.campaign.Campaign;

public interface EventPublisher {
     void dispatchOutcomeEvent(String email, Outcome outcome, Campaign campaign);
     void dispatchReviewEvent(Flight flight);
     void dispatchCleaningEvent(Flight flight);
}
