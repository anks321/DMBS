package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private AnnouncementsDAO announcementsDAO;
    @Autowired
	private TransactionDAO transactionDAO;
	@Autowired
    private UserValidator userValidator;
    @Autowired
    private ToastService toastService;
	
	@GetMapping("/showAnnouncements/{mess_no}/{section_no}")
    public String addannounce(@PathVariable("mess_no") int mess_no, @PathVariable("section_no") int section_no, HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String username = auth_Service.getCurrentUser(session);

        if(username != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Student student = studentDAO.findByUsername(username);

		List<Announcements> announcements = announcementsDAO.findByAnnouncements(mess_no, section_no);
        
        model.addAttribute("announcements", announcements);
        return "studentprofile";
    }

	@GetMapping("/dashboard/student/add")
    public String studentAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String username = auth_Service.getCurrentUser(session);

        if(username != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String loggedinUser = auth_Service.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);

        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("loggedinUser", auth_Service.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addUser";
    }

    @PostMapping("/dashboard/user/add")
    public String userAddDashboardPost(@ModelAttribute("user") User user, HttpSession session) {

        user.setPhoto("adminpic");
        userDAO.save(user);

        return "redirect:/dashboard/manage/users";
    }

    
}
