package com.example.dbms.dao;

import com.example.dbms.model.Announcements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Lazy
@Repository
public class AnnouncementsDAO {

    @Autowired
    private JdbcTemplate jt;
    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Announcements announcements) {

        String sql="insert into announcements(purpose,purposeID,rating,complaint,suggestion,date) values (?,?,?);";
        jt.update(sql,
                announcements.getA_id(),
                announcements.getDate_and_time(),
                announcements.getText()
        );
    }

    public void delete(int announcementID) {

        String sql = "delete from feedback where feedbackID = ?";
        jt.update(sql, announcementID);
    }

    public Announcements findByID(int announcementID) {
        String sql = "select * from feedback where feedbackID = ?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Announcements.class), announcementID);

    }

//    public int isAppointmentFeedback(int id) {
//
//        String sql = "Select count(*) from feedback where purpose = 'Appointment' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
//    public int appointmentRating(int id) {
//
//        String sql = "Select rating from feedback where purpose = 'Appointment' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
//    public int isTestbookingFeedback(int id) {
//
//        String sql = "Select count(*) from feedback where purpose = 'Test Booking' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
//    public int testbookingRating(int id) {
//
//        String sql = "Select rating from feedback where purpose = 'Test Booking' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
//    public int isRoombookingFeedback(int id) {
//
//        String sql = "Select count(*) from feedback where purpose = 'Room Booking' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
//    public int roombookingRating(int id) {
//
//        String sql = "Select rating from feedback where purpose = 'Room Booking' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
//    public int isMedicineorderFeedback(int id) {
//
//        String sql = "Select count(*) from feedback where purpose = 'Medicine Order' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
//    public int medicineorderRating(int id) {
//
//        String sql = "Select rating from feedback where purpose = 'Medicine Order' and purposeID = ?";
//        return jt.queryForObject(sql, Integer.class,id);
//
//    }
//
    public List<Announcements> allAnnouncements() {

        String sql="select * from announcements;";

        return jt.query(sql, new BeanPropertyRowMapper<>(Announcements.class));
    }
//
//    public int getLastID() {
//        String query = "Select LAST_INSERT_ID()";
//        return jt.queryForObject(query, Integer.class);
//    }

}

