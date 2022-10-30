package com.example.dbms.dao;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.dbms.model.Questions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.*;

@Lazy
@Repository
public class QuestionsDAO {
    @Autowired
	private JdbcTemplate temp;


    public void insertQuestion(int mess_id,int section_id,String start_time, String end_time,String text){
        String sql = "Insert into Questions(mess_id,section_id,StarTime,EndTime,text) values(?,?,?,?,?)";
        temp.update(sql,mess_id,section_id,start_time,end_time,text);
    }
    public List<Questions> getQuestions(){
        String sql = "Select * from Questions";

        return temp.query(sql,new BeanPropertyRowMapper<>(Questions.class));
    }
    public List<Questions> getQuestionbySection(int section_id){
        String sql = "Select * from Questions where section_id = ?";

        return temp.query(sql,new BeanPropertyRowMapper<>(Questions.class),section_id);
    }
    public Questions getQuestionbyId(int id){
        String sql = "Select * from Questions where questionid = ?";

        return temp.queryForObject(sql,new BeanPropertyRowMapper<>(Questions.class),id);

    }
    
    
}
