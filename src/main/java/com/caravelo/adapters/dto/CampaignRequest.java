package com.caravelo.adapters.dto;

import com.caravelo.domain.campaign.CampaignType;

public class CampaignRequest {
    private CampaignType campaignType;
    private int oversell;
    private float oversellFactor;
    private int flightCapacity;
    private int flightSold;
    private boolean isLongHaul;
    private String email;

   public CampaignRequest(CampaignType campaignType, int oversell, float oversellFactor,
                           int flightCapacity, int flightSold, boolean isLongHaul, String email) {
        this.campaignType = campaignType;
        this.oversell = oversell;
        this.oversellFactor = oversellFactor;
        this.flightCapacity = flightCapacity;
        this.flightSold = flightSold;
        this.isLongHaul = isLongHaul;
        this.email = email;
    }


    public CampaignRequest() {
    }

    public CampaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType) {
        this.campaignType = campaignType;
    }

    public int getOversell() {
        return oversell;
    }

    public void setOversell(int oversell) {
        this.oversell = oversell;
    }

    public float getOversellFactor() {
        return oversellFactor;
    }

    public void setOversellFactor(float oversellFactor) {
        this.oversellFactor = oversellFactor;
    }

    public int getFlightCapacity() {
        return flightCapacity;
    }

    public void setFlightCapacity(int flightCapacity) {
        this.flightCapacity = flightCapacity;
    }

    public int getFlightSold() {
        return flightSold;
    }

    public void setFlightSold(int flightSold) {
        this.flightSold = flightSold;
    }

    public boolean isLongHaul() {
        return isLongHaul;
    }

    public void setLongHaul(boolean longHaul) {
        isLongHaul = longHaul;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
