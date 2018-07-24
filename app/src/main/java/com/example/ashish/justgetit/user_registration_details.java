package com.example.ashish.justgetit;

public class user_registration_details {
    String name;
    String phone_no;
    String email;
    String password;

    public user_registration_details() {

    }

    public user_registration_details(String name, String phone_no, String email) {
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getEmail() {
        return email;
    }


}
