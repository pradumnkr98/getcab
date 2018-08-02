package com.example.ashish.justgetit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ashish.justgetit.local_booking.getting_nearby_driver;

public class oneway_bookingsummary extends AppCompatActivity {
    Toolbar toolbar;
    Button book_cab;
    String pickup_location, drop_location, pick_time, pickup_date;
    TextView pick_location, drop_location0, journey_time, journey_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneway_bookingsummary);
        toolbar = findViewById(R.id.summary_toolbar);
        book_cab = findViewById(R.id.book_cab);

        pick_location = findViewById(R.id.Pick_up);
        drop_location0 = findViewById(R.id.drop_location);
        journey_time = findViewById(R.id.time);
        journey_date = findViewById(R.id.date);

        toolbar.setNavigationIcon(R.drawable.back_icon);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        book_cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(oneway_bookingsummary.this, getting_nearby_driver.class);
                startActivity(intent);
            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pickup_location = preferences.getString("pickup", "NULL");
        drop_location = preferences.getString("drop", "NULL");
        pickup_date = preferences.getString("pickdate", "NULL");
        pick_time = preferences.getString("picktime", "NULL");


        pick_location.setText(pickup_location);
        drop_location0.setText(drop_location);
        journey_time.setText(pick_time);
        journey_date.setText(pickup_date);
    }
}