package com.caravelo.application;

import com.caravelo.adapters.campaign.CampaignA;
import com.caravelo.adapters.campaign.CampaignB;
import com.caravelo.adapters.campaign.CampaignC;
import com.caravelo.domain.Flight;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.campaign.Campaign;
import com.caravelo.domain.campaign.CampaignType;
import com.caravelo.domain.exception.UnsupportedCampaignType;
import com.caravelo.ports.CampaignStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CampaignExecutorTest {

    @Mock
    private Map<CampaignType, CampaignStrategy> campaignStrategies;

    @Mock
    private CampaignA campaignA;

    @Mock
    private CampaignB campaignB;

    @Mock
    private CampaignC campaignC;

    @InjectMocks
    private CampaignExecutor campaignExecutor;

    private Flight flight;
    private Campaign campaign;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        flight = new Flight(100, 50);
        when(campaignStrategies.get(CampaignType.A)).thenReturn(campaignA);
        when(campaignStrategies.get(CampaignType.B)).thenReturn(campaignB);
        when(campaignStrategies.get(CampaignType.C)).thenReturn(campaignC);
    }

    @Test
    public void when_execute_CampaignA_outcome_should_be_as_expected() {
        campaign = new Campaign(CampaignType.A, 0, 0);
        Outcome expectedOutcome = new Outcome.Builder()
                .build();
        when(campaignA.execute(campaign, flight, "test@example.com")).thenReturn(expectedOutcome);

        Outcome outcome = campaignExecutor.execute(campaign, flight, "test@example.com");

        assertEquals(expectedOutcome, outcome);
        verify(campaignA).execute(campaign, flight, "test@example.com");
    }

    @Test
    public void when_execute_CampaignB_outcome_should_be_as_expected() {
        campaign = new Campaign(CampaignType.B, 0, 0);
        Outcome expectedOutcome = new Outcome.Builder()
                .build();
        when(campaignB.execute(campaign, flight, "test@example.com")).thenReturn(expectedOutcome);

        Outcome outcome = campaignExecutor.execute(campaign, flight, "test@example.com");

        assertEquals(expectedOutcome, outcome);
        verify(campaignB).execute(campaign, flight, "test@example.com");
    }

    @Test
    public void when_execute_CampaignC_outcome_should_be_as_expected() {
        campaign = new Campaign(CampaignType.C, 0, 0);
        Outcome expectedOutcome = new Outcome.Builder()
                .build();
        when(campaignC.execute(campaign, flight, "test@example.com")).thenReturn(expectedOutcome);

        Outcome outcome = campaignExecutor.execute(campaign, flight, "test@example.com");

        assertEquals(expectedOutcome, outcome);
        verify(campaignC).execute(campaign, flight, "test@example.com");
    }

    @Test
    public void when_execute_CampaignZ_UnsupportedCampaignType_should_be_thrown() {
        campaign = new Campaign(CampaignType.Z, 0, 0);

        assertThrows(UnsupportedCampaignType.class, () -> {
            campaignExecutor.execute(campaign, flight, "test@example.com");
        });
    }

}
