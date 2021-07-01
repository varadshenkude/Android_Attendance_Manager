package com.swarajya.schoolAttendance.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.FirebaseDatabase;
import com.swarajya.schoolAttendance.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class takeNActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

  EditText notes;
  Button btn ;
  String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

  String classes,tid,item;
  private boolean isitemselected;
  private Toolbar mToolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    tid = getIntent().getStringExtra("tid");

    btn = findViewById(R.id.save);
    mToolbar=(Toolbar)findViewById(R.id.takeNote);
    mToolbar.setTitle(tid+"'s Dashboard"+"("+date+")");
    init();
    notes = findViewById(R.id.writenote);
    classes = getIntent().getStringExtra("class");


    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        saveN(view);
      }
    });

  }
  private void init() {

    Spinner spinner2 = (Spinner) findViewById(R.id.spinn);
    isitemselected = false;


    spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
        R.array.periods, android.R.layout.simple_spinner_item);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner2.setAdapter(dataAdapter);
  }
  public void saveN(View view){
    if (!notes.getText().toString().isEmpty()&&isitemselected){
      if (item.isEmpty()||item==null){
        Toast.makeText(getApplicationContext(), "Please Select the subject: " + item, Toast.LENGTH_LONG).show();

      }else {
        String takenNote = notes.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("notes").
            child(date + "_" + classes).child(item).setValue(takenNote + "_" + tid);
        Toast.makeText(getApplicationContext(), "Notes Saved Successfully !!"  , Toast.LENGTH_LONG).show();

      }
    }
  }


  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    item = parent.getItemAtPosition(position).toString();
    if (!item.equals("select Subject")) {
      isitemselected = true;
    }
//    else if (item.equals("select Subject")){
//      Toast.makeText(parent.getContext(), "Please Select the subject: " + item, Toast.LENGTH_LONG).show();
//
//    }
    else {
      Toast.makeText(parent.getContext(), "Please Select subject" + item, Toast.LENGTH_LONG).show();
    }
  }

  public void onNothingSelected(AdapterView<?> arg0) {
    Toast.makeText(getApplicationContext(), "Please Select subject" + item, Toast.LENGTH_LONG).show();

  }

 }
