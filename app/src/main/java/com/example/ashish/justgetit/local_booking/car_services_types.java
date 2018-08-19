package com.example.ashish.justgetit.local_booking;

public class car_services_types {
    String car_image;
    String car_name;
    Long fare;

    public car_services_types(String car_image, String car_name, Long fare) {
        this.car_image = car_image;
        this.car_name = car_name;
        this.fare = fare;
    }

    public car_services_types() {

    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public Long getFare() {
        return fare;
    }

    public void setFare(Long fare) {
        this.fare = fare;
    }
}
