package com.caravelo.adapters.dto;

public class FlightRequest {
    private int capacity;
    private int sold;
    private boolean isLongHaul;

    public FlightRequest() {
    }

    public FlightRequest(int capacity, int sold, boolean isLongHaul) {
        this.capacity = capacity;
        this.sold = sold;
        this.isLongHaul = isLongHaul;
    }

    // Getters and Setters
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public boolean isLongHaul() {
        return isLongHaul;
    }

    public void setLongHaul(boolean longHaul) {
        isLongHaul = longHaul;
    }
}
