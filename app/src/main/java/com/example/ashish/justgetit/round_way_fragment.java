package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class round_way_fragment extends android.support.v4.app.Fragment {


    private EditText round_name;
    private EditText roundf_destination;
    private EditText roundt_destination;
    private EditText round_traveller_number;
    private EditText roundf_date;
    private EditText roundt_date;
    private Button round_save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.round_way_fragment, container, false);

        round_name = view.findViewById(R.id.round_name);
        roundf_destination = view.findViewById(R.id.roundf_destination);
        roundt_destination = view.findViewById(R.id.roundt_destination);
        round_traveller_number = view.findViewById(R.id.round_travellers_number);
        roundt_date = view.findViewById(R.id.roundt_date);
        roundf_date = view.findViewById(R.id.roundf_date);
        round_save = view.findViewById(R.id.round_save);





        round_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), homepage1.class);
                startActivity(intent);
            }
        });



        return view;


    }
}
