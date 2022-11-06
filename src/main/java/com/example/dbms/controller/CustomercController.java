package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.dbms.service.ToastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dbms.dao.AnnouncementsDAO;
import com.example.dbms.dao.CustomerDao;
import com.example.dbms.dao.SectionDAO;
import com.example.dbms.dao.StudentDAO;
import com.example.dbms.model.Announcements;
import com.example.dbms.model.Customer;
import com.example.dbms.model.Section;
import com.example.dbms.service.AuthenticateService;

@Controller
public class CustomercController {

    @Autowired
    public CustomerDao customerDAO;
    @Autowired
    private AuthenticateService auth_Service;
    @Autowired
    private SectionDAO sectionDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private AnnouncementsDAO announcementsDAO;
    @Autowired
    private ToastService toastService;

    @GetMapping("/customer/profile")
    public String profile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByUsername(curr_user);
        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("customer", customer);
        return "customerprofile";

    }

    @GetMapping("/customerannouncement")
    public String Announce(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.iscustomer(session)) {

            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Customer customer = customerDAO.findByUsername(curr_user);

        Integer mess_no = customer.getMess_id();
        Integer section_no = customer.getSection_id();
        if (mess_no == null && section_no == null) {
            return "redirect/:loggedin";
        }
        List<Announcements> announce = announcementsDAO.findByAnnouncements(mess_no, section_no);
        // String announce_text = announce.getAnnounce_text();
        // String date_and_time = announce.getDate_and_time();

        model.addAttribute("announcements", announce);
        System.out.println("mess_no");
        System.out.println(section_no);
        System.out.println(announce.get(0));

        return "customerannouncement";
    }

    @GetMapping("/customermenu")
    public String Custmenue(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.iscustomer(session)) {

            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Customer customer = customerDAO.findByUsername(curr_user);

        Integer mess_no = customer.getMess_id();
        Integer section_no = customer.getSection_id();
        if (mess_no == null && section_no == null) {
            return "redirect/:loggedin";
        }
        Section Menu = sectionDAO.findSection(mess_no, section_no);
        String breakfast = Menu.getBreakfast();
        String lunch = Menu.getLunch();
        String dinner = Menu.getDinner();

        model.addAttribute("breakfast", breakfast);
        model.addAttribute("lunch", lunch);
        model.addAttribute("dinner", dinner);

        return "customermenue";
    }

}
