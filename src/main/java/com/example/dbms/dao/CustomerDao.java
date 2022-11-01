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
	private JdbcTemplate temp;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Customer customer) {

		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		
		String sql = "insert into customer(username,password,cid,balance,pin,phone_no,c_aadhar_number,account_no,sex,ifsc,dob,first_name,last_name,email,city,street) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		temp.update(sql, customer.getUsername(), customer.getPassword(), 
				
				customer.getCid(), customer.getBalance(), customer.getPin(), customer.getPhone_no(),
				customer.getC_aadhar_number(),
				customer.getAccount_no(), customer.getSex(), customer.getIfsc(), customer.getDob(),
				customer.getFirst_name(),
				customer.getLast_name(), customer.getEmail(), customer.getCity(), customer.getStreet());
		

	}

	public void update(int cid, int balance, int pin, String phone_no, String c_aadhar_number, String account_no,
			String sex, String ifsc, String dob, String first_name,
			String last_name, String email, String city, String street, int mess_id, int section_id, String username) {

		String sql = "update customer set cid = ?,balance = ?,pin = ?,phone_no = ?,c_aadhar_number = ?,account_no = ?,sex = ?,ifsc = ?,dob = ?,first_name = ?,last_name = ?,email = ?,city = ?,street = ?,mess_id = ?,section_id = ? where username = ?";
		temp.update(sql, cid, balance, pin, phone_no, c_aadhar_number, account_no, sex, ifsc, dob, first_name,
				last_name, email, city, street, mess_id, section_id);
	}

	public void delete(String username) {

		String sql = "delete from customer where username = ?";
		temp.update(sql, username);
	}

	public Customer findByUsername(String username) {
		String sql = "select * from customer where username='" + username + "'";
		try {
			return temp.queryForObject(sql, new RowMapper<Customer>() {
				public Customer mapRow(ResultSet row, int rowNum) throws SQLException {
					Customer customer = (new BeanPropertyRowMapper<>(Customer.class)).mapRow(row, rowNum);
					return customer;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public boolean userExists(String username) {

		String sql = "select count(*) from customer where username='" + username + "'";

		int found = temp.queryForObject(sql, Integer.class);

		return (found == 1);
	}

	// public boolean updatePassword(String username,String oldPassword,String
	// oldPasswordEntered, String newPassword) {

	// if(bCryptPasswordEncoder.matches(oldPasswordEntered,oldPassword)) {
	// String sql="update customer set password=? where username=?;";
	// String encodedNewPassword=bCryptPasswordEncoder.encode(newPassword);
	// temp.update(sql,encodedNewPassword,username);
	// return true;
	// }
	// else {
	// return false;
	// }
	// }

	public List<Customer> allCustomers() {

		String sql = "select * from customer;";

		return temp.query(sql, new BeanPropertyRowMapper<>(Customer.class));
	}

	public List<Customer> allCustomersbySection(int mess_id, int section_id) {

		String sql = "select * from customer where mess_id = ? and section_id =?;";

		return temp.query(sql, new BeanPropertyRowMapper<>(Customer.class), mess_id, section_id);
	}


	public List<Customer> findbymess(int mess_id) {
		String sql = "select * from customer where mess_id = ? ";

		return temp.query(sql, new BeanPropertyRowMapper<>(Customer.class), mess_id);
	}

}
