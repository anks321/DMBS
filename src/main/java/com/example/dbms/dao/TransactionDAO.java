package com.example.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
// import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.Student;
import com.example.dbms.model.Transaction;

@Lazy
@Repository
public class TransactionDAO {
    @Autowired
	private JdbcTemplate jt;
    public List<Transaction> alltransactions(int id, int isStudent) {
        String sql = "";
        if(isStudent == 1) {
            sql = "select * from Transactions where roll_no='" + id + "'";}
        else{
            sql = "select * from Transactions where C_id='" + id + "'";}

		return jt.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
	}

}
