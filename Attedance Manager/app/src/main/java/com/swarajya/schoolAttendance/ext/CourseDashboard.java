package com.swarajya.schoolAttendance.ext;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.swarajya.schoolAttendance.R;
import com.swarajya.schoolAttendance.activities.MainActivity;

public class CourseDashboard extends AppCompatActivity {

    // Set up the menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Listener for clicks.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If the back button is pressed.
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        // Create new intent.
        Intent login = new Intent(CourseDashboard.this, MainActivity.class);
        startActivity(login);
        // Finish currentActivity.
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dashboard);
        // Set the activity title.
        setTitle("Course Dashboard");

        // Set Listeners for the cardview clicks.
        // For Reports
        ConstraintLayout cvReports = (ConstraintLayout) findViewById(R.id.cvReports);
        cvReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new intent.
//                Intent intent = new Intent(CourseDashboard.this, ReportsDashboard.class);
                // Grab the extra from previous intent.
//                String item = getIntent().getStringExtra("courseName");
                // Attach the string to new intent.
//                intent.putExtra("courseName", item);
                // Start the new activity.
//                startActivity(intent);
            }
        });

        // For Attendance.
        ConstraintLayout cvAttendance = (ConstraintLayout) findViewById(R.id.cvAttendance);
        cvAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new intent.
                Intent intent = new Intent(CourseDashboard.this, Attendance.class);
                // Grab the extra from previous intent.
                String item = getIntent().getStringExtra("courseName");
                // Attach the string to new intent.
                intent.putExtra("courseName", item);
                // Start the new activity.
                startActivity(intent);
            }
        });

    }
}
