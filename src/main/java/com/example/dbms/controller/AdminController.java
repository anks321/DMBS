package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.dbms.service.*;
import com.example.dbms.validator.UserValidator;
import com.example.dbms.dao.*;
import com.example.dbms.model.*;

@Controller
public class AdminController {

    @Autowired
	private AuthenticateService auth_Service;
    @Autowired
	private SectionDAO sectionDAO;
	@Autowired
	private StudentDAO studentDAO;  
	@Autowired
	private AnnouncementsDAO AnnouncementsDAO;
    @Autowired
	private TransactionDAO transactionDAO;
	@Autowired
    private UserValidator userValidator;
    @Autowired
    private ToastService toastService;
	
	@GetMapping("/addAnnouncements")
    public String addannounce(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
        if(!auth_Service.isAuthenticated(session))
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String username = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByUsername(username);
        
        model.addAttribute("student", student);
        return "studentprofile";
    }

    
}
