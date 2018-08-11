package com.example.ashish.justgetit.navigation_drawer;

public class future_ride_modelclass {

    private String date, time, amount, vehicle, from, to, payment;

    public future_ride_modelclass(String date, String time, String amount, String vehicle, String from, String to, String payment) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.vehicle = vehicle;
        this.from = from;
        this.to = to;
        this.payment = payment;

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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getPayment() {
        return payment;
    }
}
