package com.example.ashish.justgetit.outstation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.customer_booking_details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class roundway_bookingsummary extends AppCompatActivity {
    Toolbar toolbar;
    Button book_cab;
    String pickup_location, drop_location = null, pick_time, pickup_date, phoneno, name, fare;
    TextView pick_location, journey_time, journey_date;

    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundway_bookingsummary);
        toolbar = findViewById(R.id.summary_toolbar);
        book_cab = findViewById(R.id.book_cab);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        pick_location = findViewById(R.id.Pick_up);
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
                Intent intent = new Intent(roundway_bookingsummary.this, roundway_gettingnearbydriver.class);
                writeuserdata(phoneno, name, pickup_location, drop_location, null, pickup_date, pick_time);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pickup_location = preferences.getString("pickup", "NULL");
        drop_location = preferences.getString("drop", "NULL");
        pickup_date = preferences.getString("pickdate", "NULL");
        pick_time = preferences.getString("picktime", "NULL");
        phoneno = preferences.getString("Phone No", "");
        name = preferences.getString("Name", "");


        pick_location.setText(pickup_location);
        journey_time.setText(pick_time);
        journey_date.setText(pickup_date);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Log.e("fare", bundle.getDouble("fare") + "");
        }
    }

    public void writeuserdata(String phoneno, String name, String pickup, String drop, String fare, String journeyDate, String journeyTime) {
        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();
        customer_booking_details customer_booking_details = new customer_booking_details(phoneno, name, pickup, drop, fare, journeyDate, journeyTime);
        reference.child("Bookings Details").child(userID).setValue(customer_booking_details);

    }
}
