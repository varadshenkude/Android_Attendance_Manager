package com.swarajya.schoolAttendance.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import com.swarajya.schoolAttendance.R;

public class SelectDivActivity extends AppCompatActivity {
    private final String TAG = "SelectDivActivity";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView listView;
    private ListView listView0;
//    private Fi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_div);
//        Intent prev  ;
//        int cls= Integer.getInteger(getIntent().getStringExtra("std"));
        init();
    }

    private void init() {
        listView = findViewById(R.id.listView);
        listView0 = findViewById(R.id.listView0);
        String[] list = new String[]{"FirstName", "LastName", "MiddleName", "MotherName", "Addresss",
                "Standard", "Division", "Gender", "MobileNo", "RollNo", "DateOfBirth", "Email", "Password"};
        ArrayAdapter adapter = new ArrayAdapter<>(SelectDivActivity.this, R.layout.my_list_, list);
        listView0.setAdapter(adapter);
//        tvFN= findViewById(R.id.tvFN);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("students")
                .child("T4bpaTz1EXVMG4KAcci2MG53nAh1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayAdapter<String> adapter ;
                ArrayList <String> list = new ArrayList<>();
                adapter = new ArrayAdapter<>(SelectDivActivity.this,R.layout.my_list_,list);

                for (DataSnapshot dSnapshot:snapshot.getChildren()){
                    String s = dSnapshot.getValue().toString();
                    Log.i("SelectDivActFiRebase",s);
//                    list.clear();
                    list.add(s);


                }
                if (adapter.isEmpty()){
                    Log.i(TAG,"adaptor is null");
                }else {
                    listView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }

//                Students students = snapshot.getValue(Students.class);
//                String fn = snapshot.child("FirstName").getValue().toString();
//                tvFN.setText(fn);
//                Log.i(TAG,"FirstName"+fn);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Students studentss = databaseReference.child("students");
//        Students students = new ();


    }
}
