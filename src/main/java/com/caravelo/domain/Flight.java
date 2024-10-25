package com.caravelo.domain;

public class Flight {
    private int capacity;
    private int sold;
    private boolean longHaul;

    public Flight(int capacity, int sold) {
        this.capacity = capacity;
        this.sold = sold;
        this.longHaul = false;
    }

    public boolean isLongHaul() {
        return longHaul;
    }

    public void setLongHaul(boolean longHaul) {
        this.longHaul = longHaul;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Integer getSold() {
        return sold;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
