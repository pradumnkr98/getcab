package com.example.ashish.justgetit.navigation_drawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ashish.justgetit.R;
import com.example.ashish.justgetit.homepage1;
import com.example.ashish.justgetit.login_page;
import com.example.ashish.justgetit.registerhere;
import com.example.ashish.justgetit.user_registration_details;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
    ProgressDialog progressDialog;
    DatabaseReference mreference;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__account);

        settingsName = findViewById(R.id.settingsName);
        settingsEmail = findViewById(R.id.settingsEmail);
        settingsPhone = findViewById(R.id.settingsPhone);
        settingsPassword = findViewById(R.id.settingsPassword);

        // getting current details in EditTexts
        String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("Customers").child(userid);
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
                Log.e("error","account not found");

            }
        });

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Edit_Account.this);
        mreference = FirebaseDatabase.getInstance().getReference();

        Button save = (Button)findViewById(R.id.saveEditAccount);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                if(settingsPassword.getText().toString().trim().length() > 0) {
                    mAuth.createUserWithEmailAndPassword(settingsEmail.getText().toString(), settingsPassword.getText().toString())
                            .addOnCompleteListener(Edit_Account.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Update", "details updated");
                                        Toast.makeText(Edit_Account.this, "Account Details Updated",
                                                Toast.LENGTH_LONG).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        write_user_data(settingsName.getText().toString(), settingsPhone.getText().toString(), settingsEmail.getText().toString());

                                        preferences = PreferenceManager.getDefaultSharedPreferences(Edit_Account.this);
                                        editor = preferences.edit();
                                        editor.putString("Phone No", settingsPhone.getText().toString());
                                        editor.putString("Name", settingsName.getText().toString());
                                        editor.commit();

                                        progressDialog.dismiss();
                                    } else {
                                        // If updation fails, display a message to the user.
                                        Log.w("Update", "details were not updated", task.getException());
                                        Toast.makeText(Edit_Account.this, "Updation Failed" + task.getException(),
                                                Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }
*/

                Intent intent = new Intent(Edit_Account.this,homepage1.class);
                startActivity(intent);
            }
        });
    }

    public void write_user_data(String name, String phone_no, String email_id) {
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        user_registration_details user_registration_details = new user_registration_details(name, phone_no, email_id);
        mreference.child("Customers").child(userId).setValue(user_registration_details);
    }
}
