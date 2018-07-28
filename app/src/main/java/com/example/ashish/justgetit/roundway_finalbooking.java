package com.example.ashish.justgetit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

public class roundway_finalbooking extends AppCompatActivity {
    private static final int Time_id = 1;
    List<car_services_types> services_types;
    android.support.v7.widget.Toolbar toolbar;
    Calendar current_date;
    int day, month, year;
    EditText Schedule_ride, time_pick;
    Button next;
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
        setContentView(R.layout.activity_final_booking);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Schedule_ride = findViewById(R.id.schedule_ride);
        time_pick = findViewById(R.id.time_pick);
        next = findViewById(R.id.confirm_booking);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(roundway_finalbooking.this, booking_summary.class);
                startActivity(intent);
            }
        });


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

}
