package com.swarajya.schoolAttendance.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ViewComplainActivity extends AppCompatActivity {

  private ListView lvcomlains;
  private ArrayList comps = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_complain);
    lvcomlains = findViewById(R.id.lvcomplain);
    getComplains();
  }

  private void getComplains() {
    FirebaseDatabase.getInstance().getReference().child("complainbox").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot :snapshot.getChildren()){
          comps.add(dataSnapshot.getKey().toString()+"_"+dataSnapshot.getValue().toString());


        }
        setLvComplains(comps);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }

  private void setLvComplains(ArrayList complains) {
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,complains);
    lvcomlains.setAdapter(adapter);
    Log.i("ViewComplainActivity","lv complains are set in lv");
  }

}
