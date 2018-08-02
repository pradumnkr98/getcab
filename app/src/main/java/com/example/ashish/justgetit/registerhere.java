package com.example.ashish.justgetit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerhere extends AppCompatActivity {

    private Button signup;
    TextView tv1;
    EditText signup_name, signup_phone_no, signup_email, signup_password;
    ProcessBuilder processBuilder;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    private DatabaseReference mreference;
    private FirebaseAuth mAuth;

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
        firebaseDatabase = FirebaseDatabase.getInstance();
        mreference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(registerhere.this);


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
                final String email, password, phone_no, name;
                email = signup_email.getText().toString();
                name = signup_name.getText().toString();
                phone_no = signup_phone_no.getText().toString();
                password = signup_password.getText().toString();
                progressDialog.setMessage("Registering:Please Wait...");
                progressDialog.show();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(registerhere.this, "Enter Name", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(phone_no) || phone_no.length() < 10 || phone_no.length() > 10) {
                    Toast.makeText(registerhere.this, "Incorrect Phone No.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(registerhere.this, "Enter Your Email Id", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(registerhere.this, "Incorrect Password.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(registerhere.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("register_here", "signInWithEmail:success");
                                        Toast.makeText(registerhere.this, "Registered Successfully..",
                                                Toast.LENGTH_LONG).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        write_user_data(name, phone_no, email);

                                        preferences = PreferenceManager.getDefaultSharedPreferences(registerhere.this);
                                        editor = preferences.edit();
                                        editor.putString("Phone No", phone_no);
                                        editor.putString("Name", name);
                                        editor.commit();

                                        Intent intent = new Intent(registerhere.this, login_page.class);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("register_here", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(registerhere.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        //   updateUI(null);
                                    }

                                    // ...
                                }
                            });

                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void write_user_data(String name, String phone_no, String email_id) {
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        user_registration_details user_registration_details = new user_registration_details(name, phone_no, email_id);
        mreference.child("Customers").child(userId).setValue(user_registration_details);
    }

}
