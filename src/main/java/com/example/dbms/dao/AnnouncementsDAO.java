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

import com.example.dbms.model.Announcements;


@Lazy
@Repository
public class AnnouncementsDAO {

    @Autowired
	private JdbcTemplate temp;

    public List<Announcements> findByAnnouncements(int mess_no,  int section_no) {
        String sql = "select * from Announcements where mess_id =? AND section_id = ?;";

        return temp.query(sql, new BeanPropertyRowMapper<>(Announcements.class), mess_no,section_no); 
    }
}
