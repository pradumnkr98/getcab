package com.example.ashish.justgetit.navigation_drawer;

public class completed_rides_modelclass {

    private String date, time, amount, vehicle;

    public completed_rides_modelclass(String date, String time, String amount, String vehicle) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.vehicle = vehicle;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAmount() {
        return amount;
    }

    public String getVehicle() {
        return vehicle;
    }
}
