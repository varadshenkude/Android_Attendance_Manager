package com.swarajya.schoolAttendance.ext;


public class Student {

    // Data Values.
    private String sname;
    private String sid;
    private String semail;
    private String attendance;

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    // Getter Setters.
//    public String getName() {
//        return sname;
//    }
//
//    public void setName(String sname) {
//        this.sname = sname;
//    }
//
//    public String getRollNo() {
//        return sid;
//    }
//
//    public void setRollNo(String sid) {
//        this.sid = sid;
//    }

//    public String getEmail() {
//        return semail;
//    }
//
//    public void setEmail(String semail) {
//        this.semail = semail;
//    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    // Constructors.
    public Student(){
        attendance = "p";
    }

    public Student(String sname, String sid, String semail,String attendance) {
        this.sname = sname;
        this.sid = sid;
        this.semail = semail;
        this.attendance =attendance ;
    }
}
