package com.example.ashish.justgetit.navigation_drawer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.justgetit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class profile_page extends AppCompatActivity {
    Uri imageuri;
    ImageView user_img;
    private int PICK_IMAGE = 100;
    TextView email1, name1, phone_no1;
    private DatabaseReference mreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        user_img = findViewById(R.id.user_img);
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mreference = FirebaseDatabase.getInstance().getReference().child("Customers").child(userid);
        name1 = findViewById(R.id.my_name);
        email1 = findViewById(R.id.my_email);
        phone_no1 = findViewById(R.id.my_number);

        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    List<Object> details = (List<Object>) dataSnapshot.getValue();
                    if (details.get(0) != null && details.get(1) != null && details.get(2) != null) {
                        String name = details.get(1).toString();
                        Log.e("name", details.get(1) + "");
                        String email = details.get(0).toString();
                        Log.e("email", details.get(0) + "");
                        String phoneno = details.get(2).toString();
                        Log.e("phoneno", details.get(2) + "");

                        email1.setText(email);
                        name1.setText(name);
                        phone_no1.setText(phoneno);
                    } else {
                        Toast.makeText(profile_page.this, "No details Found", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error_oncancelled", "Failed to read value.", error.toException());
            }
        });
    }
}


