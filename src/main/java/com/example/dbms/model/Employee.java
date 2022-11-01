package com.example.dbms.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
    private String username;
	private String password;
	private String passwordConfirm;
	private String oldPassword;
	private int eid; 
    private int salary;
    private int age;
    private int phone_no;
    private int pin;
    private Date dob;
    private String ifsc;
    private String account_no;
    private String e_aadhar_number;
    private String first_name;
    private String last_name;
    private String Designation;
    private String email;
    private String city;
    private String street;
    private int mess_id;
    private int section_id;
}