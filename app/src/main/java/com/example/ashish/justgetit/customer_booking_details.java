package com.example.ashish.justgetit;

public class customer_booking_details {
    private String Phone_No;
    private String Name;
    private String Pickuplocation;
    private String Droplocation;
    private String Fare;
    private String Journeydate;
    private String Journeytime;

    public customer_booking_details() {

    }

    public customer_booking_details(String phone_No, String name, String pickup_Location, String drop_Location, String fare, String journey_Date, String journey_Time) {
        Phone_No = phone_No;
        Name = name;
        Pickuplocation = pickup_Location;
        Droplocation = drop_Location;
        Fare = fare;
        Journeydate = journey_Date;
        Journeytime = journey_Time;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPickuplocation() {
        return Pickuplocation;
    }

    public void setPickuplocation(String pickuplocation) {
        Pickuplocation = pickuplocation;
    }

    public String getDroplocation() {
        return Droplocation;
    }

    public void setDroplocation(String droplocation) {
        Droplocation = droplocation;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    public String getJourneydate() {
        return Journeydate;
    }

    public void setJourneydate(String journeydate) {
        Journeydate = journeydate;
    }

    public String getJourneytime() {
        return Journeytime;
    }

    public void setJourneytime(String journeytime) {
        Journeytime = journeytime;
    }
}
