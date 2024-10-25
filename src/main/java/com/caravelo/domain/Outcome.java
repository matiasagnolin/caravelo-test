package com.caravelo.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Outcome {
    private static final Logger logger = LoggerFactory.getLogger(Outcome.class);
    private final Integer target;
    private final FlightInventoryStatus inventoryStatus;

    private Outcome(Builder builder) {
        this.target = builder.target;
        this.inventoryStatus = builder.inventoryStatus;
    }

    public Integer getTarget() {
        return target;
    }

    public FlightInventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }


    public static class Builder {
        private Integer target;
        private FlightInventoryStatus inventoryStatus;

        public Builder withTarget(Integer target) {
            this.target = target;
            return this;
        }

        public Builder withInventoryStatus(FlightInventoryStatus inventoryStatus) {
            this.inventoryStatus = inventoryStatus;
            return this;
        }


        public Outcome build() {
            if (target == null || inventoryStatus == null) {
                //since for some test cases the important outcome is the event created and not the endpoint actual return.
                logger.warn("Target is: {} and Inventory Status: {}", target, inventoryStatus);
            }
            return new Outcome(this);
        }
    }
}
