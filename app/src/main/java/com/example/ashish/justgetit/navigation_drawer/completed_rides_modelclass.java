package com.example.ashish.justgetit.navigation_drawer;

public class completed_rides_modelclass {

    private String journeydate, journeytime, fare, pickuplocation, droplocation;

    public completed_rides_modelclass(String journeydate, String journeytime, String fare, String pickuplocation, String droplocation) {
        this.journeydate = journeydate;
        this.journeytime = journeytime;
        this.fare = fare;
        this.pickuplocation = pickuplocation;
        this.droplocation = droplocation;

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

    public completed_rides_modelclass() {


    }

    public String getFare() {
        return fare;
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

    public void setFare(String fare) {
        this.fare = fare;
    }

}
