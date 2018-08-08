package com.example.ashish.justgetit.navigation_drawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ashish.justgetit.R;

public class wallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
    }





   /* Bundle extras=getIntent().getExtras();
        if(extras!=null){
        pickuplocation=extras.getString("pickup");
    }
    Geocoder geocoder= new Geocoder(customer_map.this);
        try {
        list=geocoder.getFromLocationName(pickuplocation,1);
        Address location=list.get(0);
        p1=new LatLng(location.getLatitude(),location.getLongitude());
    } catch (IOException e) {
        e.printStackTrace();
    }*/
}
