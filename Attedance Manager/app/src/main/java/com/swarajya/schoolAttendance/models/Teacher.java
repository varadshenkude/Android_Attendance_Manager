package com.swarajya.schoolAttendance.models;


public class Teacher {



    private String tmothername;
    private String tgender;
    private String tmobileno;
    private String taddresss;
    private String tdateofbirth;
    private String temail;
    private String tname;
    private String tid;
    private String tclasses;
    private String tpass;
    private String tphotourl;

    public String getTmothername() {
        return tmothername;
    }

    public void setTmothername(String tmothername) {
        this.tmothername = tmothername;
    }

    public String getTgender() {
        return tgender;
    }

    public void setTgender(String tgender) {
        this.tgender = tgender;
    }


    public String getTmobileno() {
        return tmobileno;
    }

    public void setTmobileno(String tmobileno) {
        this.tmobileno = tmobileno;
    }

    public String getTaddresss() {
        return taddresss;
    }

    public void setTaddresss(String taddresss) {
        this.taddresss = taddresss;
    }

    public String getTdateofbirth() {
        return tdateofbirth;
    }

    public void setTdateofbirth(String tdateofbirth) {
        this.tdateofbirth = tdateofbirth;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTclasses() {
        return tclasses;
    }

    public void setTclasses(String tclasses) {
        this.tclasses = tclasses;
    }

    public String getTpass() {
        return tpass;
    }

    public void setTpass(String tpass) {
        this.tpass = tpass;
    }

    public String getTphotourl() {
        return tphotourl;
    }

    public void setTphotourl(String tphotourl) {
        this.tphotourl = tphotourl;
    }

    public Teacher(String tid, String stdName, String mothername
        , String addresss, String Standard, String gender
        , String mobileno,
                   String dateofbirth, String email, String pass, String photourl) {
        this.tid = tid;
        this.tname=stdName;
        this.tmothername=mothername;
        this.tgender = gender;
        this.tmobileno=mobileno;
        this.taddresss=addresss;
        this.tdateofbirth= dateofbirth;
        this.tclasses= Standard;
        this.tpass = pass;
        this.temail = email;
        this.tphotourl = photourl;


    }
    public Teacher(){

    }



}
