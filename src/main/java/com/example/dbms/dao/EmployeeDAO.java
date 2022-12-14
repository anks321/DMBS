package com.example.dbms.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
// import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.*;

@Lazy
@Repository
public class EmployeeDAO {
    @Autowired
	private JdbcTemplate temp;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Employee employee) {

		employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
		String sql = "insert into employees(username,password,role,token,active,eid,salary,age,phone_no,pin,dob,ifsc,account_no,e_aadhar_number,first_name,last_name,Designation,email,city,street,mess_id,section_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		temp.update(sql, employee.getUsername(), employee.getPassword(), employee.getRole(), employee.getToken(), employee.getActive(),
				employee.getEid(), employee.getSalary(), employee.getAge(), employee.getPin(), employee.getDob(),
				employee.getIfsc(), employee.getAccount_no(), employee.getE_aadhar_number(), employee.getFirst_name(), employee.getLast_name(),
				employee.getDesignation(),employee.getEmail(),employee.getCity(),employee.getStreet(),employee.getMess_id(),employee.getSection_id());

	}
 
	public void update(int eid, int salary, int age, int phone_no, int pin, Date dob, String ifsc, String account_no, String e_aadhar_number, String first_name, String last_name, String Designation, String email, String city, String street, int mess_id, int section_id, String username) {

		String sql = "update employees set eid = ?,salary = ?,age = ?,phone_no = ?,pin = ?,dob = ?,ifsc = ?,account_no = ?,e_aadhar_number = ?,first_name = ?,last_name = ?,Designation = ?,email = ?,city = ?,street = ?,mess_id = ?,section_id = ? where username = ?";
		temp.update(sql, eid, salary, age, phone_no, pin, dob, ifsc, account_no, e_aadhar_number, first_name, last_name, Designation, email, city, street, mess_id, section_id);
	} 

	public void delete(String username) {

		String sql = "delete from employees where username = ?";
		temp.update(sql, username);
	}

	public void updateActivity(String username,int active) {

		String sql = "update employees set active = ? where username = ?";
		temp.update(sql, active, username);
	}

	public Employee findByUsername(String username) {
		String sql = "select * from employees where username='" + username + "'";
		try {
			return temp.queryForObject(sql, new RowMapper<Employee>() {
				public Employee mapRow(ResultSet row, int rowNum) throws SQLException {
					Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row, rowNum);
					return employee;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// public Employee findByID(int userID) {
	// 	String sql = "select * from employees where userID = ?";
	// 	return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), userID);

	// }

	// public void updateProfile(String username, String filename) {
	// 	String query = "update employees set photo = ? where username = ?";
	// 	temp.update(query, filename, username);
	// }

	public boolean userExists(String username) {

		String sql = "select count(*) from employees where username='" + username + "'";

		int found = temp.queryForObject(sql, Integer.class);

		if (found == 1)
			return true;
		else
			return false;
	}

	// public boolean updatePassword(String username,String oldPassword,String oldPasswordEntered, String newPassword) {
		
	// 	if(bCryptPasswordEncoder.matches(oldPasswordEntered,oldPassword)) {
	// 		String sql="update employees set password=? where username=?;";
	// 		String encodedNewPassword=bCryptPasswordEncoder.encode(newPassword);
	// 		temp.update(sql,encodedNewPassword,username);
	// 		return true;
	// 	}
	// 	else {
	// 		return false;
	// 	}	
	// }

	public List<Employee> allEmployees() {

		String sql = "select * from employee;";

		return temp.query(sql, new BeanPropertyRowMapper<>(Employee.class));
	}

	public Employee findByConfirmationToken(String token) {
        String sql = "select * from employees where token='" + token + "'";
        try{
        	return temp.queryForObject(sql, new RowMapper<Employee>() {
                public Employee mapRow(ResultSet row, int rowNum) throws SQLException {                	
                	Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
                	return employee;
                }
            });        
        }catch(EmptyResultDataAccessException e){
        	return null;
        }         
    }

	public void update(int eid, int salary, int age, int pin, java.util.Date dob, String ifsc, String account_no,
			String e_aadhar_number, String first_name, String last_name, String designation, String email, String city,
			String street, int mess_id, int section_id, String username) {
	}

}
