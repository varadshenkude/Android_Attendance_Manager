package com.swarajya.schoolAttendance.models;


public class Student {



  private String smothername;
  private String sgender;
  private String smobileno;
  private String saddresss;
  private String sdateofbirth;
  private String semail;
  private String sname;
  private String sid;
  private String sclasses;
  private String spass;
  private String sphotourl;

  public String getStddivsid() {
    return stddivsid;
  }

  public void setStddivsid(String stddivsid) {
    this.stddivsid = stddivsid;
  }

  private String stddivsid;



  public String getSmothername() {
    return smothername;
  }

  public void setSmothername(String smothername) {
    this.smothername = smothername;
  }

  public String getSgender() {
    return sgender;
  }

  public void setSgender(String sgender) {
    this.sgender = sgender;
  }

  public String getSmobileno() {
    return smobileno;
  }

  public void setSmobileno(String smobileno) {
    this.smobileno = smobileno;
  }

  public String getSaddresss() {
    return saddresss;
  }

  public void setSaddresss(String saddresss) {
    this.saddresss = saddresss;
  }

  public String getSdateofbirth() {
    return sdateofbirth;
  }

  public void setSdateofbirth(String sdateofbirth) {
    this.sdateofbirth = sdateofbirth;
  }

  public String getSemail() {
    return semail;
  }

  public void setSemail(String semail) {
    this.semail = semail;
  }

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

  public String getSclasses() {
    return sclasses;
  }

  public void setSclasses(String sclasses) {
    this.sclasses = sclasses;
  }

  public String getSpass() {
    return spass;
  }

  public void setSpass(String spass) {
    this.spass = spass;
  }

  public String getSphotourl() {
    return sphotourl;
  }

  public void setSphotourl(String sphotourl) {
    this.sphotourl = sphotourl;
  }

  public Student(String sid, String stdName, String mothername
      , String addresss, String Standard, String gender
      , String mobileno,
                 String dateofbirth, String email, String pass, String photourl) {
    this.sid = sid;
    this.sname=stdName;
    this.smothername=mothername;
    this.sgender = gender;
    this.smobileno=mobileno;
    this.saddresss=addresss;
    this.sdateofbirth= dateofbirth;
    this.sclasses= Standard;
    this.spass = pass;
    this.semail = email;
    this.sphotourl = photourl;
//    this.stddivsid = Standard+"_"+sid;

  }
  public Student(){

  }



}
