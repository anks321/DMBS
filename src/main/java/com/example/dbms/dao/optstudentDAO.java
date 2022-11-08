package com.example.dbms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.dbms.model.Options;
import com.example.dbms.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.*;

@Lazy
@Repository
public class optstudentDAO {
    @Autowired
    private JdbcTemplate temp;

    public int countstudent(int queid, int optid) {
		String sql = "select COUNT(*) from opt_student where Q_id = ? and Opt_id=?";
		return temp.queryForObject(sql, Integer.class, queid, optid);
	}
}
