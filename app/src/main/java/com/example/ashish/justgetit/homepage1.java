package com.example.ashish.justgetit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.ashish.justgetit.local_booking.getting_nearby_driver;
import com.example.ashish.justgetit.navigation_drawer.completed_ride;
import com.example.ashish.justgetit.navigation_drawer.future_ride;
import com.example.ashish.justgetit.navigation_drawer.profile_page;
import com.example.ashish.justgetit.navigation_drawer.settings;
import com.example.ashish.justgetit.navigation_drawer.wallet;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class homepage1 extends AppCompatActivity implements /*PaytmPaymentTransactionCallback,*/ NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {
    Button bt1, locals, outstation, one_way, clickhere;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark};


    //Google maps utils

    public static final int DEFAULT_ZOOM = 100;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    private static final LatLngBounds LAT_LNG_BOUNDS1 = new LatLngBounds(new LatLng(-60, -190), new LatLng(80, 145));
    public FusedLocationProviderClient mfusedlocationproviderclient;
    public GoogleMap mMap;

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

    DatabaseReference reference1;

    String str_from, end_to;
    TextView email1;

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
                SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
                double latitude = Double.parseDouble(preference.getString("driverlat", "0"));
                if (latitude == 0) {
                    Toast.makeText(this, "No Ride Found", Toast.LENGTH_LONG).show();
                    Log.e("driverlocation", latitude + "");
                } else {
                    Intent intent = new Intent(homepage1.this, getting_nearby_driver.class);
                    startActivity(intent);
                }
                return true;

            case R.id.future_ride:
                Intent intent2 = new Intent(homepage1.this, future_ride.class);
                startActivity(intent2);
                return true;

            case R.id.wallet:
                Intent intent4 = new Intent(homepage1.this, wallet.class);
                startActivity(intent4);
                return true;

            case R.id.completed_ride:
                Intent intent5 = new Intent(homepage1.this, completed_ride.class);
                startActivity(intent5);
                return true;

            case R.id.settings:
                Intent intent6 = new Intent(homepage1.this, settings.class);
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

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        LatLng pickup, drop;

        // str_from=search.getText().toString();
        // end_to=drop_location.getText().toString();

        Log.e("pickup", str_from + "");
        Log.e("drop", end_to + "");

        Toast.makeText(this, str_from + end_to, Toast.LENGTH_SHORT).show();

        //turn on the my location layer and the related control on the map
        updateLocationUI();
        if (locatonpermissiongranted) {
            getDeviceLocation();
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        init();



       /* pickup=getLocationFromAddress(this,str_from);
        drop=getLocationFromAddress(this,end_to);
        Log.e("pickup",pickup+"");
        Log.e("drop",drop+"");
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(pickup, drop)
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

        polylines = new ArrayList<>();

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
                    //Intent intent = new Intent(homepage1.this, final_booking.class);
                    String pickup, drop;
                    pickup = search.getText().toString();
                    drop = drop_location.getText().toString();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(homepage1.this);
                    editor = preferences.edit();
                    editor.putString("pickup", pickup);
                    editor.putString("drop", drop);
                    editor.commit();
                    editor.apply();
                    LatLng pickup1, drop1;
                    str_from = search.getText().toString();
                    end_to = drop_location.getText().toString();
                    pickup1 = getLocationFromAddress(homepage1.this, str_from);
                    drop1 = getLocationFromAddress(homepage1.this, end_to);
                    Routing routing = new Routing.Builder()
                            .travelMode(AbstractRouting.TravelMode.DRIVING)
                            .withListener(homepage1.this)
                            .alternativeRoutes(false)
                            .waypoints(pickup1, drop1)
                            .build();
                    routing.execute();

                    //startActivity(intent);
                }
            }
        });

        str_from=search.getText().toString();
        end_to=drop_location.getText().toString();

        Log.e("pickup", str_from + "");
        Log.e("drop", end_to + "");


        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference1 = FirebaseDatabase.getInstance().getReference().child("Customers").child(userid);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, String> getemail = (Map) dataSnapshot.getValue();
                    String email = getemail.get("email");
                    TextView username = findViewById(R.id.usernameDrawer);
                    Log.e("username", email + "");
//                    username.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
      /*  SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        String imagepath=preferences.getString("picture_path","");
        ImageView imageView=findViewById(R.id.user_img);
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagepath));*/


       /* String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + end_to + "&mode=driving&language=fr-FR&avoid=tolls&key=YOUR_API_KEY";
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

            Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + (route.get(i).getDistanceValue()) / 1000 + ": duration - " + (route.get(i).getDurationValue()) / 3600 + " Hr", Toast.LENGTH_SHORT).show();
            /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(homepage1.this);
            editor = preferences.edit();
            editor.putString("distance", route.get(0).getDistanceValue() + "");
            editor.commit();
            editor.apply();*/
            Intent intent = new Intent(homepage1.this, final_booking.class);
            intent.putExtra("distance", route.get(0).getDistanceValue() + "");
            Log.i("Value", String.valueOf(route.get(0).getDistanceValue()));
            startActivity(intent);
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
