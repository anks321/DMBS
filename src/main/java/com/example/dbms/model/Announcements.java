package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Announcements {
    private int A_id;
    private String announce_text;
    private String date_and_time;
    private int mess_id;
    private int section_id;
    
}
