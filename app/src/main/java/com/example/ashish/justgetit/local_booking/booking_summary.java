package com.example.ashish.justgetit.local_booking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.customer_booking_details;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class booking_summary extends AppCompatActivity {
    Toolbar toolbar;
    Button book_cab;
    String pickup_location, drop_location, pick_time, pickup_date, phoneno, name, fare;
    TextView pick_location, drop_location0, journey_time, journey_date, carname;
    ImageView car_image;
    String fare1;

    DatabaseReference reference;
    FirebaseAuth auth;

    public int radius = 1;
    public Boolean driverfound = false;
    LatLng pickuplocation;
    private String driverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);
        toolbar = findViewById(R.id.summary_toolbar);
        book_cab = findViewById(R.id.book_cab);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        pick_location = findViewById(R.id.Pick_up);
        drop_location0 = findViewById(R.id.drop_location);
        journey_time = findViewById(R.id.time);
        journey_date = findViewById(R.id.date);
        carname = findViewById(R.id.carname);
        car_image = findViewById(R.id.car_image);

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            fare1 = bundle.getString("fare");
            Log.e("fare", fare1);
            String car_name = bundle.getString("carname");
            carname.setText(car_name);
            Log.e("carname", car_name);
            String carimage = bundle.getString("carimage");
            Log.e("carimage", carimage);
            Picasso.with(this).load(carimage).resize(80, 50).into(car_image);

        }
        book_cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeuserdata(phoneno, name, pickup_location, drop_location, fare1, pickup_date, pick_time);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cab_request");

                LatLng location;
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                location = getLocationFromAddress(booking_summary.this, pickup_location);
                GeoFire geoFire = new GeoFire(ref);
                Log.e("locationpickup", location.latitude + location.longitude + "");
                geoFire.setLocation(userID, new GeoLocation(location.latitude, location.longitude), new
                        GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {

                            }
                        });

                pickuplocation = new LatLng(location.latitude, location.latitude);
                book_cab.setText("Getting Your Driver! Pls Wait..");
                getdriveravailable();


            }
        });




        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pickup_location = preferences.getString("pickup", "NULL");
        drop_location = preferences.getString("drop", "NULL");
        pickup_date = preferences.getString("pickdate", "NULL");
        pick_time = preferences.getString("picktime", "NULL");
        phoneno = preferences.getString("Phone No", "");
        name = preferences.getString("Name", "");
        Log.e("drop_location", drop_location);



        pick_location.setText(pickup_location);
        drop_location0.setText(drop_location);
        journey_time.setText(pick_time);
        journey_date.setText(pickup_date);
    }

    public void writeuserdata(String phoneno, String name, String pickup, String drop, String fare, String journeyDate, String journeyTime) {
        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();
        Log.e("userid", userID);

        customer_booking_details customer_booking_details = new customer_booking_details(phoneno, name, pickup, drop, fare, journeyDate, journeyTime);
        reference.child("Bookings Details").child(userID).setValue(customer_booking_details);


    }

    private void getdriveravailable() {
        DatabaseReference driveravailable = FirebaseDatabase.getInstance().getReference().child("drivers_available");

        GeoFire geoFire = new GeoFire(driveravailable);
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(pickuplocation.latitude, pickuplocation.longitude), radius);
        geoQuery.removeAllListeners();

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!driverfound) {
                    driverfound = true;
                    driverId = key;

                    getdriverlocation();
                }

            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if (!driverfound) {
                    radius++;
                    getdriveravailable();
                } else {
                    Toast.makeText(booking_summary.this, "Driver Not Found !! Try after some Time", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

    public void getdriverlocation() {
        DatabaseReference driverlocation = FirebaseDatabase.getInstance().getReference().child("drivers_available").child(driverId).child("l");
        driverlocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Object> map = (List<Object>) dataSnapshot.getValue();
                    double latitude = 0;
                    double longitude = 0;
                    if (map.get(0) != null && map.get(1) != null) {
                        latitude = Double.parseDouble(map.get(0).toString());
                        longitude = Double.parseDouble(map.get(1).toString());
                        Intent intent = new Intent(booking_summary.this, getting_nearby_driver.class);
                        intent.putExtra("driverlat", latitude);
                        intent.putExtra("driverlong", longitude);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(booking_summary.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("driverlat", latitude + "");
                        editor.putString("driverlong", longitude + "");
                        Log.e("driverlat", latitude + "");
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Converting string address to latlng

    public LatLng getLocationFromAddress(Context context, String inputtedAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng resLatLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return resLatLng;
    }
}
