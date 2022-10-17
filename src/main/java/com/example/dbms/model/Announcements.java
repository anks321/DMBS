package com.example.dbms.model;
import java.util.Date;

public class Announcements {
    private int A_id;
    private String text;
    private String date_and_time;
    public int getA_id() {
        return A_id;
    }
    public void setA_id(int a_id) {
        A_id = a_id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getDate_and_time() {
        return date_and_time;
    }
    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }
}
