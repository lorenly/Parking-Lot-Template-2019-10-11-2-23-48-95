package com.thoughtworks.parking_lot.model;

public class ParkingLot {
    private String name;
    private int capacity;
    private String location;

    public ParkingLot(){

    }

    public ParkingLot(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
