package com.example.ashish.justgetit.outstation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ashish.justgetit.R;

public class outstation_roundway extends AppCompatActivity {

    private Button confirm_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstation_roundway);

        confirm_booking = findViewById(R.id.roundway_next);

        confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(outstation_roundway.this, roundway_finalbooking.class);
                startActivity(intent);

            }
        });
    }
}
