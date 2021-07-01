package com.swarajya.schoolAttendance.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyCourseAtt {

    private String courseName;
    private ArrayList<String> presentStudents = new ArrayList<String>();
    private ArrayList<String> absentStudents = new ArrayList<String>();
    private ArrayList<String> leaveStudents = new ArrayList<String>();
    private String currentDate;

    public ArrayList<String> getLeaveStudents() {
        return leaveStudents;
    }

    public void setLeaveStudents(ArrayList<String> leaveStudents) {
        this.leaveStudents = leaveStudents;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    //Empty Constructor

    public MyCourseAtt(String courseName, ArrayList<String> presentStudents, ArrayList<String> absentStudents, ArrayList<String> leaveStudents, String currentDate) {
        this.courseName = courseName;
        this.presentStudents = presentStudents;
        this.absentStudents = absentStudents;
        this.leaveStudents = leaveStudents;
        this.currentDate = currentDate;
    }

    public MyCourseAtt(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String strDate = dateFormat.format(date).toString();
        this.currentDate = strDate;
    }

    //Getters and Setters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<String> getPresentStudents() {
        return presentStudents;
    }

    public void setPresentStudents(ArrayList<String> presentStudents) {
        this.presentStudents = presentStudents;
    }

    public ArrayList<String> getAbsentStudents() {
        return absentStudents;
    }

    public void setAbsentStudents(ArrayList<String> absentStudents) {
        this.absentStudents = absentStudents;
    }
}
