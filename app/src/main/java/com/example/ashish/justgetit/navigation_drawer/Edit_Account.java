package com.example.ashish.justgetit.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ashish.justgetit.R;

public class Edit_Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__account);

        Button save = (Button)findViewById(R.id.saveEditAccount);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_Account.this,settings.class);
                startActivity(intent);
            }
        });
    }
}
