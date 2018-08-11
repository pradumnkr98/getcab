package com.example.ashish.justgetit.local_booking;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.ashish.justgetit.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class getting_nearby_driver extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    Button request_cab;
    ProgressDialog progressDialog;
    LatLng pickuplocation;


    private int radius = 1;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);

    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //  com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        Log.e("location", latLng.toString());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.addMarker(new MarkerOptions().position(latLng).title("pickup here"));


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean driverfound = false;
    private String driverfoundid;
    private Marker mdrivermarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_nearby_driver);
        progressDialog = new ProgressDialog(getting_nearby_driver.this);
        progressDialog.show();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.customer_map);
        mapFragment.getMapAsync(this);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // request_cab.setText("requesting cab nearby...");

        getclosestdriver();


    }

    private void getclosestdriver() {
        DatabaseReference driverlocation = FirebaseDatabase.getInstance().getReference().child("Driver Available");
        GeoFire geoFire = new GeoFire(driverlocation);
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(pickuplocation.longitude, pickuplocation.latitude), radius);
        geoQuery.removeAllListeners();

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!driverfound) {
                    driverfound = true;
                    driverfoundid = key;

                    DatabaseReference driverref = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverfoundid);
                    String customerid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    HashMap map = new HashMap();
                    map.put("Customer Id", customerid);
                    driverref.updateChildren(map);

                    getdriverlocation();
                    request_cab.setText("Looking for driver location....");
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
                    getclosestdriver();
                }

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void getdriverlocation() {

        DatabaseReference driverlocationref = FirebaseDatabase.getInstance().getReference().child("drivers working").child(driverfoundid).child("l");
        driverlocationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Object> map = (List<Object>) dataSnapshot.getValue();
                    double locationlat = 0;
                    double locationlan = 0;
                    request_cab.setText("driver found");
                    if (map.get(0) != null) {
                        locationlat = Double.parseDouble(map.get(0).toString());

                    }

                    if (map.get(1) != null) {
                        locationlan = Double.parseDouble(map.get(1).toString());

                    }
                    LatLng driverlatlng = new LatLng(locationlat, locationlan);
                    if (mdrivermarker != null) {
                        mdrivermarker.remove();
                    }
                    Location loc1 = new Location("");
                    loc1.setLatitude(pickuplocation.latitude);
                    loc1.setLongitude(pickuplocation.longitude);

                    Location loc2 = new Location("");
                    loc2.setLatitude(driverlatlng.latitude);
                    loc2.setLongitude(driverlatlng.longitude);


                    float distance = loc1.distanceTo(loc2);
                    request_cab.setText("Driver found: " + String.valueOf(distance));

                    mdrivermarker = mMap.addMarker(new MarkerOptions().position(driverlatlng).title("Your Driver"));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
