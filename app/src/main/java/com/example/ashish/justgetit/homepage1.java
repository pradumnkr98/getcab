package com.example.ashish.justgetit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.ashish.justgetit.local_booking.final_booking;
import com.example.ashish.justgetit.navigation_drawer.account_details;
import com.example.ashish.justgetit.navigation_drawer.available_booking;
import com.example.ashish.justgetit.navigation_drawer.current_duty;
import com.example.ashish.justgetit.navigation_drawer.driver_incentives;
import com.example.ashish.justgetit.navigation_drawer.profile_page;
import com.example.ashish.justgetit.navigation_drawer.recharge;
import com.example.ashish.justgetit.outstation_one_way.outstation_one_way;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class homepage1 extends AppCompatActivity implements /*PaytmPaymentTransactionCallback,*/ NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {
    Button bt1, locals, outstation, one_way;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark};


    //Google maps utils

    public static final int DEFAULT_ZOOM = 100;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    private static final LatLngBounds LAT_LNG_BOUNDS1 = new LatLngBounds(new LatLng(-60, -190), new LatLng(80, 145));
    public FusedLocationProviderClient mfusedlocationproviderclient;
    public GoogleMap mMap;
    LatLng pickup_location, drop_location2;
    HashMap<String, Double> pickupMap, dropMap;
    List<Double> list1;
    // BottomNavigationView bottomNavigationView;
    AutoCompleteTextView search;
    ImageView mgps;
    FirebaseAuth mAuth;
    private Boolean locatonpermissiongranted = false;
    private PlacesAutocompleteAdapter placesAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient, googleApiClient;
    private PlacesInfo mplace;
    private AutoCompleteTextView drop_location;
    private Button confirm_booking;
    private TextView total_distance, total_time;
    private List<Polyline> polylines;
    SharedPreferences.Editor editor;



    /* --------------------------------------------------------------------------------------------------------------------- */

    // Paytm Main Activity
/*
    The following code is commented until we get merchant details and payment layout.

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
    /*
      -----------------------------code for displaying list of places matching with keyword------------------
      */

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
            case R.id.current_ride:
                Intent intent1 = new Intent(homepage1.this, current_duty.class);
                startActivity(intent1);
                return true;

            case R.id.future_ride:
                Intent intent2 = new Intent(homepage1.this, available_booking.class);
                startActivity(intent2);
                return true;

            case R.id.wallet:
                Intent intent4 = new Intent(homepage1.this, recharge.class);
                startActivity(intent4);
                return true;

            case R.id.completed_ride:
                Intent intent5 = new Intent(homepage1.this, account_details.class);
                startActivity(intent5);
                return true;

            case R.id.settings:
                Intent intent6 = new Intent(homepage1.this, driver_incentives.class);
                startActivity(intent6);
                return true;

            case R.id.profile:
                Intent intent7 = new Intent(homepage1.this, profile_page.class);
                startActivity(intent7);
                return true;
            case R.id.log_out:
                mAuth.signOut();
                Intent intent8 = new Intent(homepage1.this, login_page.class);
                Toast.makeText(homepage1.this, "logout successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent8);


            default:
                return false;
        }

        //  return false;
    }


    //THE CODE FOR GOOGLE MAPS

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
                            search.setHint("Pinned Location");
                            // pickup_location=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
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
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(cameraUpdate);

    }


    private void geolocate() {
        //Log.d("homepage1", "geolocate: geolocating");
        String searchstring = search.getText().toString();

        Geocoder geocoder = new Geocoder(homepage1.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchstring, 1);

        } catch (IOException e) {
            //Log.e("homepage1", "geolocate: IOException" + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            //Log.d("homepage1", "geolocate: found a location:" + address.toString());
            //  location=new LatLng(address.getLatitude(),address.getLongitude());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
            search.setText(address.getAddressLine(0));
            list1.add(address.getLatitude());
            list1.add(address.getLongitude());
            Log.e("List1", list1.get(0) + "");
            Log.i("List2", list1.get(1) + "");

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        pickupMap = new HashMap<>();
        dropMap = new HashMap<>();
        //turn on the my location layer and the related control on the map
        updateLocationUI();
        if (locatonpermissiongranted) {
            getDeviceLocation();
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        init();

        Log.e("list1", list1.size() + "");

        Log.i("Value", String.valueOf(pickupMap.get("latPick")));

        if (list1.size() != 0) {
            Log.i("latitude", String.valueOf(list1.get(0)));
            Log.i("longitude", String.valueOf(list1.get(1)));
        }
        /*LatLng latLng = new LatLng(pickupMap.get("latPick"),pickupMap.get("lngPick"));
        LatLng latLng1 = new LatLng(dropMap.get("latDrop"),dropMap.get("lngDrop"));

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(latLng, latLng1)
                .build();
        routing.execute();*/

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


    //ON CREATE METHOD

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage1);
        mAuth = FirebaseAuth.getInstance();

//        pickupMap=new HashMap<>();
//        dropMap=new HashMap<>();


        list1 = new ArrayList<Double>();

        one_way = findViewById(R.id.oneWay);
        one_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage1.this, outstation_one_way.class);
                startActivity(intent);
            }
        });


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
        confirm_booking = findViewById(R.id.confirm_booking);
        // polylines = new ArrayList<>();

        //setting fragment for a map to be shown on layout

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(homepage1.this);

        search = findViewById(R.id.map_search);
        mgps = findViewById(R.id.ic_gps);
        drop_location = findViewById(R.id.drop_location);
        locals = findViewById(R.id.locals);
        Button oneWay = findViewById(R.id.oneWay);
        oneWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage1.this, outstation_one_way.class);
                startActivity(intent);
            }
        });

        Button roundway = findViewById(R.id.round_way);
        roundway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage1.this, com.example.ashish.justgetit.outstation.outstation_roundway.class);
                startActivity(intent);
            }
        });

        confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search.getText().toString().length() == 0) {
                    Toast.makeText(homepage1.this, "Enter Your Pickup Location", Toast.LENGTH_LONG).show();
                } else if (drop_location.getText().toString().length() == 0) {
                    Toast.makeText(homepage1.this, "Enter Drop Location", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(homepage1.this, final_booking.class);
                    String pickup, drop;
                    pickup = search.getText().toString();
                    drop = drop_location.getText().toString();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(homepage1.this);
                    editor = preferences.edit();
                    editor.putString("pickup", pickup);
                    editor.putString("drop", drop);
                    editor.apply();
                    startActivity(intent);
                }
            }
        });



       /* String str_from,end_to;
        str_from=search.getText().toString();
        end_to=drop_location.getText().toString();
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + end_to + "&mode=driving&language=fr-FR&avoid=tolls&key=YOUR_API_KEY";
        new GeoTask(homepage1.this).execute(url);*/


      /* String pickup_location1;
       pickup_location1=search.getText().toString();
       Intent intent=new Intent(homepage1.this,customer_map.class);
       intent.putExtra("pickup",pickup_location1);
       startActivity(intent);*/



    }
   /* @Override
    public void setDouble(String result) {
        String res[]=result.split(",");
        Double min=Double.parseDouble(res[0])/60;
        int dist=Integer.parseInt(res[1])/1000;
        total_distance.setText("Duration= " + (int) (min / 60) + " hr " + (int) (min % 60) + " mins");
        total_time.setText("Distance= " + dist + " kilometers");


    }*/


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
              /*  Intent intent=new Intent(homepage1.this,customer_map.class);
                intent.putExtra( "pickup_location_latitude",pickup_location.latitude);
                intent.putExtra("pick_up_location_longitude",pickup_location.longitude);
                startActivity(intent);*/
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


    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int starting_index) {
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingCancelled() {

    }
    public void erasepolylines(){
        for (Polyline line:polylines){
            line.remove();
        }
        polylines.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list1.size() != 0) {
            LatLng latLng = new LatLng(list1.get(0), list1.get(1));
            LatLng latLng1 = new LatLng(list1.get(2), list1.get(2));

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(latLng, latLng1)
                    .build();
            routing.execute();
        }

    }
}
