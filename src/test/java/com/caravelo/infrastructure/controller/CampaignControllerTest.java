package com.caravelo.infrastructure.controller;


import com.caravelo.application.CampaignExecutor;
import com.caravelo.domain.Flight;
import com.caravelo.domain.FlightInventoryStatus;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.exception.UnsupportedCampaignType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CampaignControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CampaignExecutor campaignExecutor;

    @InjectMocks
    private CampaignController campaignController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
    }

    @Test
    public void when_execute_request_then_200_response() throws Exception {
        //Given
        Flight flight = new Flight(100, 50);
        String email = "test@example.com";
        Outcome outcome = new Outcome.Builder()
                .withTarget(flight.getSold())
                .withInventoryStatus(FlightInventoryStatus.OPEN)
                .build();

        //When
        when(campaignExecutor.execute(any(Campaign.class), any(Flight.class), any(String.class)))
                .thenReturn(outcome);

        mockMvc.perform(post("/api/campaign/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"campaignType\": \"A\","
                                + "\"oversell\": 10,"
                                + "\"oversellFactor\": 0.1,"
                                + "\"capacity\": 100,"
                                + "\"sold\": 50,"
                                + "\"email\": \"" + email + "\""
                                + "}")
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk());
    }


    @Test
    public void when_execute_request_then_400_response() throws Exception {
        //Given
        String invalidCampaignJson = "{"
                + "\"campaignType\": \"Z\","
                + "\"oversell\": 10,"
                + "\"oversellFactor\": 0.1,"
                + "\"capacity\": 100,"
                + "\"sold\": 50"
                + "\"isLongHaul\": true"
                + "\"email\": test@example.com"
                + "}";

        Mockito.when(campaignExecutor.execute(Mockito.any(Campaign.class), Mockito.any(Flight.class), Mockito.any(String.class)))
                .thenThrow(new UnsupportedCampaignType("Campaign type: Z is not supported."));

        //When
        mockMvc.perform(post("/api/campaign/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCampaignJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
