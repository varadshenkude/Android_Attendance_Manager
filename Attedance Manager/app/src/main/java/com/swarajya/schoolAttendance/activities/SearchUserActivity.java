package com.swarajya.schoolAttendance.activities;
// done search by roll done 🥳🎉👯‍♂️👯‍♀️🎉🎉🔥🥙🥙

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.swarajya.schoolAttendance.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final String TAG = "SearchUserActivity";
    EditText id;
    Button getUser,Btnbyclass;
    TextView result,pleseWait,Tvsearch;
    private String item;
    Spinner spinner2;
    Intent intent;
    private String classes;
    boolean isclassset =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        init();
    }

    private void init() {

        Btnbyclass = findViewById(R.id.byclass);
        Tvsearch = findViewById(R.id.TvserchByRoll);
        Btnbyclass.setVisibility(View.INVISIBLE);
        Tvsearch.setVisibility(View.INVISIBLE);


        getUser = findViewById(R.id.getUser);
        id = findViewById(R.id.EtID);
        getUser.setVisibility(View.INVISIBLE);
        id.setVisibility(View.INVISIBLE);

        pleseWait = findViewById(R.id.tvResult);
        pleseWait.setVisibility(View.INVISIBLE);

         spinner2 = (Spinner) findViewById(R.id.spinn);
        spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
            R.array.classes, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(this);

        getUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != null) {
                    String sid = id.getText().toString();
                    if ((isclassset&&!sid.isEmpty())&&!classes.equals("select Class")) {
                        pleseWait.setVisibility(View.VISIBLE);

                        getDataFromFB(sid, classes);

                    }else {
                        Toast.makeText(SearchUserActivity.this, TAG + "Please select standard and division" , Toast.LENGTH_LONG).show();
                        pleseWait.animate().rotation((float) 10.1);
                        pleseWait.setText("Please select standard and division");
                    }
                }
            }
        });
    }

    private void getDataFromFB(final String sid,String classes) {
        final String clsid = classes+"_"+sid;
        Log.i("SEarch user activity ",clsid);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");
        mDatabase.child(clsid).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String uids = snapshot.getKey();//-> sahi hey bhai
                            Log.i(TAG + "uids", uids);
                            Toast.makeText(SearchUserActivity.this, TAG + "uid" + uids, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SearchUserActivity.this, SearchRActivity.class);
                            intent.putExtra("uid", clsid);
                            intent.putExtra("stddiv", clsid);
                            intent.putExtra("sid", sid);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("Search user activity  ",databaseError.getDetails());
                        Toast.makeText(SearchUserActivity.this, TAG + "Please select standard and division to search" , Toast.LENGTH_LONG).show();

                    }
                });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        item = parent.getItemAtPosition(position).toString();
        classes = item;
        isclassset = true;
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
         intent = new Intent(SearchUserActivity.this,ViewClass.class);
        intent.putExtra("class_selected", item);
        if (!item.equals("select Class")) {
            showOptions();
        }
    }

    private void showOptions() {
        Btnbyclass.setVisibility(View.VISIBLE);
        Tvsearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(), "Please Select Class", Toast.LENGTH_LONG).show();
        isclassset = false;
    }

    public  void  byclass(View view){
        if (!item.equals("select Class")) {
            startActivity(intent);
            finish();

        }else {
            Toast.makeText(getApplicationContext(), "Please Select Class", Toast.LENGTH_LONG).show();

        }
    }
    public void searchByRoll(View view){
        id.setVisibility(View.VISIBLE);
        getUser.setVisibility(View.VISIBLE);



    }
}
/*I dont need code below*/

        /* here I unable  to search by roll
         no*/
        /*final Query databaseReference = FirebaseDatabase.getInstance().getReference().child("students")
                .orderByChild("RollNO").equalTo(sid);
//        String t = databaseReference.toString();
        final String st;
//        DatabaseReference uid = databaseReference.getRef().getKey();
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Query uid = dataSnapshot.getRef().orderByChild("RollNO").equalTo(sid);
//                String uid =(String) dataSnapshot.getRef().orderByChild("RollNO").equalTo(sid);
//                 String uids = uid.getRef().getParent().getKey(); // -> null
                 @SuppressLint("RestrictedApi") String uids = String.valueOf(uid.getPath());
//                 String uids = String.valueOf(uid.getPath()); //-> students


                String FName = dataSnapshot.child("FirstName").getValue(String.class);
//                Log.i(TAG+"FName", FName.toString());
                Toast.makeText(SearchUserActivity.this,TAG+"uid"+ uids,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SearchUserActivity.this,SearchRActivity.class);
                intent.putExtra("uid", uids);
                intent.putExtra("id",id.getText().toString());
//                startActivity(intent);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



//        OTHEWR stuff



//needeed
//        Log.i(TAG+"uid", st[0].toString());
//        Toast.makeText(SearchUserActivity.this,TAG+"uid"+ st[0].toString(),Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(SearchUserActivity.this,SearchRActivity.class);
//        intent.putExtra("uid", st[0].toString());
//        intent.putExtra("id",id.getText().toString());

//    });

//}

