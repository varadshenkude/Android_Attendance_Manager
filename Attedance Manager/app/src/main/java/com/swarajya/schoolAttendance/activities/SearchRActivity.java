package com.swarajya.schoolAttendance.activities;
//uid np prb here
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.swarajya.schoolAttendance.R;
import com.swarajya.schoolAttendance.models.Student;

import java.util.ArrayList;

public class SearchRActivity extends AppCompatActivity {
    private final String TAG = "SelectDivActivity";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView listView;
    private ListView listView0;
    private ImageView imagepp;
    private Student students;
    private static long back_pressed;
    private int width;

//    private Fi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_div);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
////        int height = displayMetrics.heightPixels;
//        width = displayMetrics.widthPixels;
        // Gets linearlayout
//         listView = findViewById(R.id.listView);
//         listView0 = findViewById(R.id.listView0);
// Gets the layout params that will allow you to resize the layout
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        ViewGroup.LayoutParams params0 = listView0.getLayoutParams();
// Changes the height and width to the specified *pixels*
//        params.height = 100;
//        params.width = params.width/2;
//        params0.width = params0.width/2;
//        listView.setLayoutParams(params);
//        listView0.setLayoutParams(params0);
        init();
    }

    private void init() {
        listView = findViewById(R.id.listView);
        listView0 = findViewById(R.id.listView0);
        String[] list = new String[]{"Student Name",  "MotherName", "Addresss",
                "Std_Div", "Gender", "MobileNo", "RollNo", "DateOfBirth", "Email", "Password"};
        ArrayAdapter adapter = new ArrayAdapter<>(SearchRActivity.this,R.layout.my_list_,list);
        listView0.setAdapter(adapter);
        imagepp = findViewById(R.id.imagepp);

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("students")
//                .child("T4bpaTz1EXVMG4KAcci2MG53nAh1");
        String uids = getIntent().getStringExtra("uid");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child(uids);
        Log.i(TAG+"suid", uids);
        Toast.makeText(SearchRActivity.this,TAG+"suid"+ uids,Toast.LENGTH_LONG).show();

                databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayAdapter<String> adapter ;
                ArrayList <String> list = new ArrayList<>();
                adapter = new ArrayAdapter<>(SearchRActivity.this,R.layout.my_list_,list);
                 students = snapshot.getValue(Student.class);

                Picasso.with(getApplicationContext()).load(students.getSphotourl()).into(imagepp);
                Log.i("VALUE",students.getSname()+"\n"+
                    students.getSmothername()+"\n"+students.getSaddresss()+
                    "\n"+"\n"+students.getSclasses()+"\n"+
                    "\n"+students.getSgender()+"\n"
                    +"\n"+students.getSmobileno()+"\n"+
                    "\n"+students.getSid()+"\n"+
                    "\n"+students.getSdateofbirth()+"\n"
                    +"\n"+students.getSemail()+"\n"+students.getSpass()+"\n"



                );
                list.add(students.getSname());
                list.add(students.getSmothername());
                list.add(students.getSaddresss());
                list.add(students.getSclasses());
                list.add(students.getSgender());
                list.add(students.getSmobileno());
                list.add(students.getSid());
                list.add(students.getSdateofbirth());
                list.add(students.getSemail());
                list.add(students.getSpass());


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


        FloatingActionButton fab = findViewById(R.id.faba);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
                next(view);
            }
        });
    }
    public  void next(View view){
        Intent intent = new Intent(SearchRActivity.this,PreUpStdAct.class);
        intent.putExtra("sid",students.getSid());
        intent.putExtra("sname",students.getSname() );
        intent.putExtra("semail",students.getSemail() );
        intent.putExtra("smobile",students.getSmobileno() );
        intent.putExtra("smother",students.getSmothername() );
        intent.putExtra("sgender",students.getSgender() );
        intent.putExtra("sclass",students.getSclasses() );
        intent.putExtra("sdateofbith",students.getSdateofbirth() );
        intent.putExtra("saddress",students.getSaddresss() );
        intent.putExtra("sphotourl",students.getSphotourl()) ;
        intent.putExtra("spass",students.getSpass()) ;
        startActivity(intent);
        finish();

    }
  @Override
  public void onBackPressed() {
    if (back_pressed + 2000 > System.currentTimeMillis()){
      super.onBackPressed();
      finish();
      ActivityCompat.finishAffinity(this);
      System.exit(0);
    }
    else {
      Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
      back_pressed = System.currentTimeMillis();
    }
  }

}
