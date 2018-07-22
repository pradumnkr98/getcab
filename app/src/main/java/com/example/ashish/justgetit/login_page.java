package com.example.ashish.justgetit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class login_page extends AppCompatActivity {
    private Button login;
    FirebaseFirestore db;
    DocumentReference documentReference;
    private TextView forget_password;
    private TextView register_here;
    private EditText log_number;
    private EditText log_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        log_number = findViewById(R.id.number);
        log_password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        forget_password = findViewById(R.id.forget_password);
        register_here = findViewById(R.id.register_here);

        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document();

        // log_number.getText().toString().trim();
        //  log_password.getText().toString().trim();

        db = FirebaseFirestore.getInstance();

        if (sharedpreferences.getUserName(login_page.this).length() == 0) {

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentsSnapshots, FirebaseFirestoreException e) {
                            for (DocumentSnapshot ds : documentsSnapshots) {

                                String number, password;
                                number = ds.getString("Number");
                                password = ds.getString("Password");
                                if (number.contentEquals(log_number.getText().toString().trim()) && password.contentEquals(log_password.getText().toString().trim())) {
                                    Intent intent = new Intent(login_page.this, homepage1.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(login_page.this, "invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    sharedpreferences.setUserName(login_page.this, log_number.toString());
                }

            });

        } else {
            Intent intent = new Intent(login_page.this, homepage1.class);
            startActivity(intent);

        }


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
