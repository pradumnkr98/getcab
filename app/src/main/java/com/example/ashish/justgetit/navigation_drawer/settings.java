package com.example.ashish.justgetit.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.login_page;
import com.google.firebase.auth.FirebaseAuth;

public class settings extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        RelativeLayout relativeLayout = findViewById(R.id.accouontSettingsLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, Edit_Account.class);
                startActivity(intent);
            }
        });

        TextView editAccount = findViewById(R.id.editAccountTextView);
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, Edit_Account.class);
                startActivity(intent);
            }
        });
    }
}
