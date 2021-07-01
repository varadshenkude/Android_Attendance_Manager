package com.swarajya.schoolAttendance.activities;
//please add compulsion to selectv class
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.swarajya.schoolAttendance.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class teacherlogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String item;
    String message;//contains sid uid
    Toolbar mToolbar;
    private static long back_pressed;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private boolean isitemselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinn);
        isitemselected = false;

        //to get username from login page
        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");
        mToolbar = (Toolbar) findViewById(R.id.takeattendancebar);
//        setSupportActionBar(mToolbar);
        mToolbar.setTitle(message + "'s Dashboard  - " + date);

        TextView txtView = (TextView) findViewById(R.id.textView1);
        txtView.setText("Welcome : " + message);
        spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
            R.array.classes, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
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

    public void takeAttendanceButton(View v) {
        Bundle basket = new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);

        if (isitemselected) {
            Intent intent = new Intent(this, AttendanceM.class);
            intent.putExtras(basket);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please Select std_div to go ahead" + item, Toast.LENGTH_LONG).show();

        }
    }

    public void previous_records(View v) {
        Bundle basket = new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);

        if (isitemselected) {

            Intent intent = new Intent(this, teacher_attendanceSheet.class);
            intent.putExtras(basket);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please Select std_div to go ahead" + item, Toast.LENGTH_LONG).show();

        }
    }


    public void logoutTeacher(View view) {
        Intent logoutTeacher = new Intent(teacherlogin.this, DashboardActivity.class);
        logoutTeacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutTeacher);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    public void viewstd(View view) {
        Intent intent = new Intent(teacherlogin.this, SearchUserActivity.class);
        startActivity(intent);
    }

    public void viewprofile(View view) {
        Intent intent = new Intent(teacherlogin.this, ViewFacActivity.class);
        intent.putExtra("uid", message);
        intent.putExtra("sid", message);
        startActivity(intent);
    }

    public void saveNotes(View view) {
        if (isitemselected) {

            Intent intent = new Intent(teacherlogin.this, takeNActivity.class);

            intent.putExtra("tid", message);
            intent.putExtra("class", item);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please Select std_div (Class) to go ahead" + item, Toast.LENGTH_LONG).show();

        }
    }
}
