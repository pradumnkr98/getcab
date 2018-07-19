package com.example.ashish.justgetit;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class outstation extends AppCompatActivity {

    private SectionPageAdapter mSectionpageAdapter;
    private ViewPager viewPager;
    Calendar current_date;
    int day, month, year;
    private EditText date;
    FirebaseFirestore db;
    private Button confirmBooking;
    private EditText one_name, one_number, t_destination, f_destination, travellers_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstation);

        confirmBooking = findViewById(R.id.save);
        one_name = findViewById(R.id.one_name);
        one_number = findViewById(R.id.one_number);
        f_destination = findViewById(R.id.f_destination);
        t_destination = findViewById(R.id.t_destination);
        travellers_number = findViewById(R.id.travellers_number);

        db = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date = findViewById(R.id.date);
        //viewPager = findViewById(R.id.container);
        // setupViewPager(viewPager);
        // TabLayout tabLayout = findViewById(R.id.tabs);
        //  tabLayout.setupWithViewPager(viewPager);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(outstation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                        monthofyear = monthofyear + 1;
                        date.setText(dayofmonth + "/" + monthofyear + "/" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        confirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = one_name.getText().toString().trim();
                String number = one_number.getText().toString().trim();
                String from = f_destination.getText().toString().trim();
                String to = t_destination.getText().toString().trim();
                String number_travellers = travellers_number.getText().toString().trim();
                String travel_date = date.getText().toString().trim();

                CollectionReference dbOustationDetails = db.collection("OutstationDetails");

                OutstationDetails outstationDetails = new OutstationDetails(
                        name,
                        number,
                        from,
                        to,
                        Integer.parseInt(number_travellers),
                        travel_date
                );

                dbOustationDetails.add(outstationDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(outstation.this,"Saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(outstation.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

  /*  private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addfragment(new one_way_fragment(), "One Way");
        adapter.addfragment(new round_way_fragment(), "ROund way");
        viewPager.setAdapter(adapter);

    }*/
}


