package com.example.ashish.justgetit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class homepage1 extends AppCompatActivity implements /*PaytmPaymentTransactionCallback,*/ NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {


    Button bt1, locals, outstation;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    public static final int DEFAULT_ZOOM = 10;

/* --------------------------------------------------------------------------------------------------------------------- */

    // Paytm Main Activity
/*
    private void generateCheckSum() {

        //getting the tax amount first.
        String txnAmount = textViewPrice.getText().toString().trim();

        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body().getChecksumHash(), paytm);
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }
        });
    }

    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this, bundle.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
    }
}
*/

/* ------------------------------------------------------------------------------------------------------------------- */

    //Google maps utils

    private Boolean locatonpermissiongranted = false;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    private static final LatLngBounds LAT_LNG_BOUNDS1 = new LatLngBounds(new LatLng(-60, -190), new LatLng(80, 145));
    public FusedLocationProviderClient mfusedlocationproviderclient;
    public GoogleMap mMap;
    // BottomNavigationView bottomNavigationView;
    AutoCompleteTextView search;
    ImageView mgps;
    private PlacesAutocompleteAdapter placesAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient, googleApiClient;
    private PlacesInfo mplace;
    private AutoCompleteTextView drop_location;
    List<car_services_types> services_types;





    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d("homepage1", "Place query did complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);
            try {
                mplace = new PlacesInfo();
                mplace.setName(place.getName().toString());
                mplace.setAddress(place.getAddress().toString());
                mplace.setAttribution(place.getAttributions().toString());
                mplace.setId(place.getId());
                mplace.setLatLng(place.getLatLng());
                mplace.setRating(place.getRating());
                mplace.setPhonenumber(place.getPhoneNumber().toString());
                mplace.setWebsiteuri(place.getWebsiteUri());

            } catch (NullPointerException e) {
                Log.e("homepage1", "onresult: " + e.getMessage());
            }
            moveCamera(new LatLng(place.getViewport().getCenter().latitude, place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mplace.getName());
            MarkerOptions options = new MarkerOptions().position(new LatLng(place.getViewport().getCenter().latitude, place.getViewport().getCenter().longitude));
            mMap.addMarker(options);

            Log.d("homepage1", "onResult: place details: " + mplace.toString());
            places.release();

        }
    };
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
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                Location currentLocation = null;
                getLocationPermission();
            }

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
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

    //Code To Set The Marker And Search The Place Entered in Search Bar

    public void getDeviceLocation() {
        mfusedlocationproviderclient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (locatonpermissiongranted) {
                 Task location = mfusedlocationproviderclient.getLastLocation();
                location.addOnCompleteListener(this,new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location currentLocation = (Location) task.getResult();
                            search.setText("Pinned Location");
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
                        } else {
                            Toast.makeText(homepage1.this, "Unable to Detect Location! \n Make sure your GPS is ON", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        } catch (SecurityException e) {
            Log.d("homepage1", "getDevicelocation: SecurityException:" + e.getMessage());
        }


    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
      /*  MarkerOptions options=new MarkerOptions().position(latLng).title(title);
        mMap.addMarker(options);*/

    }



    private void geolocate() {
        Log.d("homepage1", "geolocate: geolocating");
        String searchstring = search.getText().toString();

        Geocoder geocoder = new Geocoder(homepage1.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchstring, 1);

        } catch (IOException e) {
            Log.e("homepage1", "geolocate: IOException" + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d("homepage1", "geolocate: found a location:" + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
            search.setText(address.getAddressLine(0));
        }

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
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        init();
    }

    /*

   ---------------------------------google places API autocomplete suggestion-----------------------

    */
    private AdapterView.OnItemClickListener mAutocompleteclicklistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = placesAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };


    /*
      -----------------------------code for displaying list of places matching with keyword------------------
      */

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage1);

        //SETTING UP NAVIGATION DRAWER

        drawerLayout = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //   bottomNavigationView = findViewById(R.id.bottom_nav_view);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //click listener for items in bottom_navigation view

     /*   bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
        });*/
        //setting fragment for a map to be shown on layout

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(homepage1.this);

        search = findViewById(R.id.map_search);
        mgps = findViewById(R.id.ic_gps);
        drop_location = findViewById(R.id.drop_location);
        locals = findViewById(R.id.locals);
        outstation = findViewById(R.id.outstation);
        outstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage1.this, outstation.class);
                startActivity(intent);
            }
        });

        /*
        ------------------------Recycler view-------------------------------------
         */
        services_types = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));


        services_types.add(new car_services_types(R.drawable.mini, "Mini"));
        services_types.add(new car_services_types(R.drawable.micro, "Micro"));
        services_types.add(new car_services_types(R.drawable.sedan, "Prime"));
        services_types.add(new car_services_types(R.drawable.suv, "SUV"));
        recyclerView.setAdapter(new programmingadapter(homepage1.this, services_types));



    }

    private void init() {
        Log.d("homepage1", "init: initializing");
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        search.setOnItemClickListener(mAutocompleteclicklistener);
        drop_location.setOnItemClickListener(mAutocompleteclicklistener);

        placesAutocompleteAdapter = new PlacesAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
        search.setAdapter(placesAutocompleteAdapter);
        drop_location.setAdapter(placesAutocompleteAdapter);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute method for searching
                    geolocate();
                }
                return false;
            }
        });
        drop_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute method for searching
                    geolocate();
                }
                return false;
            }
        });
        mgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();

            }
        });
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
