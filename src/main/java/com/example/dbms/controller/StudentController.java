package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.dbms.service.AuthenticateService;
import com.example.dbms.dao.AnnouncementsDAO;
import com.example.dbms.dao.SectionDAO;
import com.example.dbms.dao.StudentDAO;
import com.example.dbms.model.Announcements;
import com.example.dbms.model.Section;
import com.example.dbms.model.Student;

@Controller
public class StudentController {

    @Autowired
	private AuthenticateService auth_Service;
    @Autowired
	private SectionDAO sectionDAO;
	@Autowired
	private StudentDAO studentDAO; 
    @Autowired
	private AnnouncementsDAO AnnouncementsDAO;      

    //Forum, Poll, Announcements, Menu, Balance, Transactions
    @GetMapping("/studentmenu")
    public String Menu(Model model, HttpSession session){
        String curr_user =  auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByUsername(curr_user);

        Integer mess_no = student.getMess_id();
        Integer section_no = student.getSection_id();

        Section Menu = sectionDAO.findSection(mess_no, section_no); 
        String breakfast = Menu.getBreakfast();
        String lunch = Menu.getLunch();
        String dinner = Menu.getDinner();
        
        model.addAttribute("breakfast", breakfast);
        model.addAttribute("lunch", lunch);
        model.addAttribute("dinner", dinner);

        return "studentmenu";
    }

    @GetMapping("/studentannouncement")
    public String Announce(Model model, HttpSession session){
        String curr_user =  "aloo";

        Student student = studentDAO.findByUsername(curr_user);

        Integer mess_no = student.getMess_id();
        Integer section_no = student.getSection_id();

        Announcements announce = AnnouncementsDAO.findAnnouncements(mess_no, section_no); 
        String announce_text = announce.getAnnounce_text();
        String date_and_time = announce.getDate_and_time();

        
        model.addAttribute("announcements", announce_text);

        return "studentannouncement";
    }

}
