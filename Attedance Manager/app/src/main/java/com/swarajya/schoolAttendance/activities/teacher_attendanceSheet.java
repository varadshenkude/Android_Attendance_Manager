package com.swarajya.schoolAttendance.activities;
//Fault in getDate as it was returing 7-9-2020 instead of 07-09-2020 and now i had fixed it
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;

import java.util.ArrayList;
import java.util.Calendar;


public class teacher_attendanceSheet extends AppCompatActivity {
    private static final String TAG = "teacher_attendanceSheet";
    ListView listView;
    String teacher_id,class_selected;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;
    String required_date;
    private Toolbar mToolbar;
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
        setContentView(R.layout.activity_teacher_attendance_sheet);
        Log.i(TAG,":onCreate");
        listView = findViewById(R.id.list);
        dateView =  findViewById(R.id.tvDateN);
        mToolbar=findViewById(R.id.ftoolbar);
//        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Previous Record");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");
        picDate();
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
                DatePickerDialog dialog = new DatePickerDialog(teacher_attendanceSheet.this, mDateSetListener, year, month, day);
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

    public void viewlist(View v) {

        Userlist.clear();
        dbStudent = ref.child("Student");
        dbStudent.orderByChild("sclasses").equalTo(class_selected).
            addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString());
//                    Log.i(TAG, String.valueOf(Userlist.isEmpty()));

                }
                display_list(Userlist);
//                Log.i(TAG, "tec$$$$$$$$"+String.valueOf(Userlist.toString()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void display_list(final ArrayList userlist) {

        Studentlist.clear();
        required_date = getDate;
        dbAttendance = FirebaseDatabase.getInstance().getReference()
            .child("attendance");
        Studentlist.add("      SID       "+"Status" + "   period");
        for (Object sid : userlist) {
//            Log.i(TAG,"requred date &&&&&&&&&&&&&"+required_date);
            //is sid returning stds ------------yes
            String stduid = class_selected+"_"+sid.toString();
//            Log.i(TAG,"is class selected &&&&&&&&&&&&&"+stduid);

//            addListenerForSingleValueEvent
            dbAttendance.child(required_date).child(stduid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                        String p1 = dsp.getValue().toString(); //marathi
//                        Log.i(TAG,"std#########"+ p1);
//is we are getting scrt val
//                        if((p1.equals("A / "+teacher_id))||(p1.equals("P / "+teacher_id))){
                            Studentlist.add(dataSnapshot.getKey().toString() + "            " + p1.substring(0,1) +"        "+dsp.getKey());
//                            Log.i(TAG,"is reach &&&&&&&&&"+p1+"SETEDDDDDDDDDDDDDDDDDDDDDDDDDD");

//                        }
                    }
                    list(Studentlist);
                    Log.i(TAG, String.valueOf(Studentlist.toString()));

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }

            });
        }
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

}
