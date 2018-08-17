package com.example.ashish.justgetit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashish.justgetit.navigation_drawer.completed_rides_modelclass;

import java.util.List;

public class programmingadapter extends RecyclerView.Adapter<programmingadapter.ProgrammingViewHolder> {
    public List<completed_rides_modelclass> data;
    Context context;

    public programmingadapter(Context context, List<completed_rides_modelclass> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public programmingadapter.ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.completed_rides_cards, parent, false);

        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull programmingadapter.ProgrammingViewHolder holder, final int position) {
        completed_rides_modelclass services_types = data.get(position);
        holder.pickup_date.setText(services_types.getJourneydate());
        holder.pickup_time.setText(services_types.getJourneytime());
        holder.fare1.setText(services_types.getFare());
        holder.pickup_location.setText(services_types.getPickuplocation());
        holder.drop_location.setText(services_types.getDroplocation());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder {
        TextView pickup_date, pickup_time, fare1, car_name, pickup_location, drop_location;

        public ProgrammingViewHolder(View itemView) {
            super(itemView);
            pickup_date = itemView.findViewById(R.id.Date);
            pickup_time = itemView.findViewById(R.id.Time);
            fare1 = itemView.findViewById(R.id.Amount);
            //car_name=itemView.findViewById(R.id.VehicleNumber);
            pickup_location = itemView.findViewById(R.id.From);
            drop_location = itemView.findViewById(R.id.To);

        }
    }
}

