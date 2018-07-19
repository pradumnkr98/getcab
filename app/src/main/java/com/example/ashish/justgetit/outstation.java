package com.example.ashish.justgetit;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class outstation extends AppCompatActivity {

    private SectionPageAdapter mSectionpageAdapter;
    private ViewPager viewPager;
    Calendar current_date;
    int day, month, year;
    private EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstation);

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

    }

  /*  private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addfragment(new one_way_fragment(), "One Way");
        adapter.addfragment(new round_way_fragment(), "ROund way");
        viewPager.setAdapter(adapter);

    }*/
}


