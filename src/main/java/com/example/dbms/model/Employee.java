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
    private String role;
    private String token;
    private Integer active;
    private Integer eid;
    private Integer salary;
    private Integer age;
    private Integer phone_no;
    private Integer pin;
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
    private Integer mess_id;
    private Integer section_id;
}