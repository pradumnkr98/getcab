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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class future_ride extends AppCompatActivity {

    FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_ride);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userid = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bookings Details");
        databaseReference.keepSynced(true);

        /*------------------------------------------- Recycler View --------------------------------------------------------------*/

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCompletedRides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<completed_rides_modelclass> options =
                new FirebaseRecyclerOptions.Builder<completed_rides_modelclass>()
                        .setQuery(databaseReference, completed_rides_modelclass.class)
                        .build();

        FirebaseRecyclerAdapter<completed_rides_modelclass, completed_rides_modelclassViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<completed_rides_modelclass, completed_rides_modelclassViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull completed_rides_modelclassViewHolder holder, final int position, @NonNull completed_rides_modelclass model) {

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
            public completed_rides_modelclassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_rides_cards, parent, false);
                return new completed_rides_modelclassViewHolder(view);
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

