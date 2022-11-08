package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Section {
    private Integer section_id;
    private Integer mess_id;
    private String breakfast;
    private String lunch;
    private String dinner;

}
