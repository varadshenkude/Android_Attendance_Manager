package com.swarajya.schoolAttendance.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.swarajya.schoolAttendance.models.Teacher;

import java.util.ArrayList;

public class ViewFacActivity extends AppCompatActivity {
  private final String TAG = "ViewFacActivity";
  private FirebaseDatabase firebaseDatabase;
  private DatabaseReference databaseReference;
  private ListView listView;
  private ListView listView0;
  private ImageView imagepp;
  private Teacher teacher;
//    private Fi

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_div);
    init();
  }

  private void init() {
    listView = findViewById(R.id.listView);
    listView0 = findViewById(R.id.listView0);
    String[] list = new String[]{"Faculty Name",  "MotherName", "Addresss",
        "Standard_Division", "Gender", "MobileNo", "RollNo", "DateOfBirth", "Email", "Password"};
    ArrayAdapter adapter = new ArrayAdapter<>(ViewFacActivity.this,R.layout.my_list_,list);
    listView0.setAdapter(adapter);
    imagepp = findViewById(R.id.imagepp);

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher")
//                .child("T4bpaTz1EXVMG4KAcci2MG53nAh1");
    String uids = getIntent().getStringExtra("uid");
    databaseReference = FirebaseDatabase.getInstance().getReference().child("Teacher").child(uids);
    Log.i(TAG+"uid", uids);
    Toast.makeText(ViewFacActivity.this,TAG+"uid"+ uids,Toast.LENGTH_LONG).show();

    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        ArrayAdapter<String> adapter ;
        ArrayList <String> list = new ArrayList<>();
        adapter = new ArrayAdapter<>(ViewFacActivity.this,R.layout.my_list_,list);
        teacher = snapshot.getValue(Teacher.class);

        Picasso.with(getApplicationContext()).load(teacher.getTphotourl()).into(imagepp);
        Log.i("VALUEW View FacActivity",teacher.getTid()+"\n"+
            teacher.getTmothername()+"\n"+teacher.getTaddresss()+
            "\n"+"\n"+teacher.getTclasses()+"\n"+
            "\n"+teacher.getTgender()+"\n"
            +"\n"+teacher.getTmobileno()+"\n"+
            "\n"+teacher.getTid()+"\n"+
            "\n"+teacher.getTdateofbirth()+"\n"
            +"\n"+teacher.getTemail()+"\n"+teacher.getTpass()+"\n"



        );
        list.add(teacher.getTname());
        list.add(teacher.getTmothername());
        list.add(teacher.getTaddresss());
        list.add(teacher.getTclasses());
        list.add(teacher.getTgender());
        list.add(teacher.getTmobileno());
        list.add(teacher.getTid());
        list.add(teacher.getTdateofbirth());
        list.add(teacher.getTemail());
        list.add(teacher.getTpass());


        if (adapter.isEmpty()){
          Log.i(TAG,"adaptor is null");
        }else {
          listView.setAdapter(adapter);

          adapter.notifyDataSetChanged();
        }

//                Students teacher = snapshot.getValue(Students.class);
//                String fn = snapshot.child("FirstName").getValue().toString();
//                tvFN.setText(fn);
//                Log.i(TAG,"FirstName"+fn);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
//        Students studentss = databaseReference.child("teacher");
//        Students teacher = new ();


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
    Intent intent = new Intent(ViewFacActivity.this,PreUpTeachAct.class);
    intent.putExtra("tid",teacher.getTid());
    intent.putExtra("tname",teacher.getTname() );
    intent.putExtra("temail",teacher.getTemail() );
    intent.putExtra("tmobile",teacher.getTmobileno() );
    intent.putExtra("tmother",teacher.getTmothername() );
    intent.putExtra("tgender",teacher.getTgender() );
    intent.putExtra("tclass",teacher.getTclasses() );
    intent.putExtra("tdateofbith",teacher.getTdateofbirth() );
    intent.putExtra("taddress",teacher.getTaddresss() );
    intent.putExtra("tphotourl",teacher.getTphotourl()) ;
    intent.putExtra("tpass",teacher.getTpass()) ;
    startActivity(intent);
    finish();

  }
}
