package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Section {
    private int section_id;
    private int hall_no;
    private String breakfast;
    private String lunch;
    private String dinner;

}
