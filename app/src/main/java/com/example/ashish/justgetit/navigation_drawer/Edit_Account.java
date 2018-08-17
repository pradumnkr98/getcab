package com.example.ashish.justgetit.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.homepage1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Edit_Account extends AppCompatActivity {

    EditText settingsName, settingsEmail, settingsPhone, settingsPassword;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__account);

        settingsName = findViewById(R.id.settingsName);
        settingsEmail = findViewById(R.id.settingsEmail);
        settingsPhone = findViewById(R.id.settingsPhone);
        //settingsPassword = findViewById(R.id.settingsPassword);

        // getting current details in EditTexts
        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Customers").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, String> details = (Map) dataSnapshot.getValue();
                    settingsName.setText(details.get("name"));
                    settingsEmail.setText(details.get("email"));
                    settingsPhone.setText(details.get("phone_no"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", "account not found");

            }
        });


        Button save = findViewById(R.id.saveEditAccount);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating user details
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Customers").child(userid).child("name").setValue(settingsName.getText().toString());
                mDatabase.child("Customers").child(userid).child("phone_no").setValue(settingsPhone.getText().toString());
                mDatabase.child("Customers").child(userid).child("email").setValue(settingsEmail.getText().toString());


                Intent intent = new Intent(Edit_Account.this, homepage1.class);
                startActivity(intent);
            }
        });
    }


}