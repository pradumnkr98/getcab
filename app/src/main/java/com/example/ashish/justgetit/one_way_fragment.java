package com.example.ashish.justgetit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class one_way_fragment extends android.support.v4.app.Fragment {
    private static final String TAG = "one_way_fragment";

    Calendar current_date;
    int day, month, year;
    private EditText one_name;
    private EditText f_destination;
    private EditText t_destination;
    private EditText traveller_number;
    // private DatePickerDialog.OnDateSetListener mDateSetListener;

    // calender mCurrentDate;
    //  int day,month,year;
    private EditText date;
    private Button save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_way_fragment, container, false);

        one_name = view.findViewById(R.id.one_name);
        f_destination = view.findViewById(R.id.f_destination);
        t_destination = view.findViewById(R.id.t_destination);
        traveller_number = view.findViewById(R.id.travellers_number);
        save = view.findViewById(R.id.save);
        date = view.findViewById(R.id.date);

        //    current_date=Calendar.getInstance();

        //    day=current_date.get(Calendar.DAY_OF_MONTH);
        //   year=current_date.get(Calendar.YEAR);
        //  month=current_date.get(Calendar.MONTH);

        //  month=month+1;
        //  date.setText(day+"/"+month+"/"+year);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                        monthofyear = monthofyear + 1;
                        date.setText(dayofmonth + "/" + monthofyear + "/" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), homepage1.class);
                startActivity(intent);
            }
        });












        return view;


    }
}
