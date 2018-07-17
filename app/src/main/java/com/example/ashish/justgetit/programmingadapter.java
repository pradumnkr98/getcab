package com.example.ashish.justgetit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class programmingadapter extends RecyclerView.Adapter<programmingadapter.ProgrammingViewHolder> {
    public List<car_services_types> data;
    Context context;

    public programmingadapter(Context context, List<car_services_types> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public programmingadapter.ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerviewlayout, parent, false);

        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull programmingadapter.ProgrammingViewHolder holder, final int position) {
        car_services_types services_types = data.get(position);
        holder.car_type.setText(services_types.getCar_type());
        holder.image.setImageDrawable(context.getResources().getDrawable(services_types.getCarimage()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        break;
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView car_type;
        View parent;
        LinearLayout linearLayout;

        public ProgrammingViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.car_type);
            car_type = itemView.findViewById(R.id.car_name);
            parent = this.itemView;

        }
    }
}
