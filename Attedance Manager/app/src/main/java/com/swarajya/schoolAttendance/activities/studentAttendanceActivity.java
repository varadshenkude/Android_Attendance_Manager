package com.swarajya.schoolAttendance.activities;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;

import java.util.ArrayList;
import java.util.Calendar;

public class studentAttendanceActivity extends AppCompatActivity {

    private static final String TAG = "studentAttendanceAct" ;
    ListView listView;
    String sid, teacher_id,stddiv;
    EditText date;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;
    String required_date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Calendar calendar;
    private TextView dateView;
    private int year,month,day;
    private int byear,bmonth,bday;
    private Boolean setDate=Boolean.FALSE;
    private String getDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        listView = (ListView) findViewById(R.id.list);
//        date =  findViewById(R.id.tvDateN);
        Bundle bundle1 = getIntent().getExtras();
        sid = bundle1.getString("sid"); // this will return student No
        picDate();
        stddiv =sid.substring(sid.lastIndexOf("_"));
        Log.i("studentAttendanceActi","stddiv"+stddiv+"sid "+sid);
    }
//    public void viewList(View v) {

//        Userlist.clear();
//        dbStudent = ref.child("Student");
//        dbStudent.orderByChild("classes").equalTo("CSE").
//            addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    Userlist.add(dsp.child("sid").getValue().toString());
//                }
//                display_list(Userlist);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        });

//    }

    public void display_list(View view) {

        Studentlist.clear();
        required_date = getDate;
        dbAttendance = ref.child("attendance");
        Studentlist.add("      Subject       "+"Status" + "   ");
            dbAttendance.child(required_date).child(sid.toString()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dsp : dataSnapshot.getChildren()) { //sujects
                        String p1 = dsp.getValue().toString();
                            Studentlist.add(dsp.getKey().toString() + "            "
                                + p1.substring(0,1) +"        ");
                        Log.i(TAG,"$$$$$$$"+p1 );

                    }
                    list(Studentlist);
                    Log.i(TAG, "StudentList "+String.valueOf(Studentlist.isEmpty()));

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }

            });
    }
    public void list(ArrayList studentlist){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void picDate() {
        dateView = findViewById(R.id.tvDateN);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(studentAttendanceActivity.this, mDateSetListener, year, month, day);
//      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = "dd/mm/yyyy:" + day + "/" + month + "/" + year;
                dateView.setText(date);
//        setDated(year,month,day);
//                bday = day;
//                bmonth = month;
//                byear = year;
                setDate = Boolean.TRUE;
                if (month<10){
                    if(day<10 ){
                        getDate = "0"+day + "-" + "0"+month + "-" + year;

                    }else getDate = day + "-" + "0"+month + "-" + year;

                }else if(day<10 ){

                    getDate = "0"+day + "-" + "0"+month + "-" + year;

                }
                else {
                    getDate = day + "-" + month + "-" + year;
                }
                Log.i(TAG, getDate);

            }

        };
    }



}

