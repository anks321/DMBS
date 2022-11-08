package com.example.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.*;

@Lazy
@Repository
public class MessDAO {

    @Autowired
    public JdbcTemplate temp;

    public List<Section> getSectionsbyMess(int mess_id) {
        String sql = "Select * from section where mess_id=?";
        return temp.query(sql, new BeanPropertyRowMapper<>(Section.class), mess_id);
    }
    public Mess getmessbyid(int mess_id) {
        String sql = "Select * from mess where mess_id=?";
        return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Mess.class), mess_id);
    }

    public void insertMess(int mess_id, String m_name, Integer Head_id, String hostel_name) {
        String sql = "Insert into mess(mess_id,Head_id,m_name,hostel_name) values(?,?,?,?)";
        temp.update(sql, mess_id, Head_id, m_name, hostel_name);
    }

    public List<Mess> getallMess() {
        String sql = "Select * from mess;";
        return temp.query(sql, new BeanPropertyRowMapper<>(Mess.class));
    }

    public void makeMesshead(int mess_id, int Head_id) {
        String sql = "Update mess set Head_id=? where mess_id=?;";
        temp.update(sql, Head_id, mess_id);
    }

    public void deletemess(int mess_id) {
        String sql = "Delete from mess where mess_id=?;";
        temp.update(sql, mess_id);
    }

}
