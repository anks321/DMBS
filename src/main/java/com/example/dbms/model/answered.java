package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class answered {

    private int questionid;
    private int optionid;

    private String text;
    private String OptionText;
}
