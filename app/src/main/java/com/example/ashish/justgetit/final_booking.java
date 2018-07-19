package com.example.ashish.justgetit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class final_booking extends AppCompatActivity {
    List<car_services_types> services_types;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*
        ------------------------Recycler view-------------------------------------
         */
        services_types = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));


        services_types.add(new car_services_types(R.drawable.imini, "Mini"));
        services_types.add(new car_services_types(R.drawable.imicro, "Micro"));
        services_types.add(new car_services_types(R.drawable.isuv, "SUV"));
        services_types.add(new car_services_types(R.drawable.innova, "Innova"));
        recyclerView.setAdapter(new programmingadapter(final_booking.this, services_types));
    }
}
