package com.example.ashish.justgetit.navigation_drawer;

public class completed_rides_modelclass {

    private String date, time, amount, vehicle, from, to, payment;

    public completed_rides_modelclass(String date, String time, String amount, String vehicle, String from, String to, String payment) {
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
