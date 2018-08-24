package com.example.ashish.justgetit.outstation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.local_booking.car_services_types;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class roundway_finalbooking extends AppCompatActivity {
    private static final int Time_id = 1;
    List<car_services_types> services_types;
    android.support.v7.widget.Toolbar toolbar;
    Calendar current_date;
    int day, month, year;

    String pick_date, pick_time;
    String pick_up_location;
    SharedPreferences.Editor editor;
    Button next1;

    EditText Schedule_ride, time_pick;
    private DatabaseReference databaseReference;
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            time_pick.setText(time1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundway_finalbooking);

        DateFormat df = new SimpleDateFormat("HH:mm a");
        String time = df.format(Calendar.getInstance().getTime());
        DateFormat df1 = new SimpleDateFormat("d MM yyyy");
        String date = df1.format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("outstation_vehicles").child("round_way");
        databaseReference.keepSynced(true);

        toolbar = findViewById(R.id.roundwaytoolbar1);
        setSupportActionBar(toolbar);

        Schedule_ride = findViewById(R.id.roundway_schedule_ride);
        Schedule_ride.setText(date);
        time_pick = findViewById(R.id.roundway_time_pick);
        time_pick.setText(time);


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(roundway_finalbooking.this);
        pick_up_location = pref.getString("pickup", "");


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
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        /*
        ------------------------Recycler view-------------------------------------
         */
        // services_types = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));


      /*  services_types.add(new car_services_types(R.drawable.imini, "Mini"));
        services_types.add(new car_services_types(R.drawable.imicro, "Sedan"));
        services_types.add(new car_services_types(R.drawable.isuv, "SUV"));
        services_types.add(new car_services_types(R.drawable.innova, "SUV(Innova)"));
        recyclerView.setAdapter(new programmingadapter(roundway_finalbooking.this, services_types));*/

        Schedule_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(roundway_finalbooking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                        monthofyear = monthofyear + 1;
                        Schedule_ride.setText(dayofmonth + "/" + monthofyear + "/" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        time_pick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });

        FirebaseRecyclerOptions<car_services_types> options =
                new FirebaseRecyclerOptions.Builder<car_services_types>()
                        .setQuery(databaseReference, car_services_types.class)
                        .build();

        FirebaseRecyclerAdapter<car_services_types, car_services_typesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<car_services_types, car_services_typesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final car_services_typesViewHolder holder, final int position, @NonNull final car_services_types model) {

                holder.setCar_name(model.getCar_name());
                holder.setFare(model.getFare());
                holder.setCar_image(model.getCar_image());
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(roundway_finalbooking.this, roundway_bookingsummary.class);
                        if (model.getCar_name().equals("SUV ertiga")) {
                            intent.putExtra("fare", (model.getFare() + 900) + "");
                        } else if (model.getCar_name().equals("SUV innova")) {
                            intent.putExtra("fare", (model.getFare() + 1000) + "");
                        } else
                            intent.putExtra("fare", (model.getFare()) + "");
                        Log.e("fare3", model.getFare() + "");
                        intent.putExtra("carname", model.getCar_name() + "");
                        intent.putExtra("carimage", model.getCar_image() + "");
                        pick_date = Schedule_ride.getText().toString();
                        pick_time = time_pick.getText().toString();
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(roundway_finalbooking.this);
                        editor = preferences.edit();
                        editor.putString("pickdate", pick_date);
                        editor.putString("picktime", pick_time);
                        editor.commit();
                        startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public car_services_typesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout, parent, false);
                return new car_services_typesViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Open the timepicker dialog
        return new TimePickerDialog(roundway_finalbooking.this, time_listener, hour,
                minute, true);
    }


    public static class car_services_typesViewHolder extends RecyclerView.ViewHolder {
        public View parent;
        View view;
        ImageView car_image1;
        TextView car_name1;
        TextView fare1;


        public car_services_typesViewHolder(View itemView) {
            super(itemView);
            parent = this.itemView;


        }


        public void setCar_name(String car_name) {
            car_name1 = itemView.findViewById(R.id.car_name);
            car_name1.setText(car_name.toString());
        }

        public void setFare(Long fare) {
            fare1 = itemView.findViewById(R.id.fare);
            fare1.setText(fare.toString());
        }

        public void setCar_image(String car_image) {
            car_image1 = itemView.findViewById(R.id.car_type);
            Picasso.with(itemView.getContext()).load(car_image).resize(80, 50).into(car_image1);
        }

    }

}
