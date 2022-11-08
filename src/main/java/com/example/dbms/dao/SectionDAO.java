package com.example.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.Section;

@Lazy
@Repository
public class SectionDAO {

	@Autowired
	private JdbcTemplate temp;

	public Section findSection(int mess_no, int section_no) {
		String sql = "select * from section where mess_id = ? AND section_id = ?;";

		return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Section.class), mess_no, section_no);
	}

	public Section findByUsername(String username) {
		String sql = "select * from section where username='" + username + "'";
		try {
			return temp.queryForObject(sql, new RowMapper<Section>() {
				public Section mapRow(ResultSet row, int rowNum) throws SQLException {
					Section student = (new BeanPropertyRowMapper<>(Section.class)).mapRow(row, rowNum);
					return student;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void updateMenue(String breakfast, String lunch, String dinner, int sectionid, int messid) {
		String sql = "Update section set breakfast = ?, lunch = ?, dinner = ? where section_id = ? and mess_id = ?";
		temp.update(sql, breakfast, lunch, dinner, sectionid, messid);
	}

	public void insert(int sectionid, int messid, String breakfast, String lunch, String dinner) {
		String sql = "Insert into section values(?, ?, ?, ?, ?)";
		temp.update(sql, messid, sectionid, breakfast, lunch, dinner);
	}

	public void delete(int messid, int sectionid) {
		String sql = "Delete from section where mess_id = ? and section_id = ?";
		temp.update(sql, messid, sectionid);
	}

	public void save(Section section) {
		String sql = "Insert into section(section_id, mess_id, breakfast, lunch, dinner) values(?, ?, ?, ?, ?)";
		temp.update(sql, section.getSection_id(), section.getMess_id(), section.getBreakfast(), section.getLunch(), section.getDinner());
	}
	
	public int countmenu(int messid, int sectionid) {
		String sql = "select count(*) from section where mess_id = ? and section_id = ?";
		return temp.queryForObject(sql, Integer.class, messid, sectionid);
	}

}
