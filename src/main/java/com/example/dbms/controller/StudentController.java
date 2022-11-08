package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dbms.service.AuthenticateService;
import com.example.dbms.service.ToastService;
import com.example.dbms.validator.UserValidator;
import com.example.dbms.dao.AnnouncementsDAO;
import com.example.dbms.dao.SectionDAO;
import com.example.dbms.dao.StudentDAO;
import com.example.dbms.dao.TransactionDAO;
import com.example.dbms.model.Announcements;
import com.example.dbms.model.Section;
import com.example.dbms.model.Student;
import com.example.dbms.model.Transaction;

@Controller
public class StudentController {

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

    // Forum, Poll, Announcements, Menu, Balance, Transactions

    @GetMapping("/student/profile")
    public String profile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByUsername(curr_user);
        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("student", student);
        return "studentprofile";

    }

    @GetMapping("/studentmenu")
    public String Menu(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByUsername(curr_user);

        Integer mess_no = student.getMess_id();
        Integer section_no = student.getSection_id();

        Section Menu = sectionDAO.findSection(mess_no, section_no);
        String breakfast = Menu.getBreakfast();
        String lunch = Menu.getLunch();
        String dinner = Menu.getDinner();

        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("mess_no", mess_no);
        model.addAttribute("section_no", section_no);
        model.addAttribute("breakfast", breakfast);
        model.addAttribute("lunch", lunch);
        model.addAttribute("dinner", dinner);

        return "studentmenu";
    }

    @GetMapping("/studentannouncement")
    public String Announce(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Student student = studentDAO.findByUsername(curr_user);

        Integer mess_no = student.getMess_id();
        Integer section_no = student.getSection_id();

        List<Announcements> announce = announcementsDAO.findByAnnouncements(mess_no, section_no);
        // String announce_text = announce.getAnnounce_text();
        // String date_and_time = announce.getDate_and_time();

        model.addAttribute("announcements", announce);
        System.out.println("mess_no");
        System.out.println(section_no);

        return "studentannouncement";
    }

    @GetMapping("/studenttransaction")
    public String alltransactions(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        // if(student==null){
        // student =
        // customerDAO.findByUsername(authenticateService.getCurrentUser(session));
        // }

        Student student = studentDAO.findByUsername(auth_Service.getCurrentUser(session));
        int roll = student.getRoll_no();

        List<Transaction> list = transactionDAO.alltransactions(roll, 1);
        model.addAttribute("transactions", list);
        if (auth_Service.isAuthenticated(session)) {
            model.addAttribute("loggedinusername", auth_Service.getCurrentUser(session));

            Student loggedUser = studentDAO.findByUsername(auth_Service.getCurrentUser(session));
            model.addAttribute("loggedUser", loggedUser);
        }

        return "studenttransaction";
    }

}
