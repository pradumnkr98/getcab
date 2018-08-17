package com.example.ashish.justgetit.navigation_drawer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashish.justgetit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class profile_page extends AppCompatActivity {
    Uri imageuri;
    ImageView user_img;
    private int PICK_IMAGE = 100;
    TextView email1, name1, phone_no1;
    private DatabaseReference mreference;

    private static int RESULT_LOAD_IMAGE = 1;

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
                    Map<String, String> details1 = (Map) dataSnapshot.getValue();
                    name1.setText(details1.get("name"));
                    email1.setText(details1.get("email"));
                    phone_no1.setText(details1.get("phone_no"));


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error_oncancelled", "Failed to read value.", error.toException());
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                try {
                    if (ActivityCompat.checkSelfPermission(profile_page.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(profile_page.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(profile_page.this);
        String picturePath1 = preferences.getString("picture_path", "");
        ImageView imageView = findViewById(R.id.user_img);
        Log.e("picture1", picturePath1 + "");
        im0ageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("picture_path", picturePath);
            Log.e("picture", picturePath + "");


        }

    }
}


