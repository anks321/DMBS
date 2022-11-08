package com.example.dbms.dao;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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

    public void insertQuestion(int mess_id, int section_id, String text, Integer finished) {
        String sql = "Insert into Questions(mess_id,section_id,text,finished) values(?,?,?,?);";
        temp.update(sql, mess_id, section_id, text, finished);
    }

    public List<Questions> getQuestions() {
        String sql = "Select * from Questions;";

        return temp.query(sql, new BeanPropertyRowMapper<>(Questions.class));
    }

    public List<Questions> getQuestionbySection(int section_id) {
        String sql = "Select * from Questions where section_id = ?;";

        return temp.query(sql, new BeanPropertyRowMapper<>(Questions.class), section_id);
    }

    public Questions getQuestionbyId(int id) {
        String sql = "Select * from Questions where questionid = ?;";

        return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Questions.class), id);

    }

    public List<Questions> getnotanswerdquestion(int roll_no, int section_id) {
        String sql = "Select * from Questions where section_id = ? AND questionid not in (Select  Q_id from opt_student where roll_no= ? );";

        return temp.query(sql, new BeanPropertyRowMapper<>(Questions.class), section_id, roll_no);
    }

    public List<answered> getanswerdquestion(int roll_no, int section_id) {
        String sql = "Select distinct questions.questionid,options.optionid,questions.text,options.OptionText from questions ,opt_student,options where section_id = ? AND questions.questionid=opt_student.Q_id and opt_id=optionid and opt_student.roll_no=? and questions.questionid=options.Q_id ;";

        return temp.query(sql, new BeanPropertyRowMapper<>(answered.class), section_id, roll_no);
    }

}
