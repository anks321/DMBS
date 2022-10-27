package com.example.dbms.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.dbms.dao.StudentDAO;

import com.example.dbms.dao.CustomerDao;
//import com.example.dbms.Utils.FileUploadUtil;
// import com.example.dbms.Utils.FileUploadUtil;
import com.example.dbms.dao.UserDAO;
import com.example.dbms.model.User;
import com.example.dbms.service.AuthenticateService;
import com.example.dbms.service.ToastService;
import com.example.dbms.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // @Autowired
    // private SecurityService securityService;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private CustomerDao customerDAO;

    @Autowired
    private ToastService toastService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AuthenticateService authenticateService;

    @GetMapping("/login")
    public String login(Model model, String error, String logout, HttpSession session) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        // System.out.println(session.getAttribute("loggedUser").toString());
        if (authenticateService.isAuthenticated(session)) {
            return "redirect:/loggedin";
        }
        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("userForm") User userForm, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String username = userForm.getUsername();
        String password = userForm.getPassword();
        // System.out.println(username);

        String errorMessage = null;

        try {
            if (authenticateService.checkCredentials(username, password)) {
                authenticateService.loginUser(session, username);
                System.out.println(username);

                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully logged in.");
                return "redirect:/loggedin";

            }
            errorMessage = "Incorrect password.";

        } catch (Exception e) {
            errorMessage = "No user with this username found.";
            System.out.println(e);
        }
        model.addAttribute("user", userForm);
        toastService.displayErrorToast(model, errorMessage);
        return "login";
    }

    @GetMapping("/loggedin")
    public String welcome(Model model, HttpSession session) {

        // System.out.println(session.getAttribute("loggedUser").toString());

        // model.addAttribute("loggedinUser",
        // authenticateService.getCurrentUser(session));

        String username = authenticateService.getCurrentUser(session);
        model.addAttribute("loggedinusername", username);
        if (studentDAO.userExists(username)) {
            model.addAttribute("student", studentDAO.findByUsername(username));
        }
        if (customerDAO.userExists(username)) {
            model.addAttribute("customer", customerDAO.findByUsername(username));
        }
        return "faltu";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        authenticateService.logoutUser(session);
        return "redirect:/login";
    }

    // @PostMapping("/logout")
    // public String logoutPost(HttpSession session, Model model) {
    // return "redirect:/welcome";
    // }

}