package com.swarajya.schoolAttendance.ext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;
import com.swarajya.schoolAttendance.activities.SearchRActivity;
import com.swarajya.schoolAttendance.activities.ViewClass;
import com.swarajya.schoolAttendance.models.Count;
import com.swarajya.schoolAttendance.models.Student;

import java.util.ArrayList;

public class StdRecordsActivity extends AppCompatActivity {

  private ArrayList<String> listisnull =  new ArrayList<>();;
  private ListView listView;
  private String stduid;
  private String stddiv;

  private final static String TAG ="StdRecordsActivity";
  private ArrayList<String> sub;
  private DatabaseReference mDbAtt,mDbCount;
  private int Cm;
  private Count count;
  private int cc;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_std_records);

    mDbCount = FirebaseDatabase.getInstance().getReference().child("count");
    mDbAtt = FirebaseDatabase.getInstance().getReference().child("attendance");
    listView = findViewById(R.id.lv);
    Toolbar mToolbar = findViewById(R.id.toobar);
    mToolbar.setTitle("Student - "+stduid);
    Cm = 0;
    stduid = "1_A_1";
    stddiv =stduid.substring(0,stduid.lastIndexOf("_"));
    Log.i(TAG,"stddiv"+stddiv+"sid "+stduid);
    init();


  }

  private void init() {
    try {

      mDbCount.orderByChild(stddiv).orderByChild("Marathi").addListenerForSingleValueEvent
          (new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
      //here is total no of lec

//              for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                 count = dataSnapshot.getValue(Count.class);
//                 cc = (int) Integer.parseInt((snapshot.getValue(String.class)));
                Log.i("DATABASE VALUES##@$@", dataSnapshot.getKey() + count.getMarathi()+"sfsafsfsa ");
next();
//              }
//                Log.i("DATABASE", students.getSname() + "Rollnoll" + students.getSid() +
//                    "std_div" + students.getSclasses());
////                uid = students.getSid();
//                String item;
//                item = students.getSid() + ": " + students.getSname();

//              }

            } @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              Toast.makeText(getApplicationContext(),databaseError.getDetails() ,Toast.LENGTH_LONG).show();
              Log.i("DATABASE ierror", databaseError.getDetails());

            }
          });
      Toast.makeText(getApplicationContext(),"Please Wait while loading data from cloud " ,Toast.LENGTH_LONG).show();



  }catch (NullPointerException e ){
      e.printStackTrace();
      Toast.makeText(getApplicationContext(),"WE GOt NULL pointer Ex" ,Toast.LENGTH_LONG).show();

    }
  }

  private void next(){
      // now I am getting a student's att for maths
      try {


        mDbAtt.addValueEventListener(
            new ValueEventListener() {

              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                  String at= "null";
                  if(!snapshot.child("/"+stduid+"/"+"Marathi").getValue(String.class).isEmpty()){
                    at = snapshot.child("/"+stduid+"/"+"Marathi").getValue(String.class);
                  }
                  Log.i(TAG,"at = "+at);
                  at = at.substring(at.lastIndexOf("/"));

                  if (at.equals("P ")){
                    Cm++;
                    newNext();
                  }

                }


              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }

            });}
      catch (NullPointerException e ){
        e.printStackTrace();
        Toast.makeText(getApplicationContext(),"WE GOt NULL pointer Ex" ,Toast.LENGTH_LONG).show();

      }
    }

  private void newNext() {

    try {


      String rs = String.valueOf(Cm / 15 * 100);
      Log.i("VALUES OF INT CM, Total", String.valueOf(Cm) + " " + count.getMarathi());

      listisnull.add(rs);
//    listisnull.add(students.getSid() + "  " + students.getSname());
      Log.i("VALUES OF  RS", rs);

      if (!listisnull.isEmpty()) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StdRecordsActivity.this, android.R.layout.simple_list_item_1, listisnull);
        listView.setAdapter(adapter);
        Log.i("SETED DATA ", "data Successfully " + rs);
      } else {
        Toast.makeText(getApplicationContext(), "No Student in this class", Toast.LENGTH_LONG).show();

      }
    } catch (ArithmeticException e) {
      e.printStackTrace();
    }
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
                              long id) {
        Intent intent = new Intent(getApplicationContext(), SearchRActivity.class);
        String d = listisnull.get(position);
        d = d.substring(0, d.indexOf(":"));
//std sid retrived in d
        d = stduid + "_" + d;
        //suid passes
        intent.putExtra("uid", d);
        intent.putExtra("sid", d);
        Log.i("Selected item", "pos " + position + "id" + d);

        startActivity(intent);
        finish();

      }
    });
  }




}
