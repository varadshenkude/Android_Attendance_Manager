package com.swarajya.schoolAttendance.activities;
//when data is saved we only get marathi no use of spinner and if not selected it even thought the data is saved as marathi
//just working as expected 🥳🎇🎀🎂🎑🎊 but
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.swarajya.schoolAttendance.ext.Student;
import com.swarajya.schoolAttendance.ext.StudentsAttendanceList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class AttendanceM extends AppCompatActivity implements AdapterView.OnItemSelectedListener   {

    private ArrayList<Student> students= new ArrayList<>();
    private ListView lv;
    private MyAttList list;
    private String courseKey;
//    private String courseName;
    private String class_selected;
    Spinner period;

    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private static final  String TAG  ="AttendanceM";
    private List<Student> stud;
    private boolean didweget,pressed;
    private String teacher_id;

    // Set up  .menu the
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Listener for clicks.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // If the back button is pressed.
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        // Create new intent.
        Intent login = new Intent(AttendanceM.this, MainActivity.class);
        startActivity(login);
        // Finish currentActivity.
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG,"OnCreate ");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse_attendance);
        // Set the activity title.
        setTitle("Attendance");

        selectedItems = new ArrayList<String>();

        didweget = false;
        pressed = false;
        // Grab the course name from intent.


        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");
        period = (Spinner) findViewById(R.id.spinner4);
        lv = findViewById(R.id.lvStudents);
        final ArrayList<String> studentKeys = new ArrayList<>();

        Log.i("AttendanceM ","tid"+ teacher_id +"classes"+class_selected);
//getting roll no and names directly
//using method to load from firebase we need as when spinner values changes list need to update
        loadDataFB();



        // Add Listener to fill the data.
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {

//                if (dataSnapshot.exists()) {
//                    Boolean found = false;
//                    for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                        // Grab the course name.
//                        String tempCourseName = courseSnapshot.child("name").getValue().toString();
                        // Grab the course key.
//                        if (tempCourseName.equals(courseName)) {
//                            for (DataSnapshot subStd: courseSnapshot.child("students").getChildren()) {
//                                String key = subStd.child("studentNo").getValue().toString();
//        for (int i =0;i<10;i++) {
//            studentKeys.add(String.valueOf(i));
//        }
//                            }
//                            found = true;
//                        }
                        // Break when key is found.
//                        if (found) break;
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });


        // Finally grab the students.
        // Build Query for the table.
//        final Query stdQuery = database.getReference("students");
//
//        stdQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    // Grab the key.
//                    String stdKey = courseSnapshot.getKey();
                    // Match the key.
//                    if (studentKeys.contains(stdKey)) {
                        // Grab the student.
//                        Student std = courseSnapshot.getValue(Student.class);


//                    }
//                }
                // Creating object of custom view item.
//                list = new StudentsAttendanceList(AttendanceM.this, students);

//         To Do  add compulsuon to spinner

//            }

//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//
//            }
//        });


        // Add Listener to search the data.
        // Grab the edit text view.
        final EditText editTxt = (EditText)findViewById(R.id.etSearch);
        editTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // Grab radio button to identify search type.
                RadioGroup rg = findViewById(R.id.rgSearch);
                // Grab the value of radio button.
                String value = ((RadioButton) findViewById(rg.getCheckedRadioButtonId()))
                    .getText().toString();
                // Get the Search Query.
                String text = editTxt.getText().toString().toLowerCase(Locale.getDefault());

                // Check the search type.
                                      try {


                if (value.equals("Name")) {
                    list.filterByName(text);
                } else {
                    list.filterByRollNo(text);
                }
                      }catch (NullPointerException e){
                                          Log.i(TAG,e.getMessage());
                      }


            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadDataFB()
    {
//        we need time to set value in list view till get  data from fb
        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference().child("Student");

        dbuser.orderByChild("sclasses").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    Log.i("AttendanceM ","tid"+teacher_id+"classes"+class_selected);

                    Student std000 = new Student(dsp.child("sname").getValue().
                        toString(), dsp.child("sid").getValue().toString()
                        , dsp.child("semail").getValue().toString(), "p");
                    Log.i("AttendanceM ", "Std" + std000.getSid() + "name" + std000.getSname());

                    students.add(std000);
//                    stud.add(std000);
                    didweget = true;
                }
//                OnStart(Userlist);
                setlv();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void setlv() {
        String periodno = period.getSelectedItem().toString();
        if (students.isEmpty()|| didweget==false){
            Toast.makeText(getApplicationContext(), "Please wait ", Toast.LENGTH_LONG).show();

        }
        else {
            Log.i("AttendanceM ", "  students  is empty ?" + String.valueOf(students.isEmpty()));
            Log.i("AttendanceM ", "  period No ?" + periodno);

                if (periodno.equals("Select Period")) {
                    Toast.makeText(this, "Select a Period", Toast.LENGTH_LONG).show();
                    Log.i("AttendanceM ", "  period No ?" + periodno);

                } else
//                    if (periodno.equals("Marathi")|| !periodno.equals("Select Period"))
                    {
                    Log.i("AttendanceM ", "  period No ?" + periodno);

                    list = new MyAttList(AttendanceM.this,
                        students, teacher_id, class_selected);
                    lv.setAdapter(list);
                    Log.i("AttendanceM ", "  list of student is empty ?" + String.valueOf(list.isEmpty()));
                    }
        }
    }

    /**
     * Marks all the student present in current view.
     *
      * @param view Current View
     */
    public void markAllPresent(View view) {
        // Iterate over each view and mark the attendance.
        for (int i = 0; i < lv.getCount(); i++) {
            // Grab the child.
            View v = lv.getChildAt(i);
            // Grab radio group.
            RadioGroup rg = v.findViewById(R.id.rgAttendance);
            // Grab the present button.
            RadioButton rb = rg.findViewById(R.id.rbPresent);
            // Set it true.
            rb.setChecked(true);
        }
    }

    /**
     * Marks all the student absent in current view.
     *
     * @param view Current View
     */
    public void markAllAbsent(View view) {
        // Iterate over each view and mark the attendance.
        for (int i = 0; i < lv.getCount(); i++) {
            // Grab the child.
            View v = lv.getChildAt(i);
            // Grab radio group.
            RadioGroup rg = v.findViewById(R.id.rgAttendance);
            // Grab the present button.
            RadioButton rb = rg.findViewById(R.id.rbAbsent);
            // Set it true.
            rb.setChecked(true);
        }
    }

    /**
     * Saves the current Attendance.
     *
     * @param view Current View.
     */
    public void saveAttendance(View view) {
        Log.i(TAG,"saveAttendance "+String.valueOf(list.isEmpty()));

        list.saveAttendance(period.getSelectedItem().toString());

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String periodno = period.getSelectedItem().toString();
        Log.i("AttendanceM ", " onItemSelected period No ?" + periodno);
//period.setOnItemClickListener(adapterView.getOnItemClickListener());
//        period.onClick(period.);
//        pressed = true;
        setlv();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
