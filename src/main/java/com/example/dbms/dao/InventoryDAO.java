package com.example.dbms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.dbms.model.*;

@Lazy
@Repository
public class InventoryDAO {
    @Autowired
	private JdbcTemplate temp;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Inventory inventory) {
		String sql = "insert into inventories(item_id, cost, quantity, expiry_date, name, mess_id, section_id) values (?,?,?,?,?,?,?);";
		temp.update(sql, inventory.getItem_id(), inventory.getCost(), inventory.getQuantity(), inventory.getExpiry_date(), inventory.getName(), inventory.getMess_id(), inventory.getSection_id());
	}
    
 
	public void update(int cost, int quantity, String name,int item_id) {
		String sql = "update inventories set cost = ?, quantity = ?, name = ? where item_id = ?";
		temp.update(sql, cost, quantity, name, item_id);
	} 

	public void delete(int itemID) {
		String sql = "delete from inventories where item_id = ?";
		temp.update(sql, itemID);
	}

	public Inventory findByItem_Id(int itemID) {
		String sql = "select * from inventories where item_id = ?";
		return temp.queryForObject(sql, new BeanPropertyRowMapper<>(Inventory.class), itemID);

	}

	public List<Inventory> allInventories() {
		String sql = "select * from inventory;";
		return temp.query(sql, new BeanPropertyRowMapper<>(Inventory.class));
	}

	public List<Inventory> findByInventories(int mess_no, int section_no) {
        String sql = "select * from inventory where mess_id =? AND section_id = ?;";

        return temp.query(sql, new BeanPropertyRowMapper<>(Inventory.class), mess_no, section_no);
    }
    
}
