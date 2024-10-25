package com.caravelo.domain.exception;

public class UnsupportedCampaignType extends RuntimeException {
    public UnsupportedCampaignType(String message) {
        super(message);
    }

    public UnsupportedCampaignType(String message, Throwable cause) {
        super(message, cause);
    }
}
