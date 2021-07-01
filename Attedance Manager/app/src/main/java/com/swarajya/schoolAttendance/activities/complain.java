package com.swarajya.schoolAttendance.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.swarajya.schoolAttendance.R;

public class complain extends AppCompatActivity {

    EditText comp;
    String var;
    DatabaseReference ref;
    String n;
    Button sc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_complain );
        n = getIntent().getStringExtra("uid");
        comp = findViewById(R.id.complain);
      ref = FirebaseDatabase.getInstance().getReference("complainbox");
      sc = findViewById(R.id.sc);
      sc.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            var = comp.getText().toString();

            ref.child(n).setValue(var);
//            Log.i("Complain",n+"  "+var);
            Toast.makeText(getApplicationContext(), "Complain send to admin successfully", Toast.LENGTH_SHORT).show();

          }
      } );


    }
}
