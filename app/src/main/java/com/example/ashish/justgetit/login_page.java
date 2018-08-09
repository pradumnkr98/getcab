package com.example.ashish.justgetit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_page extends AppCompatActivity {
    private Button login;
    FirebaseAuth mAuth;
    private TextView forget_password;
    private TextView register_here;
    ProgressDialog progressDialog;
    private EditText log_password;
    private EditText log_email;

    FirebaseAuth.AuthStateListener firebasestatelistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        log_email = findViewById(R.id.number);
        log_password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        forget_password = findViewById(R.id.forget_password);
        register_here = findViewById(R.id.register_here);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(login_page.this);

        firebasestatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(login_page.this, homepage1.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password;
                email = log_email.getText().toString();
                password = log_password.getText().toString();
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login_page.this, "Enter your email ID", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login_page.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(login_page.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("login_page", "signInWithEmail:success");
                                        Intent intent = new Intent(login_page.this, homepage1.class);
                                        startActivity(intent);
                                        FirebaseUser user = mAuth.getCurrentUser();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("login_page", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(login_page.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });

                }

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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebasestatelistener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebasestatelistener);
    }
}



