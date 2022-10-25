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

    public Section findSection(int mess_no,  int section_no) {
        String sql = "select * from section where mess_id =? AND section_id = ?;";

        return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Section.class), mess_no,section_no); 
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
    
}
