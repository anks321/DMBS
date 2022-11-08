package com.example.dbms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Questions {
    private Integer questionid;
    private Integer mess_id;
    private Integer section_id;
    private String text;
    private Integer Finished;
    // public Questions() {
    // }

    // public Questions(int questionid, String startTime, String endTime, String
    // text) {
    // this.questionid = questionid;
    // StartTime = startTime;
    // EndTime = endTime;
    // this.text = text;
    // }

    // public int getQuestionid() {
    // return questionid;
    // }
    // public void setQuestionid(int questionid) {
    // this.questionid = questionid;
    // }
    // public String getStartTime() {
    // return StartTime;
    // }
    // public void setStartTime(String startTime) {
    // StartTime = startTime;
    // }
    // public String getEndTime() {
    // return EndTime;
    // }
    // public void setEndTime(String endTime) {
    // EndTime = endTime;
    // }
    // public String getText() {
    // return text;
    // }
    // public void setText(String text) {
    // this.text = text;
    // }

}
