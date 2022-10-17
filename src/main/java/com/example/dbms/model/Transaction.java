package com.example.dbms.model;
import java.util.Date;

public class Transaction {
    private int t_id;
    private int amount;
    private int type;
    private Date date;
    private String mode_of_payment;
    public int getT_id() {
        return t_id;
    }
    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getMode_of_payment() {
        return mode_of_payment;
    }
    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }
}
