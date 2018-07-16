package com.example.ashish.justgetit;

public class car_services_types {
    int carimage;
    String car_type;

    public car_services_types(int carimage, String car_type) {
        this.carimage = carimage;
        this.car_type = car_type;
    }

    public int getCarimage() {
        return carimage;
    }

    public void setCarimage(int carimage) {
        this.carimage = carimage;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }
}
