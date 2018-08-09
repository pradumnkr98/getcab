package com.example.ashish.justgetit.navigation_drawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ashish.justgetit.R;

public class settings extends AppCompatActivity {

    EditText settingsName, settingsPhone, settingsEmail, settingsPassword;
    ImageView settingsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        settingsName = findViewById(R.id.settingsName);
        settingsEmail = findViewById(R.id.settingsEmail);
        settingsPhone = findViewById(R.id.settingsPhone);
        settingsPassword = findViewById(R.id.settingsPassword);
        settingsImageView = findViewById(R.id.settingsImageView);
    }
}
