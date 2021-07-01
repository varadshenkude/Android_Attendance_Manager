package com.swarajya.schoolAttendance.activities;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.swarajya.schoolAttendance.R;
import com.swarajya.schoolAttendance.models.Student;
import com.swarajya.schoolAttendance.ui.login.LoginFormState;
import com.swarajya.schoolAttendance.ui.login.LoginResult;
import com.swarajya.schoolAttendance.ui.login.LoginViewModel;
import com.swarajya.schoolAttendance.ui.login.LoginViewModelFactory;

import java.util.Calendar;
import java.util.Objects;

//two values are null without updating saving prb spinnner class or date
public class UpdateStdActivity extends AppCompatActivity {
  private static final String TAG ="UpdateStdActivity" ;
  private static  String UID  ;
  EditText Sname, Sid,spassword,motherNameEt,mNo,etEmail,etPass,etAdd;
  String sname,sid,classname,spass,Gender;
  private DatePickerDialog.OnDateSetListener mDateSetListener;
  private Calendar calendar;
  private TextView dateView;
  private int year,month,day;
  private String gender;
  private RadioButton rm,rf;
  private Boolean setDate,setGender,isclassset=Boolean.FALSE;
  private ImageView imageView ;
  private Spinner classes;
  private DatabaseReference databaseStudent;
  Toolbar mToolbar;
  private String DOB;
  private LoginViewModel loginViewModel;
  private FirebaseAuth mAuth;
  private FirebaseUser user;
  private Button btn;
  private String sclass;
  private String stddivsid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_addstudent);
    Log.i(TAG,"onCreate Started");

    databaseStudent = FirebaseDatabase.getInstance().getReference("Student");
    btn = findViewById(R.id.button9);
    Sname =  (EditText) findViewById(R.id.stdName);
    classes = (Spinner) findViewById(R.id.spinner3);
    mToolbar=(Toolbar)findViewById(R.id.ftoolbar);
    mNo= findViewById(R.id.mobileNo);
    motherNameEt= findViewById(R.id.motherName);
    etAdd = findViewById(R.id.etAdd);
    etEmail =findViewById(R.id.email);
    etPass =findViewById(R.id.password);
//    etUserId =findViewById(R.id.userId);//sid is rollno and userid is also roll no
    Sid = findViewById(R.id.userId);
    rf = findViewById(R.id.rbFe);
    rm = findViewById(R.id.rbM);
    dateView = findViewById(R.id.etDob);

    mToolbar.setTitle("Add/Remove Student");
//    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
        .get(LoginViewModel.class);
// Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();

    init();

    loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
      @Override
      public void onChanged(@Nullable LoginFormState loginFormState) {
        if (loginFormState == null) {
          return;
        }
//        loginButton.setEnabled(loginFormState.isDataValid());
        if (loginFormState.getUsernameError() != null) {
          etEmail.setError(getString(loginFormState.getUsernameError()));
        }
        if (loginFormState.getPasswordError() != null) {
          etPass.setError(getString(loginFormState.getPasswordError()));
        }
      }
    });
    loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
      @Override
      public void onChanged(@Nullable LoginResult loginResult) {
        if (loginResult == null) {
          return;
        }
//        loadingProgressBar.setVisibility(View.GONE);
        if (loginResult.getError() != null) {
          showLoginFailed(loginResult.getError());
        }
//        if (loginResult.getSuccess() != null) {
//          updateUiWithUser(loginResult.getSuccess());
//        }
//        setResult(Activity.RESULT_OK);

        finish();
      }
    });

    TextWatcher afterTextChangedListener = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // ignore
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // ignore
      }

      @Override
      public void afterTextChanged(Editable s) {
        loginViewModel.loginDataChanged(etEmail.getText().toString(),etEmail.getText().toString());
      }
    };
    etEmail.addTextChangedListener(afterTextChangedListener);
    etPass.addTextChangedListener(afterTextChangedListener);
    etPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          loginViewModel.login(etEmail.getText().toString(),
              etPass.getText().toString());
        }
        return false;
      }
    });
    picDate();

  }


  private void showLoginFailed(@StringRes Integer errorString) {
    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
  }

  private void init() {

//    String sname = getIntent().getStringExtra("");
    String sname =getIntent().getStringExtra("sname");
    String sid =getIntent().getStringExtra("sid");
    String semail = getIntent().getStringExtra("semail");
    String smobile = getIntent().getStringExtra("smobile");
    String smother = getIntent().getStringExtra("smother");
    String sgender = getIntent().getStringExtra("sgender");
     sclass = getIntent().getStringExtra("sclass");
    String sdateofbith = getIntent().getStringExtra("sdateofbith");
    String saddress = getIntent().getStringExtra("saddress");
    String imgUri = getIntent().getStringExtra("imgUri");
    String spass = getIntent().getStringExtra("spass");
    Sname.setText(sname);
    etAdd.setText(saddress);
    etEmail.setText(semail);
    motherNameEt.setText(smother);
    Sid.setText(sid);
    etPass.setText(spass);
    mNo.setText(smobile);
    dateView.setText(sdateofbith);
    DOB = sdateofbith;

    setDate = Boolean.TRUE;
    gender= sgender;
//    classname = sclass;
//    isclassset =Boolean.TRUE;


    if(gender.equals("Female")){
      rf.setChecked(true);
      setGender=true;

    }
    else if (Objects.equals(gender, "Male")){
      rm.setChecked(true);
      setGender=true;

    }
  }

  public void addStudent(View view) {
    try{

    if( TextUtils.isEmpty(Sname.getText())){
      Toast.makeText(getApplicationContext(),"Student Full name is required!",Toast.LENGTH_SHORT).show();
      Sname.setError( "Student Full name is required!" );

    }else if(TextUtils.isEmpty(motherNameEt.getText())) {
      Toast.makeText(getApplicationContext(), "Mother name is required!", Toast.LENGTH_SHORT).show();
      motherNameEt.setError("Mother name is required!");
    }
    else if(TextUtils.isEmpty(Sid.getText()))
    {
      Toast.makeText(getApplicationContext(),"ID  is required!",Toast.LENGTH_SHORT).show();
      Sid.setError( "ID is required!" );
    }else if(TextUtils.isEmpty(etAdd.getText())){
      Toast.makeText(getApplicationContext(),"Address is required!",Toast.LENGTH_SHORT).show();
      etAdd.setError( "Address is required!" );
    }else if(TextUtils.isEmpty(mNo.getText())){
      Toast.makeText(getApplicationContext(),"Mobile Number is required!",Toast.LENGTH_SHORT).show();
      mNo.setError( "Mobile Number is required!" );
    }else if(TextUtils.isEmpty(etEmail.getText())){
      Toast.makeText(getApplicationContext(),"Email is required!",Toast.LENGTH_SHORT).show();
      etEmail.setError( "Email is required!" );
    }
    else if(TextUtils.isEmpty(etPass.getText())){
      Toast.makeText(getApplicationContext(),"Password is required!",Toast.LENGTH_SHORT).show();
      etPass.setError( "Password is required!" );
    }
//    else if(TextUtils.isEmpty(rNo.getText())){
//      Toast.makeText(getApplicationContext(), "Roll Number is required!",Toast.LENGTH_SHORT).show();
//      rNo.setError( "Roll Number is required!" );}
    else if(classes.getSelectedItem().equals("null")){
      Toast.makeText(getApplicationContext(), "Class Name is required!",Toast.LENGTH_SHORT).show();
//      classes.setError( "Class Number is required!" );
    }else if(setDate!=Boolean.TRUE ){
      Toast.makeText(getApplicationContext(),"Date is required!",Toast.LENGTH_SHORT).show();
      dateView.setError( "Date is required!" );
    }else if(setGender!=Boolean.TRUE ) {
      Toast.makeText(getApplicationContext(), "Gender is required!", Toast.LENGTH_SHORT).show();
//          rg.setError( "Gender is required!" );
    }

    else {
      //String id = databaseStudent.push().getKey();
      sname = Sname.getText().toString();


      if (classes.isSelected()){
        classname = classes.getSelectedItem().toString();

      }else{
        classname =sclass;}
//      spass = spassword.getText().toString();

      String MotherN,MobileN,Dob,email,pass,Add;
      MotherN = motherNameEt.getText().toString();
//      RollN = rNo.getText().toString();
      email = etEmail.getText().toString();
      spass = etPass.getText().toString();
      MobileN = mNo.getText().toString();
      Add= etAdd.getText().toString();
      Dob = DOB;
      setDate = Boolean.TRUE;
      sid = Sid.getText().toString();


      String imgUri ;
//      PreStdAct preStdAct = new PreStdAct();
      imgUri = String.valueOf(PreUpStdAct.uris);
      String photo = imgUri;
      Log.i("Value ",sid+sname.trim()+MotherN.trim()+Add.trim()+classname.trim()+
          gender.trim()+MobileN.trim()+Dob.trim()+ email.trim()+ spass.trim()+"\n IMP"+imgUri);
      //this.Gender+ this is null exception
      Student student = new Student(sid,sname.trim(),MotherN.trim(),Add.trim(),
          classname.trim(), gender.trim(), MobileN.trim(),Dob.trim(), email.trim(), spass.trim(),photo);
       stddivsid = classname + "_" + sid;

      databaseStudent.child(stddivsid).setValue(student);
//      createAccount(email,spass);
      Toast.makeText(getApplicationContext(), "student added successfully", Toast.LENGTH_LONG).show();
      finish();

    }
  } catch (Exception e) {
    e.printStackTrace();
  }
  }
  public void removeStudent(View v){
    if (!TextUtils.isEmpty(Sid.getText().toString())) {
      sid = Sid.getText().toString();
      databaseStudent.child(stddivsid).setValue(null);
      Toast.makeText(getApplicationContext(),"teacher removed successfully", Toast.LENGTH_LONG).show();

    }else {
      Toast.makeText(getApplicationContext(),"id cannot be empty", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }

  public void onRadioButtonClicked(View view) {
    boolean checked = ((RadioButton) view).isChecked();
    switch(view.getId()) {
      case R.id.rbFe:
        if (checked)
          gender = "Female";
        this.gender = "Female";

        setGender = Boolean.TRUE;
        break;
      case R.id.rbM:
        if (checked)
          gender = "Male";
        this.gender = "Male";
        setGender = Boolean.TRUE;
        break;
    }
  }

  private void picDate() {
    dateView = findViewById(R.id.etDob);
    dateView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(UpdateStdActivity.this, mDateSetListener, year, month, day);
        dialog.show();
      }
    });
    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month += 1;
        String date = "dd/mm/yyyy:" + day + "/" + month + "/" + year;
        Log.i(TAG, date);
        dateView.setText(date);
        setDate = Boolean.TRUE;
        DOB = day + "/" + month + "/" + year;
      }

    };
  }
  public void createAccount(@NonNull final String email, @NonNull final String password ) {
    Log.i(TAG, "createAccount:" + email +password);

    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              Log.i(TAG, "createUserWithEmailAndPassword:success");
              user = mAuth.getCurrentUser();//            updateUI(user);
              Log.i(TAG, "Firebase details"+user.getEmail()+user.getUid());
              Log.i("signUP","######in success ######");
              UID = task.getResult().getUser().getUid();
              Log.i("signUP","#####PAss"+password);
              Log.i("signUP","#####EMAIL"+email);
            } else {
              Log.w(TAG, "createUserWithEmail:failure", task.getException());
              Log.w(TAG, "The email address is already in use by another account");
              Toast.makeText(UpdateStdActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            }
          }
        });
    // [END create_user_with_email]
  }


}
