package com.example.ashish.justgetit.outstation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.justgetit.PlacesAutocompleteAdapter;
import com.example.ashish.justgetit.PlacesInfo;
import com.example.ashish.justgetit.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class outstation_roundway extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button confirm_booking;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    android.support.v7.widget.Toolbar toolbar;
    AutoCompleteTextView search;
    private PlacesAutocompleteAdapter placesAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient, googleApiClient;
    private PlacesInfo mplace;
    SharedPreferences.Editor editor;





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

            Log.d("homepage1", "onResult: place details: " + mplace.toString());
            places.release();

        }
    };
    private AdapterView.OnItemClickListener mAutocompleteclicklistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = placesAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstation_roundway);

        confirm_booking = findViewById(R.id.roundway_next);
        toolbar = findViewById(R.id.summary_toolbar);
        search = findViewById(R.id.pickup_location);

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

        init();

        confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (search.getText().toString().length() == 0) {
                    Toast.makeText(outstation_roundway.this, "Enter Your Pickup Location", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(outstation_roundway.this, roundway_finalbooking.class);
                    String pickup, drop;
                    pickup = search.getText().toString();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(outstation_roundway.this);
                    editor = preferences.edit();
                    editor.putString("pickup", pickup);
                    editor.apply();


                    startActivity(intent);
                }

            }
        });
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

        placesAutocompleteAdapter = new PlacesAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
        search.setAdapter(placesAutocompleteAdapter);
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
       /* Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(pickup_location,drop_location2)
                .build();
        routing.execute();*/
    }

    private void geolocate() {
        Log.d("homepage1", "geolocate: geolocating");
        String searchstring = search.getText().toString();

        Geocoder geocoder = new Geocoder(outstation_roundway.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchstring, 1);

        } catch (IOException e) {
            Log.e("homepage1", "geolocate: IOException" + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d("homepage1", "geolocate: found a location:" + address.toString());
            //  location=new LatLng(address.getLatitude(),address.getLongitude());
            search.setText(address.getAddressLine(0));

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
