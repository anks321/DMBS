package com.example.dbms.model;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inventory {
    private int item_id;
    private int cost;
    private int quantity;
    private Date expiry_date;
    private String name;
    
}
