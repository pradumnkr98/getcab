package com.example.ashish.justgetit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class homepage1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    public GoogleMap mMap;
    Button bt1;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Boolean locatonpermissiongranted = false;
    public static final float DEFAULT_ZOOM = 15f;
    public FusedLocationProviderClient mfusedlocationproviderclient;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage1);

        //SETTING UP NAVIGATION DRAWER

        drawerLayout = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //click listener for items in bottom_navigation view

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mini:
                        return true;
                    case R.id.micro:
                        return true;
                    case R.id.prime:
                        return true;
                    default:
                        return false;
                }


            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(homepage1.this);

    }


    //Method to close drawer by back key pressing
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//making the navigation icon workable

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.current_duty:
                Intent intent1 = new Intent(homepage1.this, current_duty.class);
                startActivity(intent1);
                return true;

            case R.id.available_booking:
                Intent intent2 = new Intent(homepage1.this, available_booking.class);
                startActivity(intent2);
                return true;

            case R.id.future_duties:
                Intent intent3 = new Intent(homepage1.this, future_duties.class);
                startActivity(intent3);

            case R.id.recharge://to be completed by aadil
                Intent intent4 = new Intent(homepage1.this, recharge.class);
                startActivity(intent4);
                return true;

            case R.id.account_details:
                Intent intent5 = new Intent(homepage1.this, account_details.class);
                startActivity(intent5);
                return true;

            case R.id.driver_incentives:
                Intent intent6 = new Intent(homepage1.this, driver_incentives.class);
                startActivity(intent6);
                return true;

            case R.id.prime:
                Intent intent7 = new Intent(homepage1.this, profile.class);
                startActivity(intent7);
                return true;

            default:
                return false;
        }

        //  return false;
    }

//THE CODE FOR GOOGLE MAPS


    //requesting permission from user to access device location
    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locatonpermissiongranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permission, 1234);
            }
    }

    //code for checking the granted permission is true or false

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locatonpermissiongranted = false;
        switch (requestCode) {
            case 1234: {
                //If request is cancelled the result arrays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locatonpermissiongranted = true;

                }
                locatonpermissiongranted = true;
            }
        }
        updateLocationUI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        //turn on the my location layer and the related control on the map
        updateLocationUI();
        if (locatonpermissiongranted) {
            getDeviceLocation();

        }
    }

    public void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locatonpermissiongranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }else{
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                Location currentLocation=null;
                getLocationPermission();
            }

        }catch (SecurityException e){
            Log.e("Exception: %s",e.getMessage());
        }
    }
    public void getDeviceLocation() {
        mfusedlocationproviderclient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (locatonpermissiongranted) {
                 Task location = mfusedlocationproviderclient.getLastLocation();
                location.addOnCompleteListener(this,new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        } else {
                            Toast.makeText(homepage1.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        } catch (SecurityException e) {
            Log.d("homepage1", "getDevicelocation: SecurityException:" + e.getMessage());
        }

    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

}
