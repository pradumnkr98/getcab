package com.example.ashish.justgetit;

import android.app.ProgressDialog;
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
    private EditText signup_name;
    private EditText signup_email;
    private EditText signup_password;
    TextView tv1;
    private ProgressDialog progressDialog;

    //private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerhere);

        signup = findViewById(R.id.signup);
        tv1 = findViewById(R.id.login);
        signup_name = findViewById(R.id.signup_name);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);

        db = FirebaseFirestore.getInstance();

        //  progressDialog=new ProgressDialog(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signup_name.getText().toString().trim();
                String email = signup_email.getText().toString().trim();
                String password = signup_password.getText().toString().trim();

                Map<String, String> userMap = new HashMap<>();
                userMap.put("Username", name);
                userMap.put("Email", email);
                userMap.put("Password", password);


                db.collection("Registered Data").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "username added to firestore ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(registerhere.this, "error" + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerhere.this, login_page.class);
                startActivity(intent);
            }
        });


    }


}
