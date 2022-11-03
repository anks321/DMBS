package com.example.dbms.model;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Transaction {
    private int t_id;
    private int amount;
    private int type;
    private Date date;
    private String mode_of_payment;
    private int roll_no;
    private int C_id;
    
}
