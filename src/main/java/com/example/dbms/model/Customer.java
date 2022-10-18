package com.example.dbms.model;

import java.util.Date;

public class Customer {
    private int cid;
    private int balance;
    private int pin;
    private int phone_no;
    private String c_aadhar_number;
    private String account_no;
    private String sex;
    private String ifsc;
    private String dob;
    private String first_name;
    private String last_name;
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getC_aadhar_number() {
        return c_aadhar_number;
    }
    public void setC_aadhar_number(String c_aadhar_number) {
        this.c_aadhar_number = c_aadhar_number;
    }
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getPin() {
        return pin;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }
    public int getPhone_no() {
        return phone_no;
    }
    public void setPhone_no(int phone_no) {
        this.phone_no = phone_no;
    }
    public String getAccount_no() {
        return account_no;
    }
    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getIfsc() {
        return ifsc;
    }
    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    private String email;
    private String city;
    private String street;
    
    
}
