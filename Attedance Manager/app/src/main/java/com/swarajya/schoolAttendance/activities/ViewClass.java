package com.swarajya.schoolAttendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;
import com.swarajya.schoolAttendance.models.Student;

import java.util.ArrayList;


public class ViewClass extends AppCompatActivity {

  private ListView listView;
  private String class_selected;
  private ArrayList<String> listisnull =  new ArrayList<>();;

  private String uid;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_class);
    listView = findViewById(R.id.lv);
    class_selected = getIntent().getStringExtra("class_selected");
    assert class_selected != null;
    Log.i("ViewClass", class_selected);

    Toolbar mToolbar = findViewById(R.id.toobar);
    mToolbar.setTitle("Class Selected  - "+class_selected);
    init();


  }

  private void init() {
    try {
      FirebaseDatabase.getInstance().getReference()
          .child("Student").orderByChild("sclasses")
          .equalTo(class_selected).addListenerForSingleValueEvent
          (new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
              }, 1000);

              for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                Student students = snapshot.getValue(Student.class);
                Log.i("DATABASE", students.getSname() + "Rollnoll" + students.getSid() +
                    "std_div" + students.getSclasses());
//                uid = students.getSid();
                String item;
                  item = students.getSid() + ": " + students.getSname();
                  listisnull.add(item);
//                  listisnull.add(students.getSid() + "  " + students.getSname());
                  Log.i("DATABASE item", item);

                if (!listisnull.isEmpty()) {
                  ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewClass.this, android.R.layout.simple_list_item_1, listisnull);
                  listView.setAdapter(adapter);
                  Log.i("SEt", "data Successfully");
                } else {
                  Toast.makeText(getApplicationContext(),"No Student in this class" ,Toast.LENGTH_LONG).show();

                }
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              Toast.makeText(getApplicationContext(),databaseError.getDetails() ,Toast.LENGTH_LONG).show();
              Log.i("DATABASE ierror", databaseError.getDetails());

            }
          });
      Toast.makeText(getApplicationContext(),"Please Wait while loading data from cloud " ,Toast.LENGTH_LONG).show();

    } catch (Exception e) {
      e.printStackTrace();
    }

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
                              long id) {
        Intent intent = new Intent(getApplicationContext(), SearchRActivity.class);
        String d = listisnull.get(position);
        d = d.substring(0,d.indexOf(":"));
//std sid retrived in d
        d =class_selected+"_"+d;
        //suid passes
        intent.putExtra("uid", d);
        intent.putExtra("sid", d);
        Log.i("Selected item","pos "+position+"id"+d);

        startActivity(intent);
        finish();

      }
    });
  }
}
