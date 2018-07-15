package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class create_newpassword extends AppCompatActivity {
    private Button login_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_newpassword);

        login_r = findViewById(R.id.login_r);

        login_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_r = new Intent(create_newpassword.this, homepage1.class);
                startActivity(intent_r);
                finish();
            }
        });


    }
}
