package com.example.ashish.justgetit.navigation_drawer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.local_booking.car_services_types;
import com.example.ashish.justgetit.local_booking.final_booking;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class completed_rides extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.completed_rides);
    }


    FirebaseRecyclerOptions<car_services_types> options =
            new FirebaseRecyclerOptions.Builder<car_services_types>()
                    .setQuery(databaseReference, car_services_types.class)
                    .build();

    FirebaseRecyclerAdapter<car_services_types, final_booking.car_services_typesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<car_services_types, final_booking.car_services_typesViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull final_booking.car_services_typesViewHolder holder, final int position, @NonNull car_services_types model) {
            holder.setCar_name(model.getCar_name());
            holder.setFare(model.getFare());
            holder.setCar_image(model.getCar_image());
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @NonNull
        @Override
        public final_booking.car_services_typesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout, parent, false);
            return new final_booking.car_services_typesViewHolder(view);
        }
    };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);



    public static class car_services_typesViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView car_image1;
        TextView car_name1;
        TextView fare1;
        View parent;

        public car_services_typesViewHolder(View itemView) {
            super(itemView);
            parent = this.itemView;

        }

        public void setCar_name(String car_name) {
            car_name1 = itemView.findViewById(R.id.car_name);
            car_name1.setText(car_name.toString());
        }

        public void setFare(String fare) {
            fare1 = itemView.findViewById(R.id.fare);
            fare1.setText(fare.toString());
        }

        public void setCar_image(String car_image) {
            car_image1 = itemView.findViewById(R.id.car_type);
            Picasso.with(itemView.getContext()).load(car_image).resize(80, 50).into(car_image1);
        }

    }
}
