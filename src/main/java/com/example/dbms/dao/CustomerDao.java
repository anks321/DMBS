package com.example.dbms.dao;

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

import com.example.dbms.model.Customer;

@Lazy
@Repository
public class CustomerDao {

	@Autowired
	private JdbcTemplate jt;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Customer customer) {

		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		// System.out.println(customer.getUsername());
		String sql = "insert into customer(username,password,role,token,active,cid,balance,pin,phone_no,c_aadhar_number,account_no,sex,ifsc,dob,first_name,last_name,email,city,street) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		jt.update(sql, customer.getUsername(), customer.getPassword(), customer.getRole(), customer.getToken(), customer.getActive(),
				customer.getCid(), customer.getBalance(), customer.getPin(), customer.getPhone_no(), customer.getC_aadhar_number(),
				customer.getAccount_no(), customer.getSex(), customer.getIfsc(), customer.getDob(), customer.getFirst_name(),
				customer.getLast_name(),customer.getEmail(),customer.getCity(),customer.getStreet());
		// System.out.println(customer.getUsername());

	}
 
	// public void update(String aadharNumber, String street, String city, String state, String country, String phone,
	// 		String username) {

	// 	String sql = "update customer set adhaarNumber = ?,street = ?,city = ?,state = ?,country = ?,phone = ? where username = ?";
	// 	jt.update(sql, aadharNumber, street, city, state, country, phone, username);
	// }


	public void updateActivity(String username,int active) {

		String sql = "update customer set active = ? where username = ?";
		jt.update(sql, active, username);
	}

	// public void delete(String username) {

	// 	String sql = "delete from customer where username = ?";
	// 	jt.update(sql, username);
	// }

	public Customer findByUsername(String username) {
		String sql = "select * from customer where username='" + username + "'";
		try {
			return jt.queryForObject(sql, new RowMapper<Customer>() {
				public Customer mapRow(ResultSet row, int rowNum) throws SQLException {
					Customer customer = (new BeanPropertyRowMapper<>(Customer.class)).mapRow(row, rowNum);
					return customer;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// public Customer findByID(int userID) {
	// 	String sql = "select * from customer where userID = ?";
	// 	return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Customer.class), userID);

	// }

	// public void updateProfile(String username, String filename) {
	// 	String query = "update customer set photo = ? where username = ?";
	// 	jt.update(query, filename, username);
	// }

	// public List<Customer> allusers() {

	// 	String sql = "select * from customer;";

	// 	return jt.query(sql, new BeanPropertyRowMapper<>(Customer.class));
	// }

	public boolean userExists(String username) {

		String sql = "select count(*) from customer where username='" + username + "'";

		int found = jt.queryForObject(sql, Integer.class);

		if (found == 1)
			return true;
		else
			return false;
	}

	// public boolean updatePassword(String username,String oldPassword,String oldPasswordEntered, String newPassword) {
		
	// 	if(bCryptPasswordEncoder.matches(oldPasswordEntered,oldPassword)) {
	// 		String sql="update customer set password=? where username=?;";
	// 		String encodedNewPassword=bCryptPasswordEncoder.encode(newPassword);
	// 		jt.update(sql,encodedNewPassword,username);
	// 		return true;
	// 	}
	// 	else {
	// 		return false;
	// 	}	
	// }

	public Customer findByConfirmationToken(String token) {
        String sql = "select * from customer where token='" + token + "'";
        try{
        	return jt.queryForObject(sql, new RowMapper<Customer>() {
                public Customer mapRow(ResultSet row, int rowNum) throws SQLException {                	
                	Customer customer = (new BeanPropertyRowMapper<>(Customer.class)).mapRow(row,rowNum);
                	return customer;
                }
            });        
        }catch(EmptyResultDataAccessException e){
        	return null;
        }         
    }
	
}


