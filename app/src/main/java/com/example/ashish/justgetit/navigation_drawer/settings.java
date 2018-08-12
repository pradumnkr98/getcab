package com.example.ashish.justgetit.navigation_drawer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.homepage1;
import com.example.ashish.justgetit.login_page;
import com.google.firebase.auth.FirebaseAuth;

public class settings extends AppCompatActivity {

    FirebaseAuth mAuth;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Back Button in toolbar
        toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_icon);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, homepage1.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(true);


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
