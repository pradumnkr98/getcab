package com.example.ashish.justgetit;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class PlacesInfo {
    private String name;
    private String address;
    private String phonenumber;
    private String id;
    private Uri websiteuri;
    private LatLng latLng;
    private float rating;
    private String attribution;

    public PlacesInfo(String name, String address, String phonenumber, String id, Uri websiteuri, LatLng latLng, float rating, String attribution) {

        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.id = id;
        this.websiteuri = websiteuri;
        this.latLng = latLng;
        this.rating = rating;
        this.attribution = attribution;
    }


    public PlacesInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getWebsiteuri() {
        return websiteuri;
    }

    public void setWebsiteuri(Uri websiteuri) {
        this.websiteuri = websiteuri;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    @Override
    public String toString() {
        return "PlacesInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", id='" + id + '\'' +
                ", websiteuri=" + websiteuri +
                ", latLng=" + latLng +
                ", rating=" + rating +
                ", attribution='" + attribution + '\'' +
                '}';
    }
}
