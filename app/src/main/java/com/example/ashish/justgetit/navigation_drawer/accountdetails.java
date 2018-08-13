package com.example.ashish.justgetit.navigation_drawer;

public class accountdetails {
    String email;
    String name;
    String phoneno;

    public accountdetails(String email, String name, String phoneno) {
        this.email = email;
        this.name = name;
        this.phoneno = phoneno;
    }

    public accountdetails() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
