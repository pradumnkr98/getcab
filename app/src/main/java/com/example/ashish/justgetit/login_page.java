package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login_page extends AppCompatActivity {
    private Button login;
    private EditText number;
    private EditText password;
    private TextView forget_password;
    private TextView register_here;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        number = findViewById(R.id.number);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        forget_password = findViewById(R.id.forget_password);
        register_here = findViewById(R.id.register_here);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this, homepage1.class);
                startActivity(intent);
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(login_page.this, forgetpassword.class);
                startActivity(intent1);
            }
        });


        register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(login_page.this, registerhere.class);
                startActivity(intent2);
            }
        });


    }

}
