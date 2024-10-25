package com.caravelo.infrastructure.controller;

import com.caravelo.adapters.dto.CampaignMapper;
import com.caravelo.adapters.dto.CampaignRequest;
import com.caravelo.application.CampaignExecutor;
import com.caravelo.domain.Flight;
import com.caravelo.domain.Outcome;
import com.caravelo.domain.campaign.Campaign;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/api/campaign")
public class CampaignController {

    private final CampaignExecutor campaignExecutor;

    @Autowired
    public CampaignController(CampaignExecutor campaignExecutor) {
        this.campaignExecutor = campaignExecutor;
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute a campaign for a specific flight",
            description = "Executes a given campaign based on provided details of a flight and an email.")
    public ResponseEntity<Outcome> executeCampaign(
            @RequestBody(
                    description = "Details of the campaign to execute",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CampaignRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody CampaignRequest campaignRequest) {

        Campaign campaign = CampaignMapper.mapToCampaign(campaignRequest);
        Flight flight = CampaignMapper.mapToFlight(campaignRequest);

        Outcome outcome = campaignExecutor.execute(campaign, flight, campaignRequest.getEmail());

        return ResponseEntity.ok(outcome);
    }
}
