package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Mess {
    private Integer mess_id;
    private Integer Head_id;
    private Integer mess_no;

    private String m_name;
    private String hostel_name;

}
