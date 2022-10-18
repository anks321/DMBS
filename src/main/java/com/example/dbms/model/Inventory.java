package com.example.dbms.model;
import java.util.Date;

public class Inventory {
    private int item_id;
    private Date date;
    private int quantity;
    private Date expiry_date;
    private String name;
    public int getItem_id() {
        return item_id;
    }
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Date getExpiry_date() {
        return expiry_date;
    }
    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
