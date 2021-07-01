package com.swarajya.schoolAttendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.RSRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.swarajya.schoolAttendance.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class studentlogin extends AppCompatActivity {
    private static final String TAG ="studentlogin" ;
    String message;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    Toolbar mToolbar;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Teacher");
    private static long back_pressed;
    private String stddivsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        stddivsid = bundle.getString("stddivsid");
        mToolbar=(Toolbar)findViewById(R.id.ftoolbar);
        mToolbar.setTitle(message+"'s Dashboard"+"("+date+")");
        TextView txtView = (TextView) findViewById(R.id.textView1);
        Log.i(TAG,"onCreate stared stddivsid :"+stddivsid);


        txtView.setText("Welcome :"+message);

    }
    public void viewAttendance(View v){
        Bundle basket = new Bundle();
        basket.putString("sid", stddivsid);
//        basket.putString("classes", );
        Intent intent = new Intent(this, studentAttendanceActivity.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void logoutStudent(View view) {
        Intent logoutStudent=new Intent(studentlogin.this,DashboardActivity.class);
        logoutStudent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutStudent);
    }

    public void viewProfile(View view){

        Intent intent = new Intent(getApplicationContext(), SearchRActivity.class);
            intent.putExtra("uid",stddivsid);
        startActivity(intent);
    }
    public void viewNote(View view){
        Intent intent = new Intent(getApplicationContext(), ViewNoteActivity.class);
        intent.putExtra("uid",stddivsid);
        startActivity(intent);


    }
    public void complainBox(View view){
        Intent intent = new Intent(getApplicationContext(), complain.class);
        intent.putExtra("uid",stddivsid);
        startActivity(intent);

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
