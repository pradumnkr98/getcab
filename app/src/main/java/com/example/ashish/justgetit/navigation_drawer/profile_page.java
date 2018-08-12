package com.example.ashish.justgetit.navigation_drawer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashish.justgetit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        mreference = FirebaseDatabase.getInstance().getReference().child("Customers");
        name1 = findViewById(R.id.my_name);
        email1 = findViewById(R.id.my_email);
        phone_no1 = findViewById(R.id.my_number);
    }
}



       /* mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String email = dataSnapshot.getValue(String.class);
                String name = dataSnapshot.getValue(String.class);
                String phone_no = dataSnapshot.getValue(String.class);
                //Log.d(, "Value is: " + value);
                email1.setText(email);
                name1.setText(name);
                phone_no1.setText(phone_no);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error_oncancelled", "Failed to read value.", error.toException());
            }
        });*/



