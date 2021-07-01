package com.swarajya.schoolAttendance.activities;


import android.app.Activity;
import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swarajya.schoolAttendance.R;
import com.swarajya.schoolAttendance.ext.CourseAttendance;
import com.swarajya.schoolAttendance.ext.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyAttList extends ArrayAdapter<Student> {

  private Activity context;
  private List<Student> students;
  private String teacher_id;
  private String class_selected;
  private ArrayList<Student> arrayListStudents;
//  private ArrayList<String> listClassStudents;
  String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
  private static final  String TAG  ="MyAttList";

  public MyAttList(Context context, int resource) {
    super(context, resource);
  }

  @NonNull
  @Override
  public Activity getContext() {
    return context;
  }

  public void setContext(Activity context) {
    this.context = context;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  // Constructor.
  public MyAttList(Activity context, List<Student> students,String teacher_id,String class_selected){
    super(context,R.layout.custom_item,students);
    this.context = context;
    this.students = students;
    this.teacher_id = teacher_id;
    this.class_selected = class_selected;
//    this.periodno = periodno;
    this.arrayListStudents = new ArrayList<Student>();
    this.arrayListStudents.addAll(this.students);
    Log.i(TAG,"onCreate ");

//    for (Student tt:students){
//
//    this.listClassStudents.add(tt.getSid());
//     }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    // Inflator to inflate the custom view.
    LayoutInflater inflater = context.getLayoutInflater();

    //  CustomView to be inflated.
    final View listViewItem = inflater.inflate(R.layout.custom_item, null, true);

    // Grab the data.
    final TextView textName = (TextView) listViewItem.findViewById(R.id.cv_name);
    final TextView textRollNo = (TextView) listViewItem.findViewById(R.id.cv_rollNo);
    final RadioGroup rg = (RadioGroup)listViewItem.findViewById(R.id.rgAttendance);

    // Inflate the view.
    final Student std = students.get(position);
//    we are getting data of student which was clicked
    textName.setText(std.getSname());
    textRollNo.setText(std.getSid());
    Log.i(TAG,"onCreate name "+std.getSname());
    Log.i(TAG,"onCreate sid "+std.getSid());

    // Get attendance.
    String att = std.getAttendance();
    // Switch and mark attendance.
//    att was null as i ha messed up with studet classs
    switch(att) {
      case "p" :
        RadioButton rbP = rg.findViewById(R.id.rbPresent);
        Log.i(TAG," switch   case \"p\" :");

        // Set it true.
        rbP.setChecked(true);
        break;
      case "a" :
        RadioButton rbA = rg.findViewById(R.id.rbAbsent);
        // Set it true.
        Log.i(TAG," switch   case \"a\" :");

        rbA.setChecked(true);
        break;
      case "l" :
        RadioButton rbL = rg.findViewById(R.id.rbLeave);
        Log.i(TAG," switch   case \"l\" :");

        // Set it true.
        rbL.setChecked(true);
    }

    // Add Listener to radio button change.
    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        // Grab the value of radio button.
        final String value = ((RadioButton)listViewItem.findViewById(rg.getCheckedRadioButtonId()))
            .getText().toString();
        // When the attendance is changed.
        switch(value) {
          case "p" :
            RadioButton rbP = rg.findViewById(R.id.rbPresent);
            // Set it true.
            rbP.setChecked(true);
            // Change the attendance.
            std.setAttendance("p");
            Log.i(TAG," .OnCheckedChangeListener()  std.setAttendance(\"p\"); :");

            break;
          case "a" :
            RadioButton rbA = rg.findViewById(R.id.rbAbsent);
            // Set it true.
            rbA.setChecked(true);
            // Change the attendance.
            Log.i(TAG," .OnCheckedChangeListener()  std.setAttendance(\"a\"); :");

            std.setAttendance("a");
            break;
          case "l" :
            RadioButton rbL = rg.findViewById(R.id.rbLeave);
            // Set it true.
            rbL.setChecked(true);
            // Change the attendance.
            Log.i(TAG," .OnCheckedChangeListener()  std.setAttendance(\"l\"); :");

            std.setAttendance("l");
        }
      }
    });

    return listViewItem;
  }

  /**
   * Filters the list for the given query.
   *
   * @param charText search query — name.
   */
  public void filterByName(String charText) {
    Log.i(TAG,"filterByName");
    charText = charText.toLowerCase(Locale.getDefault());
    students.clear();
    if (charText.length() == 0) {
      students.addAll(arrayListStudents);
    } else {
      for (Student std : arrayListStudents) {
        if (std.getSname().toLowerCase(Locale.getDefault())
            .contains(charText)) {
          students.add(std);
        }
      }
    }
    notifyDataSetChanged();
  }

  /**
   * Filters the list for given roll no.
   *
   * @param charText search query — roll no.
   */
  public void filterByRollNo(String charText) {
    Log.i(TAG,"filterByRollNo");

    charText = charText.toLowerCase(Locale.getDefault());
    students.clear();
    if (charText.length() == 0) {
      students.addAll(arrayListStudents);
    } else {
      for (Student std : arrayListStudents) {
        if (std.getSid().toLowerCase(Locale.getDefault())
            .contains(charText)) {
          students.add(std);
        }
      }
    }
    notifyDataSetChanged();
  }

  /**
   * Saves the attendance to db.
   *
   */
  public void saveAttendance(String periodno){
    try {


    Log.i(TAG,"saveAttendance in myattlist");
    Log.i(TAG,"saveAttendance is arrayListStudents "+String.valueOf(arrayListStudents.isEmpty()));
    Log.i(TAG,"saveAttendance is Studemyts "+String.valueOf(students.isEmpty()));

    // Attendance Object.
//    CourseAttendance attendance = new CourseAttendance();
//    attendance.setCourseName(courseName);

    // Save to db.
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("attendance").child(date);
//    for (String rno :listClassStudents) {
//      dbRef.child(class_selected + "_" + rno).child(periodno).setValue(attendance + " / " + teacher_id);
//    }
    // Grab Attendance for individual student.
    for (Student temp : arrayListStudents)
    {

      switch (temp.getAttendance()) {
        case "p":
          // if this is present then set data directly from here
          dbRef.child(class_selected + "_" + temp.getSid()).child(periodno).setValue("P" + " / " + teacher_id);
          Log.i(TAG,"saved att for "+temp.getSid());
//          attendance.getPresentStudents().add(temp.getRollNo());// here they are adding data in
//          attendace. array of present students and value is student who is present

          break;
        case "a":
//          attendance.getAbsentStudents().add(temp.getRollNo());
          dbRef.child(class_selected + "_" + temp.getSid()).child(periodno).setValue("A" + " / " + teacher_id);
          Log.i(TAG,"saved att for A "+temp.getSid());

          break;
        case "l":
          dbRef.child(class_selected + "_" + temp.getSid()).child(periodno).setValue("L" + " / " + teacher_id);
          Log.i(TAG,"saved att for  L "+temp.getSid());

//          attendance.getLeaveStudents().add(temp.getRollNo());
          break;
      }
    }

    final DatabaseReference dr = FirebaseDatabase.getInstance().getReference()
        .child("count").child(class_selected).child(periodno);
    final DatabaseReference dk = FirebaseDatabase.getInstance().getReference()
        .child("count").child(class_selected).child(periodno);

//    final int[] count = {0};
     final Integer[] c = new Integer[1];
    try {
//we need to create count for every class

      dr.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
//          count[0] = Integer.getInteger(snapshot.getValue(String.class));
          c[0] = snapshot.getValue(Integer.class);
          Log.i("COunt", String.valueOf( "   " + c[0]));
//          Integer i = c[0]+1;
          dk.setValue((int)(snapshot.getValue(Integer.class)+1));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
      });
//      dr.setValue(count[0] + 1);



    }catch (Exception e){
      Log.i("error",e.getMessage());
    }



//    for (String item : selectedItems) {
      Toast.makeText(context, "Attendance created   and saved Successfully", Toast.LENGTH_SHORT).show();
      context.finish();
//      dk.updateChildren()
//    Map<String, Object> childUpdates = new HashMap<>();
//    childUpdates.put( periodno, i);
//
//    dk.updateChildren(childUpdates);
//    Log.i("COuntfbdbdrdsrdr", String.valueOf(i+ "   " + c[0]));

    }catch (Exception e){
      Log.i(TAG,e.getMessage());

    }
  }}
