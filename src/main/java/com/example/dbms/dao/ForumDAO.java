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

	public Forum getforumbyid(int id) {
		String sql = "select * from forum where C_id=?";
		return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Forum.class), id);
	}

	public List<Forum> getforumbymess(int id) {
		String sql = "select * from forum where mess_id=?";
		return temp.query(sql, new BeanPropertyRowMapper<>(Forum.class), id);
	}

	public List<Forum> getmyforum(int roll_no) {
		String sql = "select * from forum where roll_no=? ;";

		return temp.query(sql, new BeanPropertyRowMapper<>(Forum.class), roll_no);

	}

	public void insertforum(int roll_no, String Datetime, String text, int resolved, int mess_id) {
		String sql = "insert into forum(roll_no,complaint,date_time,resolved,mess_id) values (?,?,?,?,?);";

		temp.update(sql, roll_no, text, Datetime, resolved, mess_id);

	}

}
