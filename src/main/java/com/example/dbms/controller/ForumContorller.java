package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dbms.dao.ForumDAO;
import com.example.dbms.dao.StudentDAO;
import com.example.dbms.model.Forum;
import com.example.dbms.model.Student;
import com.example.dbms.service.AuthenticateService;
import com.example.dbms.service.ToastService;

@Controller
public class ForumContorller {
    @Autowired
    private AuthenticateService auth_Service;
    @Autowired
    public StudentDAO studentDAO;
    @Autowired
    private ToastService toastService;
    @Autowired
    public ForumDAO forumDAO;

    @GetMapping("/student/forum")
    public String getmyfor(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
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

        List<Forum> forums = forumDAO.getmyforum(student.getRoll_no());

        model.addAttribute("forum", forums);
        return "stforum";
    }

    @GetMapping("/student/forum/add")
    public String addforumpage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Student student = studentDAO.findByUsername(curr_user);

        model.addAttribute("newfor", new Forum());

        return "addforpage";
    }

    @PostMapping("/student/forum/add")
    public String addforum(@ModelAttribute("newfor") Forum newforum, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByUsername(curr_user);

        forumDAO.insertforum(student.getRoll_no(), "new", newforum.getComplaint(), 0);
        return "redirect:/student/forum";
    }

    @GetMapping("/student/forum/delete/{id}")
    public String deleteforum(@PathVariable("id") int forumid, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Student student = studentDAO.findByUsername(curr_user);
        Forum form = forumDAO.getforumbyid(forumid);
        if (form == null) {
            return "redirect:/student/forum";
        }
        forumDAO.deleteforumbyid(forumid);
        return "redirect:/student/forum";

    }
}
