package com.swarajya.schoolAttendance.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;

public class loginstudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG ="loginstudent" ;
    Button login;
    DatabaseReference ref;
    String userid,pass;
    String dbpassword;
    Bundle basket;
    EditText username,password;
    ProgressDialog mDialog;
    private boolean isitemselected;
    private String item;
    private String clsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginstudent);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinnerc);
        isitemselected = false;

        login = findViewById(R.id.login);
        username = findViewById(R.id.adminEditText);
        password = findViewById(R.id.adminPasswordEditText);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = username.getText().toString();
                pass = password.getText().toString();

                basket = new Bundle();
                basket.putString("uid", userid);
                basket.putString("message", userid);
                item = spinner2.getSelectedItem().toString();

                if (item.isEmpty() || item.equals("select Class")) {
//                    Toast.makeText(getApplicationContext(), "User Name is null ", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Please Select Class to go ahead" + item, Toast.LENGTH_LONG).show();

                } else {
                    mDialog = new ProgressDialog(loginstudent.this);
                    mDialog.setMessage("Please Wait..." + userid);
                    mDialog.setTitle("Loading");
                    mDialog.show();
//                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");
                     clsid = item + "_" + userid;
                    Log.i("loginstidey", clsid);

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");
//                    mDatabase.orderByChild("stddivsid").equalTo(clsid).addListenerForSingleValueEvent
                    mDatabase.child(clsid).addListenerForSingleValueEvent//as Ihave changed sdi with stddivsid
                        (new ValueEventListener() {
                            String uids;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                     uids = snapshot.getKey();//-> sahi hey bhai
//                                    Log.i(TAG + "uids", uids);
//                                }

                                    mDialog.dismiss();
                                    dbpassword = dataSnapshot.child("spass").getValue(String.class);
                                    Log.i(TAG + "dbpass", dbpassword);

                                    verify(dbpassword);
                                }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "database error", Toast.LENGTH_LONG).show();
                            }
                        });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        if (!item.equals("select Class")) {
            isitemselected = true;
        }
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }
    public void verify(String dbpassword) {
        if(userid.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Username cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if (pass.equalsIgnoreCase(dbpassword) ) {
            //  if (userid.equalsIgnoreCase("admin") && pass.equals("admin")) {
            mDialog.dismiss();
//            if (isitemselected) {
                Intent intent = new Intent(this, studentlogin.class);
            basket.putString("stddivsid", clsid);

            intent.putExtras(basket);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                finish();
//            } else {
//                Toast.makeText(getApplicationContext(), "Please Select std_div to go ahead" + item, Toast.LENGTH_LONG).show();

//            }


            //  }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Enter valid user id or password", Toast.LENGTH_LONG).show();
        }
    }
}
