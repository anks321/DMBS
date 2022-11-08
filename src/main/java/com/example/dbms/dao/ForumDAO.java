package com.example.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;
import java.util.Date;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.*;

@Lazy
@Repository
public class ForumDAO {
	@Autowired
	private JdbcTemplate temp;

	public List<Forum> getallforum() {
		String sql = "select * from forum ;";
		return temp.query(sql, new BeanPropertyRowMapper<>(Forum.class));
	}

	public void deleteforumbyid(int id) {
		String sql = "delete from forum where C_id=?;";
		temp.update(sql, id);
	}

	public int countforum() {
		String sqll = "select COUNT(*) from forum";
		int hai = temp.queryForObject(sqll, Integer.class);
		if(hai==0) return 0;
		String sql = "select MAX(C_id) from forum";
		return temp.queryForObject(sql, Integer.class);
	}

	public Forum getforumbyid(int id) {
		String sql = "select * from forum where C_id=?";
		return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Forum.class), id);
	}

	public void updateresolved(int resolve, int id) {
        String sql = "update forum set resolved = ? where C_id=?";
		temp.update(sql, resolve, id);
	}

	public List<Forum> getforumbymess(int id) {
		String sql = "select * from forum where mess_id=?";
		return temp.query(sql, new BeanPropertyRowMapper<>(Forum.class), id);
	}

	public List<Forum> getmyforum(int roll_no) {
		String sql = "select * from forum where roll_no=? ;";
		return temp.query(sql, new BeanPropertyRowMapper<>(Forum.class), roll_no);

	}

	public void insertforum(int id, String Datetime, int roll, int resolve, String text,int mess_id,  int section_id) {
		String sql = "insert into forum(C_id, date_time, roll_no, resolved, complaint, mess_id, section_id) values (?,?,?,?,?,?,?);";

		temp.update(sql, id, Datetime, roll, resolve, text, mess_id,  section_id);

	}

	public List<Forum> findByforums(int mess_no, int section_no) {
		String sql = "select * from forum where mess_id =? AND section_id = ?;";

		return temp.query(sql, new BeanPropertyRowMapper<>(Forum.class), mess_no, section_no);
	}

}
