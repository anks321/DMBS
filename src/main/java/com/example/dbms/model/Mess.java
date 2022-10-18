package com.example.dbms.model;
import java.util.Date;

public class Mess {
    private int mess_id;
    private int phone_no;
    private int mess_no;
    private String m_name;
    private String hostel_name;
    public String getM_name() {
        return m_name;
    }
    public void setM_name(String m_name) {
        this.m_name = m_name;
    }
    public int getMess_id() {
        return mess_id;
    }
    public void setMess_id(int mess_id) {
        this.mess_id = mess_id;
    }
    public int getPhone_no() {
        return phone_no;
    }
    public void setPhone_no(int phone_no) {
        this.phone_no = phone_no;
    }
    public int getMess_no() {
        return mess_no;
    }
    public void setMess_no(int mess_no) {
        this.mess_no = mess_no;
    }
    public String getHostel_name() {
        return hostel_name;
    }
    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }
}
