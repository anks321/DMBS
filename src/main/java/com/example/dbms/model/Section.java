package com.example.dbms.model;
import java.util.Date;

public class Section {
    private int section_id;
    private int hall_no;
    private String breakfast;
    private String lunch;
    private String dinner;
    public int getSection_id() {
        return section_id;
    }
    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }
    public int getHall_no() {
        return hall_no;
    }
    public void setHall_no(int hall_no) {
        this.hall_no = hall_no;
    }
    public String getBreakfast() {
        return breakfast;
    }
    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }
    public String getLunch() {
        return lunch;
    }
    public void setLunch(String lunch) {
        this.lunch = lunch;
    }
    public String getDinner() {
        return dinner;
    }
    public void setDinner(String dinner) {
        this.dinner = dinner;
    }
   
}
