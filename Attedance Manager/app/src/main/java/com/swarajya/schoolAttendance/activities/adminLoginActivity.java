package com.swarajya.schoolAttendance.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;

public class adminLoginActivity extends AppCompatActivity {
    private static final String TAG = "adminLoginActivity";
    Button login;
    DatabaseReference ref;
    String userid,pass;
    String dbpassword;
    Bundle basket;
    EditText username,password;
    ProgressDialog mDialog;
    private FirebaseAuth mAuth;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login);
        username = findViewById(R.id.adminEditText);
        password = findViewById(R.id.adminPasswordEditText);

        intent = new Intent(adminLoginActivity.this, adminlogin.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = username.getText().toString();
                pass = password.getText().toString();
                mDialog=new ProgressDialog(adminLoginActivity.this);
                mDialog.setMessage("Please Wait..."+userid);
                mDialog.setTitle("Loading");
                mDialog.show();
                basket = new Bundle();
                basket.putString("message", userid);
//                oldWay();
                newWay(userid,pass);

            }
        });
    }

    private void oldWay() {


        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dbuser = ref.child("Admin");


        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDialog.dismiss();
                dbpassword = dataSnapshot.child("Admin").getValue(String.class);
                verify(dbpassword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "database error", Toast.LENGTH_LONG).show();
            }
        });
    }




    public void verify(String dbpassword) {
        if(userid.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Username cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if (userid.equals("Admin") && pass.equalsIgnoreCase(dbpassword) ) {
            //  if (userid.equalsIgnoreCase("admin") && pass.equals("admin")) {
            mDialog.dismiss();
            intent.putExtras(basket);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
            finish();
            //  }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Enter valid user id or password", Toast.LENGTH_LONG).show();
        }
    }


    private void newWay(final String email, final String password) {
        if(email.isEmpty()||password.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Username Or Password cannot be empty", Toast.LENGTH_LONG).show();
        }
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        mDialog.dismiss();

                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        intent.putExtras(basket);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
//                        signUp(email, password);
                    }

                }
            });

        }
        private  void signUp(String email,String password){
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

        }
    }