package com.example.ashish.justgetit.navigation_drawer;

public class completed_rides_modelclass {

    private String journeydate, journeytime, amount, vehicle, pickuplocation, droplocation, payment;

    public completed_rides_modelclass(String journeydate, String journeytime, String amount, String vehicle, String pickuplocation, String droplocation, String payment) {
        this.journeydate = journeydate;
        this.journeytime = journeytime;
        this.amount = amount;
        this.vehicle = vehicle;
        this.pickuplocation = pickuplocation;
        this.droplocation = droplocation;
        this.payment = payment;
    }

    public String getJourneydate() {
        return journeydate;
    }

    public void setJourneydate(String journeydate) {
        this.journeydate = journeydate;
    }

    public String getJourneytime() {
        return journeytime;
    }

    public void setJourneytime(String journeytime) {
        this.journeytime = journeytime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getPickuplocation() {
        return pickuplocation;
    }

    public void setPickuplocation(String pickuplocation) {
        this.pickuplocation = pickuplocation;
    }

    public String getDroplocation() {
        return droplocation;
    }

    public void setDroplocation(String droplocation) {
        this.droplocation = droplocation;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
