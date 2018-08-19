package com.example.ashish.justgetit.navigation_drawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashish.justgetit.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class completed_ride extends AppCompatActivity {

    private DatabaseReference databaseReference;
    List<completed_rides_modelclass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_ride);
        arrayList = new ArrayList<>();

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer Booking").child(userid).child("Booking Details");
        databaseReference.keepSynced(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCompletedRides);
        recyclerView.setLayoutManager(new LinearLayoutManager(completed_ride.this, LinearLayoutManager.VERTICAL, false));

        /*------------------------------------------- Recycler View --------------------------------------------------------------*/


       /* databaseReference.addValueEventListener(new ValueEventListener() {
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
        });*/
        FirebaseRecyclerOptions<completed_rides_modelclass> options =
                new FirebaseRecyclerOptions.Builder<completed_rides_modelclass>()
                        .setQuery(databaseReference, completed_rides_modelclass.class)
                        .build();

        FirebaseRecyclerAdapter<completed_rides_modelclass, future_ride.completed_rides_modelclassViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<completed_rides_modelclass, future_ride.completed_rides_modelclassViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull future_ride.completed_rides_modelclassViewHolder holder, final int position, @NonNull completed_rides_modelclass model) {

                // to get details of a particular customer only
                // get a if condition to get info for future bookings
                {
                    holder.setJourneydate(model.getJourneydate());
                    holder.setJourneytime(model.getJourneytime());
                    holder.setFare(model.getFare());
                    holder.setPickuplocation(model.getPickuplocation());
                    holder.setDroplocation(model.getDroplocation());

                    holder.parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }

            @NonNull
            @Override
            public future_ride.completed_rides_modelclassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_rides_cards, parent, false);
                return new future_ride.completed_rides_modelclassViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class completed_rides_modelclassViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView Date, Time, Amount, Vehicle, From, To, Payment;
        View parent;

        public completed_rides_modelclassViewHolder(View itemView) {
            super(itemView);
            parent = this.itemView;

        }

        public void setJourneydate(String journeydate) {
            Date = itemView.findViewById(R.id.Date);
            Date.setText(journeydate.toString());
        }

        public void setJourneytime(String journeytime) {
            Time = itemView.findViewById(R.id.Time);
            Time.setText(journeytime.toString());
        }


        public void setPickuplocation(String pickuplocation) {
            From = itemView.findViewById(R.id.From);
            From.setText(pickuplocation.toString());
        }

        public void setDroplocation(String droplocation) {
            To = itemView.findViewById(R.id.To);
            To.setText(droplocation.toString());
        }


        public void setFare(String fare) {
            Amount = itemView.findViewById(R.id.Amount);
            Amount.setText("Rs." + fare);
        }
    }
}
