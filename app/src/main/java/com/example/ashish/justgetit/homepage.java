package com.example.ashish.justgetit;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class homepage extends AppCompatActivity {

    Button bt1;
    private static final String TAG = "homepage";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        bt1 = findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage.this, login_page.class);
                startActivity(intent);
            }
        });

        //Checking is google services working properly or not (is service ok is a method defined below

        if (isServicesOk()) {
            Toast.makeText(this, "google working", Toast.LENGTH_LONG).show();
        }
    }

   /*public void init() {
        bt1 = findViewById(R.id.btn_chk);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage.this, login_page.class);
                startActivity(intent);
            }
        });

    }*/

    public boolean isServicesOk() {
        Log.d(TAG, "isServicesOk :checking google version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOk:google play services is working");
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOk: an error occured which can be resolved");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(homepage.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(homepage.this, "we can't make map reader", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
