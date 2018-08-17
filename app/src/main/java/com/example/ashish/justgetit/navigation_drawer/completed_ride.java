package com.example.ashish.justgetit.navigation_drawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.programmingadapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class completed_ride extends AppCompatActivity {

    private DatabaseReference databaseReference;
    List<completed_rides_modelclass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_ride);
        arrayList = new ArrayList<>();

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Bookings Details").child(userid);
        databaseReference.keepSynced(true);

        /*------------------------------------------- Recycler View --------------------------------------------------------------*/


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> ridesdetails = (Map) dataSnapshot.getValue();
                String journeydate, journeytime, fare, pickuplocation, droplocation;
                journeydate = ridesdetails.get("journeydate").toString();
                journeytime = ridesdetails.get("journeytime").toString();
                fare = ridesdetails.get("fare").toString();
                pickuplocation = ridesdetails.get("pickuplocation").toString();
                droplocation = ridesdetails.get("droplocation").toString();

                arrayList.add(new completed_rides_modelclass(journeydate + "", journeytime + "", fare + "", pickuplocation + "", droplocation + ""));
                //arrayList.add(new completed_rides_modelclass("20 03 2018","20:30","40","delhi","mumbai"));
                RecyclerView recyclerView = findViewById(R.id.recyclerViewCompletedRides);
                recyclerView.setLayoutManager(new LinearLayoutManager(completed_ride.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new programmingadapter(completed_ride.this, arrayList));
                Log.e("journeydate", arrayList.get(0).getJourneydate().toString() + "");
                arrayList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
