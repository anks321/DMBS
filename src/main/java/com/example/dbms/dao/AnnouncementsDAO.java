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

    public void save(Announcements announcement) {
		String sql = "insert into Announcements(A_id, mess_id, section_id, announce_text, date_and_time) values (?,?,?,?,?);";
		temp.update(sql, announcement.getA_id(), announcement.getMess_id(), announcement.getSection_id(), announcement.getAnnounce_text(), announcement.getDate_and_time());
	}
 
	public void update(String announce_text, String date_and_time, int A_id) {
		String sql = "update Announcements set announce_text = ?, date_and_time = ? where A_id = ?";
		temp.update(sql, announce_text, date_and_time, A_id);
	} 

	public void delete(int itemID) {
		String sql = "delete from Announcements where A_id = ?";
		temp.update(sql, itemID);
	}

	public Announcements findByItem_Id(int itemID) {
		String sql = "select * from Announcements where A_id = ?";
		return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Announcements.class), itemID);
	}

	public int countannounce() {
		String sql = "select MAX(A_id) from Announcements";
		return temp.queryForObject(sql, Integer.class);
	}

	public List<Announcements> allAnnouncements() {
		String sql = "select * from Announcements;";
		return temp.query(sql, new BeanPropertyRowMapper<>(Announcements.class));
	}

    public List<Announcements> findByAnnouncements(int mess_no, int section_no) {
        String sql = "select * from Announcements where mess_id =? AND section_id = ?;";

        return temp.query(sql, new BeanPropertyRowMapper<>(Announcements.class), mess_no, section_no);
    }
}
