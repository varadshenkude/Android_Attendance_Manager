package com.swarajya.schoolAttendance.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
  private static final String TAG = "ViewNoteActivity";
  private boolean isitemselected;
  private String item;
  private TextView tvNotes;
  private Calendar calendar;
  private TextView dateView;
  private int year,month,day;
  private int byear,bmonth,bday;
  private Boolean setDate=Boolean.FALSE;
  private String getDate;
  private DatePickerDialog.OnDateSetListener mDateSetListener;
  private Button btnGetNote;
  private String sid,stddiv;
  private Toolbar mToolbar;
  String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());



  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_notes);
    tvNotes = findViewById(R.id.tvNotes);
    tvNotes.setVisibility(View.INVISIBLE);
    btnGetNote = findViewById(R.id.getNotes);
    sid = getIntent().getStringExtra("uid");
    stddiv =sid.substring(0,sid.lastIndexOf("_"));
    mToolbar=(Toolbar)findViewById(R.id.ViewNote);
    mToolbar.setTitle(sid.substring(sid.lastIndexOf("_")+1)+"'s Dashboard"+"("+date+")");
    btnGetNote.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isitemselected&&getDate!="null") {
          getNotes();
        }else {
                      Toast.makeText(getApplicationContext(), "Please Select Date To get Notes " , Toast.LENGTH_LONG).show();

        }
      }
    });
    init();


  }

  private void getNotes() {
    try{
    Log.i("ViewNoteACtivity","stddiv "+stddiv+"sid "+sid);
    if (isitemselected){
      if (item.isEmpty()||item==null){
        Toast.makeText(getApplicationContext(), "Please Select the subject: " + item, Toast.LENGTH_LONG).show();

      }else {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notes").
            child(getDate + "_" + stddiv).child(item);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            String notes = snapshot.getValue(String.class);
            if(notes.isEmpty()){
              tvNotes.setVisibility(View.VISIBLE);

              tvNotes.setText("Notes Not Found !!");

            }

            Log.i("ViewNoteACtivity",notes);
            tvNotes.setVisibility(View.VISIBLE);

            tvNotes.setText(notes);

//            Toast.makeText(getApplicationContext(), "Notes Loaded Successfully " , Toast.LENGTH_LONG).show();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.i("ViewNoteACtivity",error.getDetails());
            Toast.makeText(getApplicationContext(), "Notes Not Found !!"  , Toast.LENGTH_LONG).show();


          }
        });

        Toast.makeText(getApplicationContext(), "Notes Retrived Successfully !!"  , Toast.LENGTH_LONG).show();

      }
    }
  }catch (Exception e){
    Log.i(TAG,e.getMessage());
    }
  }

  private void init() {
    picDate();
    Spinner spinner2 = (Spinner) findViewById(R.id.spinn);
    isitemselected = false;


    spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
        R.array.periods, android.R.layout.simple_spinner_item);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner2.setAdapter(dataAdapter);
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    item = parent.getItemAtPosition(position).toString();
    if (!item.isEmpty()) {
      isitemselected = true;
    }
    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

  }

  public void onNothingSelected(AdapterView<?> arg0) {
    Toast.makeText(getApplicationContext(), "Please Select subject" + item, Toast.LENGTH_LONG).show();

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
        DatePickerDialog dialog = new DatePickerDialog(ViewNoteActivity.this, mDateSetListener, year, month, day);
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
