package com.example.ashish.justgetit.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ashish.justgetit.local_booking.getting_nearby_driver;

public class current_ride extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(current_ride.this, getting_nearby_driver.class);
        startActivity(intent);
    }
}
