package com.example.dbms.model;

//import java.util.Date;
import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private String username;
    private String password;
	private String passwordConfirm;
	private String oldPassword;
	private String role;
    private String token;
	private int active;
    private int roll_no;
    private int room_no;
    private int Age;
    private int Balance;
    private Date DOB;
    private String f_name;
    private String l_name;
    private String hostel_name;
    private String sex;
    private String parent;
    private String phone_no;
    private String s_email;
    private String localGaurdian;
    private String aadhar_no;
    private String s_account_no;
    private String s_ifsc;
    
}
