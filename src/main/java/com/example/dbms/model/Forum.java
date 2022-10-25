package com.example.dbms.model;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Forum {
    private int C_id;
    private int roll_no;
    private int resolved;
    private Date expiry_date;
    private String complaint;
    private String date_time;
    
}
