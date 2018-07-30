package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.oneway_finalbooking;

public class one_way_layout extends AppCompatActivity {

    private Button oneWayNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way_layout);

        oneWayNextButton = findViewById(R.id.oneWayNextButton);

        oneWayNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(one_way_layout.this, oneway_finalbooking.class);
                startActivity(intent);

            }
        });
    }
}
