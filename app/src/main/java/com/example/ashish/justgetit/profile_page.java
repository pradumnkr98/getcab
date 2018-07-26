package com.example.ashish.justgetit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_page extends AppCompatActivity {
    Button change_pic;
    Uri imageuri;
    ImageView user_img;
    private int PICK_IMAGE = 100;
    TextView email1, name1, phone_no1;
    private DatabaseReference mreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        change_pic = findViewById(R.id.change_image);
        user_img = findViewById(R.id.user_img);
        mreference = FirebaseDatabase.getInstance().getReference().child("Customers");
        name1 = findViewById(R.id.my_name);
        email1 = findViewById(R.id.my_email);
        phone_no1 = findViewById(R.id.my_number);


        change_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });


        mreference.addValueEventListener(new ValueEventListener() {
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
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageuri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imageuri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            user_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }
}
