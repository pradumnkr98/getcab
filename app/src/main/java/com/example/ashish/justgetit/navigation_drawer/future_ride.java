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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class future_ride extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_ride);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bookings Details");
        databaseReference.keepSynced(true);

        /*------------------------------------------- Recycler View --------------------------------------------------------------*/

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCompletedRides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<completed_rides_modelclass> options =
                new FirebaseRecyclerOptions.Builder<completed_rides_modelclass>()
                        .setQuery(databaseReference, completed_rides_modelclass.class)
                        .build();

        FirebaseRecyclerAdapter<completed_rides_modelclass,completed_rides.completed_rides_modelclassViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<completed_rides_modelclass, completed_rides.completed_rides_modelclassViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull completed_rides.completed_rides_modelclassViewHolder holder, final int position, @NonNull completed_rides_modelclass model) {

                holder.setDate(model.getJourneydate());
                holder.setTime(model.getJourneytime());
                holder.setAmount(model.getAmount());
                holder.setVehicle(model.getVehicle());
                holder.setFrom(model.getPickuplocation());
                holder.setTo(model.getDroplocation());
                holder.setPayment(model.getPayment());

                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            @NonNull
            @Override
            public completed_rides.completed_rides_modelclassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout, parent, false);
                return new completed_rides.completed_rides_modelclassViewHolder(view);
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

        public void setDate(String date) {
            Date = itemView.findViewById(R.id.Date);
            Date.setText(date.toString());
        }

        public void setTime(String time) {
            Time = itemView.findViewById(R.id.Time);
            Time.setText(time.toString());
        }

        public void setVehicle(String vehicle) {
            Vehicle = itemView.findViewById(R.id.vehicleModel);
            Vehicle.setText(vehicle.toString());
        }

        public void setFrom(String from) {
            From = itemView.findViewById(R.id.From);
            From.setText(from.toString());
        }

        public void setTo(String to) {
            To = itemView.findViewById(R.id.To);
            To.setText(to.toString());
        }

        public void setPayment(String payment) {
            Payment = itemView.findViewById(R.id.Amount);
            Payment.setText(payment.toString());
        }

        public void setAmount(String amount) {
            Amount = itemView.findViewById(R.id.Amount);
            Amount.setText(amount.toString());
        }
    }
    }
}
