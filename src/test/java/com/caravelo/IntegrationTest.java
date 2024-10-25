package com.caravelo;


import com.caravelo.domain.Flight;
import com.caravelo.domain.FlightInventoryStatus;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.event.NotifyOutcome;
import com.caravelo.support.DummyEventListener;
import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventBus eventBus;

    private final String email = "test@example.com";

    private final Flight flight = new Flight(100, 50);

    @Test
    public void when_execute_request_then_200_response_outcome_is_valid_and_events_were_published() throws Exception {
        //given
        DummyEventListener listener = new DummyEventListener();
        register(listener);
        Outcome outcome = new Outcome.Builder()
                .withTarget(10)
                .withInventoryStatus(FlightInventoryStatus.CLOSED)
                .build();

        //when
        mockMvc.perform(post("/api/campaign/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"campaignType\": \"A\","
                                + "\"oversell\": 10,"
                                + "\"oversellFactor\": 0.1,"
                                + "\"capacity\": \"" + flight.getCapacity() + "\","
                                + "\"sold\": \"" + flight.getSold() + "\","
                                + "\"email\": \"" + email + "\""
                                + "}")
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.target").value(10))
                .andExpect(jsonPath("$.inventoryStatus").value("CLOSED"));

        NotifyOutcome notify = listener.getLastEvent();

        assertEquals(email, notify.getEmail());
        assertEquals(outcome.getInventoryStatus(),
                notify.getOutcome().getInventoryStatus());
        assertEquals(outcome.getTarget(),
                notify.getOutcome().getTarget());

    }


    private void register(Object listener) {
        this.eventBus.register(listener);
    }

}
