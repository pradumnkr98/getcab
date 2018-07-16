package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class forgetpassword extends AppCompatActivity {

    private EditText enterednumber;
    private Button OTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        enterednumber = findViewById(R.id.enterednumber);
        OTP = findViewById(R.id.OTP);
        //  enterednumber.getText().toString();

        OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "requesting OTP", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "sending OTP", Toast.LENGTH_SHORT).show();
                Intent intentfp = new Intent(forgetpassword.this, OTP_confirmation.class);
                startActivity(intentfp);
                finish();
            }
        });
    }
}
