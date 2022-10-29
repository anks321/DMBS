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

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.*;

@Lazy
@Repository
public class OptionsDAO {
    
@Autowired
public JdbcTemplate temp;


public void addOption(int option_id,int question_id, int text){
    String sql="Insert into Options values(?,?,?);";
    temp.update(sql,option_id,question_id,text);
}

public List<Options> getOptions(int question_id){
    String sql="Select * from Options where Q_id = ? ;";
    return temp.query(sql,new BeanPropertyRowMapper<>(Options.class),question_id);
}

public void deleteOption(int option_id,int question_id){
    String sql="Delete from Options where Q_id = ? AND optionid=?;";
    temp.update(sql,question_id,option_id);
}

public List<Student> getStudentsforoption(int option_id, int question_id){
    String sql="Select * from Students where roll_no in(Select * from Opt_Student where optionid=? and Q_id=?)";

    return temp.query(sql,new BeanPropertyRowMapper<>(Student.class),option_id,question_id);

}

public void chooseOption(int option_id,int question_id,int roll_no){
    String sql="Insert into Opt_Student values(?,?,?);";
    temp.update(sql,roll_no,question_id,option_id);

}


}
