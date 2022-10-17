package com.example.dbms.model;
import java.util.Date;

public class Section {
    private int section_id;
    private int hall_no;
    private int breakfast;
    private int lunch;
    private int dinner;
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
    public int getBreakfast() {
        return breakfast;
    }
    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }
    public int getLunch() {
        return lunch;
    }
    public void setLunch(int lunch) {
        this.lunch = lunch;
    }
    public int getDinner() {
        return dinner;
    }
    public void setDinner(int dinner) {
        this.dinner = dinner;
    }
}
