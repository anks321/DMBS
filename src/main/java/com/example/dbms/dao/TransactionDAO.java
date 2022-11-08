package com.example.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
	private JdbcTemplate temp;

    public List<Transaction> alltransactions(int id, int isStudent) {
        String sql = "";
        if(isStudent == 1) {
            sql = "select * from Transactions where roll_no='" + id + "'";}
        else{
            sql = "select * from Transactions where C_id='" + id + "'";}

		return temp.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
	}

    public void inserttransac(int t_id, int amount, int type, String date, String mode_of_payment, int roll_no, int C_id) {
        if(roll_no == -1){
            String sql = "insert into Transactions(t_id, amount, type, date, mode_of_payment, C_id) values (?,?,?,?,?,?);";
            temp.update(sql, t_id, amount, type, date, mode_of_payment, C_id);
        }
        if(C_id == -1){
            String sql = "insert into Transactions(t_id, amount, type, date, mode_of_payment, roll_no) values (?,?,?,?,?,?);";
            temp.update(sql, t_id, amount, type, date, mode_of_payment, roll_no);
        }

	}

	public void update(int t_id, int amount, int type, String date, String mode_of_payment, int roll_no, int C_id) {
        if(roll_no == -1){
            String sql = "update Transactions set amount = ?, type = ?, date = ?, mode_of_payment = ?, C_id = ? where t_id = ?";
            temp.update(sql, amount, type, date, mode_of_payment, C_id, t_id);
        }
        if(C_id == -1){
            String sql = "update Transactions set amount = ?, type = ?, date = ?, mode_of_payment = ?, roll_no = ? where t_id = ?";
            temp.update(sql, amount, type, date, mode_of_payment, roll_no, t_id);
        }
	}

	public void delete(int itemID) {
		String sql = "delete from Transactions where t_id = ?";
		temp.update(sql, itemID);
	}

    public Transaction findById(int itemID) {
		String sql = "select * from Transactions where t_id = ?";
		return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Transaction.class), itemID);
	}

    public int counttrans() {
        String sqll = "select COUNT(*) from Transactions";
		int hai = temp.queryForObject(sqll, Integer.class);
		if(hai==0) return 0;
		String sql = "select MAX(t_id) from Transactions";
		return temp.queryForObject(sql, Integer.class);
	}

}
