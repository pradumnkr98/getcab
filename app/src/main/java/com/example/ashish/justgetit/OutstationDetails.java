package com.example.ashish.justgetit;

public class OutstationDetails {
    String name;
    String number;
    String from;
    String to;
    int number_travellers;
    String travel_date;

    public OutstationDetails() {

    }

    public OutstationDetails(String name, String number, String from, String to, int number_travellers, String travel_date) {
        this.name = name;
        this.number = number;
        this.from = from;
        this.to = to;
        this.number_travellers = number_travellers;
        this.travel_date = travel_date;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getNumber_travellers() {
        return number_travellers;
    }

    public String getTravel_date() {
        return travel_date;
    }
}
