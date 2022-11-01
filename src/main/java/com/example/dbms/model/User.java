package com.example.dbms.model;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private int userID;
	private String username;
	private String password;
	private String passwordConfirm;
	private String oldPassword;
	private String photo;
	private String birthDate;
	private String gender;
	private String adhaarNumber;
	private String emailID;
	private String firstName;
	private String lastName;
	private String middleName;
	private String street;
	private String city;
	private String state;
	private String country;
	private String phone;
	
}

