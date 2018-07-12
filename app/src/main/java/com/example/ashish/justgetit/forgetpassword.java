package com.example.ashish.justgetit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class forgetpassword extends AppCompatActivity {

    private EditText entered_number;
    private Button OTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        entered_number = findViewById(R.id.your_name);
        OTP = findViewById(R.id.OTP);

        OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "requesting OTP", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "sending OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
