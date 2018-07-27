package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class roundway_bookingsummary extends AppCompatActivity {
    Toolbar toolbar;
    Button book_cab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);
        toolbar = findViewById(R.id.summary_toolbar);
        book_cab = findViewById(R.id.book_cab);

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
                Intent intent = new Intent(roundway_bookingsummary.this, roundway_gettingnearbydriver.class);
                startActivity(intent);
            }
        });
    }
}
