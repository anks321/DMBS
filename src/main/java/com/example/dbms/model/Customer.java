package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
	private String username;
	private String password;
	private String passwordConfirm;
	private String oldPassword;
	private String role;
    private String token;
	private int active;
    private int cid;
    private int balance;
    private int pin;
    private String phone_no;
    private String c_aadhar_number;
    private String account_no;
    private String sex;
    private String ifsc;
    private String dob;
    private String first_name;
    private String last_name;
    private String email;
    private String city;
    private String street;
}
