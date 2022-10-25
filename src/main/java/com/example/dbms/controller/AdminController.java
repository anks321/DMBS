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
public class AdminController {

    @Autowired
	private AuthenticateService auth_Service;
    @Autowired
	private SectionDAO sectionDAO;
	@Autowired
	private StudentDAO studentDAO;      
    
}
