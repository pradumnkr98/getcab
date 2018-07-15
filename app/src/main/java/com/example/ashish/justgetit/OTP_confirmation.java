package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OTP_confirmation extends AppCompatActivity {

    private EditText enterOTP;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_confirmation);

        next = findViewById(R.id.next);
        enterOTP = findViewById(R.id.enterOTP);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_c = new Intent(OTP_confirmation.this, create_newpassword.class);
                startActivity(intent_c);
                finish();
            }

        });

     /*   if(enterOTP.getText().toString() != null)
        {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(OTP_confirmation.this,create_newpassword.class);
                    startActivity(intent);
                }

            });
        }

        else
            Toast.makeText(getApplicationContext(),"ENTER OTP TO PROCEED",Toast.LENGTH_SHORT).show();

    }*/


    }
}