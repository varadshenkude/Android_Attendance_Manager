package com.swarajya.schoolAttendance.activities;
//I am able to retrive data from firabase and also have stored

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.swarajya.schoolAttendance.R;


public class ViewStdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final String TAG = "ViewStdActivity";
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_std);
        init();

    }

    private void init() {
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner5);
        spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
            R.array.classes, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
          Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ViewStdActivity.this,ViewClass.class);
        intent.putExtra("class_selected", item);
        startActivity(intent);
        finish();

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getApplicationContext(), "Please Select Class", Toast.LENGTH_LONG).show();

    }

}
