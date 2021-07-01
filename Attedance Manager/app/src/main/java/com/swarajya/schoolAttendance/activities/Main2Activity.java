package com.swarajya.schoolAttendance.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.swarajya.schoolAttendance.R;

public class Main2Activity extends AppCompatActivity {
  DatabaseReference databaseReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    FirebaseDatabase.getInstance().getReference()
        .child("child").child("sub child").setValue("Value is value");
    Log.i(" WORKDONE BOI "," FIREBASE Working fine");
  }
}
