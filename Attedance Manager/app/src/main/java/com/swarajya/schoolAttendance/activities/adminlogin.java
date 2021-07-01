package com.swarajya.schoolAttendance.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class adminlogin extends AppCompatActivity {

    DatabaseReference ref;
    DatabaseReference dbStudent;
    DatabaseReference dbAttendance;
    DatabaseReference dbadmin;
    Toolbar mToolbar;
    private static long back_pressed;

    ArrayList Studentlist = new ArrayList<>();

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        mToolbar=findViewById(R.id.ftoolbar11);
        mToolbar.setTitle("Admin Dashboard : "+"("+date+")");
        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("Student");
        dbAttendance = ref.child("attendance");


    }
    public void AddTeacherButton(View v){
        user = "teacher";
        Intent intentt = new Intent(this, PreStdAct.class);
//        intentt.putExtra("user","teacher");
        startActivity(intentt);
    }
    public void AddStudentButton(View v){
        user = "student";
        Intent intent = new Intent(this, PreStdAct.class);
//        intent.putExtra("user","student");

        startActivity(intent);
//        finish();

    }
    public void attendanceRecord(View v){
//        this is view student activity
        startActivity(new Intent(this, SearchUserActivity.class));
//        finish();
    }

    public void CreateAttendance(View v){
//        this is view teacher activity
        startActivity(new Intent(this, SearchTeacherActivity.class));



    }


    public void logout(View view) {

        Intent logout=new Intent(adminlogin.this,DashboardActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void changepassword(View view) {
        dbadmin=ref.child("Admin");

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Set your new password");
            final LayoutInflater inflater = this.getLayoutInflater();
            View add_menu_layout = inflater.inflate(R.layout.changepassword, null);
            final EditText password= add_menu_layout.findViewById(R.id.newpassword);
            alertDialog.setView(add_menu_layout);
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    if (!TextUtils.isEmpty(password.getText().toString()))
                    {
                        dbadmin.child("Admin").setValue(password.getText().toString());
                        Toast.makeText(adminlogin.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(adminlogin.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                    }



                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();


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

/*firebaseDatabase.getReference("parent")
                            .orderByChild("childNode")
                            .startAt("[a-zA-Z0-9]*")
                            .endAt(searchString)


*/