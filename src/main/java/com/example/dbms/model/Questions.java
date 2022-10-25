package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Questions {
    private int questionid;
    private String StartTime;
    private String EndTime;
    private String text;
   
}
