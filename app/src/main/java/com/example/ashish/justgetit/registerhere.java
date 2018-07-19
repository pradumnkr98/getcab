package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerhere extends AppCompatActivity {

    private Button signup;
    TextView tv1;
    EditText name, phone_no, email, password;
    ProcessBuilder processBuilder;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerhere);

        signup = findViewById(R.id.signup);
        tv1 = findViewById(R.id.login);
        name = findViewById(R.id.signup_name);
        phone_no = findViewById(R.id.phone_no);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        databaseReference = FirebaseDatabase.getInstance().getReference("CustomerRegistration");

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registerhere.this, login_page.class);
                startActivity(intent);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = name.getText().toString().trim();
                String user_phone_no = phone_no.getText().toString().trim();
                String useremail = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();
                if (TextUtils.isEmpty(user_name)) {
                    Toast.makeText(registerhere.this, "Enter your name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(user_phone_no)) {
                    Toast.makeText(registerhere.this, "Enter your phone no.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(useremail)) {
                    Toast.makeText(registerhere.this, "Enter your Email Id", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(user_password)) {
                    Toast.makeText(registerhere.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else {
                    user_registration_details user_registration_details = new user_registration_details(user_name, user_phone_no, useremail, user_password);
                    String id = databaseReference.push().getKey();
                    databaseReference.child(user_phone_no).setValue(user_registration_details);
                    Toast.makeText(registerhere.this, "Registered successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerhere.this, login_page.class);
                    startActivity(intent);

                }



            }
        });
    }
}
