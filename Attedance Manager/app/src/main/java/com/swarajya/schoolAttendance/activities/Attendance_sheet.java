package com.swarajya.schoolAttendance.activities;


public class Attendance_sheet {
    String Marathi,English,Hindi,Science,History,Mathematics,Geography,Sanskrit;

    public Attendance_sheet(String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8) {
        this.Marathi=p1;
        this.English=p2;
        this.Hindi=p3;
        this.Science=p4;
        this.History=p5;
        this.Mathematics=p6;
        this.Geography=p7;
        this.Sanskrit=p8;

    }

    public String getP1() {
        return Marathi;
    }
    public String getP2() {
        return English;
    }
    public String getP3() {
        return Hindi;
    }
    public String getP4() {
        return Science;
    }
    public String getP5() { return History; }
    public String getP6() {
        return Mathematics;
    }
    public String getP7() {
        return Geography;
    }
    public String getP8() {
        return Sanskrit;
    }
}
