package com.example.ashish.justgetit.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ashish.justgetit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class settings extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    TextView name, email, phone_no;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        name = findViewById(R.id.customerName);
        email = findViewById(R.id.Email);
        phone_no = findViewById(R.id.Mobile);


        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Customers").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, String> details = (Map) dataSnapshot.getValue();
                    name.setText(details.get("name"));
                    Log.e("name", details.get("name") + "");
                    email.setText(details.get("email"));
                    phone_no.setText(details.get("phone_no"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", "account not found");

            }
        });


        RelativeLayout relativeLayout = findViewById(R.id.accouontSettingsLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, Edit_Account.class);
                startActivity(intent);
            }
        });

        TextView editAccount = findViewById(R.id.editAccountTextView);
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, Edit_Account.class);
                startActivity(intent);
            }
        });
    }
}
