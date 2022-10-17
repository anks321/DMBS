package com.example.dbms.model;
import java.util.Date;

public class Forum {
    private int C_id;
    private int roll_no;
    private int resolved;
    private Date expiry_date;
    private String complaint;
    private String date_time;
    public int getResolved() {
        return resolved;
    }
    public void setResolved(int resolved) {
        this.resolved = resolved;
    }
    public String getDate_time() {
        return date_time;
    }
    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
    public int getC_id() {
        return C_id;
    }
    public void setC_id(int c_id) {
        C_id = c_id;
    }
    public int getRoll_no() {
        return roll_no;
    }
    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }
    public Date getExpiry_date() {
        return expiry_date;
    }
    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }
    public String getComplaint() {
        return complaint;
    }
    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
