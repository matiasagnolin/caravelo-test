package com.caravelo.adapters.campaign;

import com.caravelo.adapters.event.EventDispatcher;
import com.caravelo.domain.Flight;
import com.caravelo.domain.FlightInventoryStatus;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.campaign.CampaignType;
import com.caravelo.domain.event.NotifyOutcome;
import com.caravelo.support.DummyEventListener;
import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CampaignATest {

    public static final String EMAIL = "test@example.com";
    private CampaignA campaignAStrategy;
    private Flight flight;
    EventBus eventBus = new EventBus();
    EventDispatcher eventDispatcher = new EventDispatcher(eventBus);

    @BeforeEach
    public void setUp() {
        campaignAStrategy = new CampaignA(eventDispatcher);
        flight = new Flight(100, 50);
    }

    @Test
    public void when_execute_and_oversell_is_0_flight_capacity_is_target() {
        //given
        Campaign campaign = new Campaign(CampaignType.A, 0, 0);

        //when
        Outcome outcome = campaignAStrategy.execute(campaign, flight, EMAIL);

        //then
        assertEquals(flight.getCapacity(), outcome.getTarget());
    }

    @Test
    public void when_execute_oversell_is_added() {
        //given
        Campaign campaign = new Campaign(CampaignType.A, 20, 0.25f);

        //when
        Outcome outcome = campaignAStrategy.execute(campaign, flight, EMAIL);

        //then
        assertEquals( 120, outcome.getTarget().intValue());
    }

    @Test
    public void when_execute_flight_should_be_closed() {
        //given
        Campaign campaign = new Campaign(CampaignType.A, 0, 0);
        flight.setSold(flight.getCapacity());

        //when
        Outcome outcome = campaignAStrategy.execute(campaign, flight, EMAIL);

        //then
        assertEquals( FlightInventoryStatus.CLOSED, outcome.getInventoryStatus());
    }

    @Test
    public void when_execute_flight_should_be_open() {
        //given
        Campaign campaign = new Campaign(CampaignType.A, 0, 0);

        //when
        Outcome outcome = campaignAStrategy.execute(campaign, flight, EMAIL);

        //then
        assertEquals( FlightInventoryStatus.OPEN, outcome.getInventoryStatus());
    }


    @Test
    public void when_execute_event_should_be_created_and_match_with_outcome() {
        //given
        Campaign c = new Campaign(CampaignType.A, 15, 0);

        DummyEventListener listener = new DummyEventListener();
        register(listener);


        //when
        Outcome outcome = campaignAStrategy.execute(c, flight, EMAIL);
        NotifyOutcome notify = listener.getLastEvent();

        //then
        assertEquals( EMAIL, notify.getEmail());
        assertEquals( outcome.getInventoryStatus(),
                notify.getOutcome().getInventoryStatus());
    }

    private void register(Object listener) {
        this.eventBus.register(listener);
    }
}
