package com.example.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;
//import java.sql.Date;

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
public class StudentDAO {

	@Autowired
	private JdbcTemplate jt;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Student student) {

		student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
		// System.out.println(student.getUsername());
		String sql = "insert into student(username,password,roll_no,room_no,Age,Balance,DOB,f_name,l_name,hostel_name,sex,parent,phone_no,s_email,localGaurdian,aadhar_no,s_account_no,s_ifsc, mess_id, section_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		jt.update(sql, student.getUsername(), student.getPassword(),
				student.getRoll_no(), student.getRoom_no(), student.getAge(), student.getBalance(), student.getDOB(),
				student.getF_name(), student.getL_name(), student.getHostel_name(), student.getSex(), student.getParent(),
				student.getPhone_no(),student.getS_email(),student.getLocalGaurdian(),student.getAadhar_no(),
				student.getS_account_no(),student.getS_ifsc(),student.getMess_id(),student.getSection_id());
		// System.out.println(student.getUsername());

	}

	public void update(int roll_no ,int room_no ,int Age ,int Balance ,Date DOB ,String f_name ,String l_name ,String hostel_name ,String sex ,String parent ,String phone_no ,String s_email ,String localGaurdian ,String aadhar_no ,String s_account_no ,String s_ifsc ,int mess_id ,int section_id, String username) {

		String sql = "update student set roll_no = ?,room_no = ?,Age = ?,Balance = ?,DOB = ?,f_name = ?,l_name = ?,hostel_name = ?,sex = ?,parent = ?,phone_no = ?,s_email = ?,localGaurdian = ?,aadhar_no = ?,s_account_no = ?,s_ifsc = ?, mess_id = ?, section_id = ? where username = ?";
		jt.update(sql , roll_no,room_no,Age,Balance,DOB,f_name,l_name,hostel_name,sex,parent,phone_no,s_email,localGaurdian,aadhar_no,s_account_no,s_ifsc, mess_id, section_id);
	}

	public void delete(String username) {

		String sql = "delete from student where username = ?";
		jt.update(sql, username);
	}

	public Student findByUsername(String username) {
		String sql = "select * from student where username='" + username + "'";
		try {
			return jt.queryForObject(sql, new RowMapper<Student>() {
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row, rowNum);
					return student;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int findBySection(String username) {
		String sql = "select mess_id from student where username='" + username + "'";
		return jt.queryForObject(sql, Integer.class);
	}

	public int findByMess(String username) {
		String sql = "select section_id from student where username='" + username + "'";
		return jt.queryForObject(sql, Integer.class);
	}

	public boolean userExists(String username) {

		String sql = "select count(*) from student where username='" + username + "'";

		int found = jt.queryForObject(sql, Integer.class);

		return (found == 1);
	}
	public List<Student> findstudentsbymess(int mess_id){
		String sql = "select * from student where mess_id=?;";

		return jt.query(sql,new BeanPropertyRowMapper<>(Student.class),mess_id);

	}
	public List<Student> findstudents(int mess_id, int section_id){
		String sql = "select * from student where mess_id=? and section_id=?;";

		return jt.query(sql,new BeanPropertyRowMapper<>(Student.class),mess_id,section_id);
		
	}

	public List<Student> allStudents() {

		String sql = "select * from student;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Student.class));
	}
	
}

