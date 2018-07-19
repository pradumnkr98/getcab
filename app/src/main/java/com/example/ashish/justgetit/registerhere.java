package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerhere extends AppCompatActivity {

    private Button signup;
    TextView tv1;
    EditText signup_name, signup_phone_no, signup_email, signup_password;
    ProcessBuilder processBuilder;
    FirebaseFirestore db;
    DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerhere);

        signup = findViewById(R.id.signup);
        tv1 = findViewById(R.id.login);
        signup_name = findViewById(R.id.signup_name);
        signup_phone_no = findViewById(R.id.signup_phone_no);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);

        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document();


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

                String name = signup_name.getText().toString().trim();
                String number = signup_phone_no.getText().toString().trim();
                String email = signup_email.getText().toString().trim();
                String password = signup_password.getText().toString().trim();

                Map user = new HashMap();
                user.put("Name", name);
                user.put("Email", email);
                user.put("Number", number);
                user.put("Password", password);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(registerhere.this, "error: " + e, Toast.LENGTH_SHORT).show();

                    }
                });
                Intent intent = new Intent(registerhere.this, login_page.class);
                startActivity(intent);


            }
        });
    }
}
