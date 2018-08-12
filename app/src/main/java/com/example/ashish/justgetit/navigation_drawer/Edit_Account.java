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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Edit_Account extends AppCompatActivity {

    EditText settingsName, settingsEmail, settingsPhone, settingsPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__account);

        settingsName = findViewById(R.id.settingsName);
        settingsEmail = findViewById(R.id.settingsEmail);
        settingsPhone = findViewById(R.id.settingsPhone);
        settingsPassword = findViewById(R.id.settingsPassword);

        Button save = (Button)findViewById(R.id.saveEditAccount);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(settingsName.getText().toString())
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Update","User profile updated.");
                                }
                            }
                        });

                Intent intent = new Intent(Edit_Account.this,homepage1.class);
                startActivity(intent);
            }
        });
    }
}
