package com.example.dbms.controller;

import javax.servlet.http.HttpSession;

import com.example.dbms.dao.StudentDAO;

import com.example.dbms.dao.CustomerDao;
import com.example.dbms.dao.EmployeeDAO;

import com.example.dbms.model.User;
import com.example.dbms.service.AuthenticateService;
import com.example.dbms.service.ToastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // @Autowired
    // private SecurityService securityService;

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private EmployeeDAO employeeDAO;
    //
    @Autowired
    private CustomerDao customerDAO;

    @Autowired
    private ToastService toastService;

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

        String errorMessage = null;

        try {
            System.out.println("Global Admin");
            if (authenticateService.checkglobaladmin(username, password)) {
                authenticateService.loginGlobalAdmin(session, username);

                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully logged in.");

                return "redirect:/loggedin";
            }

            if (authenticateService.checkStudentCredentials(username, password)
                    && studentDAO.userExists(username)) {
                authenticateService.loginStudent(session, username);
                System.out.println(username);
                // authenticateService.loginUser(session, username);
                // System.out.println(username);
                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully logged in.");
                return "redirect:/loggedin";
            } else if (authenticateService.checkCustomerCredentials(username, password)
                    && customerDAO.userExists(username)) {
                authenticateService.loginCustomer(session, username);
                System.out.println(username);
                // authenticateService.loginUser(session, username);
                // System.out.println(username);
                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully logged in.");
                return "redirect:/loggedin";
            } else if (authenticateService.checkEmployeeCredentials(username, password)
                    && employeeDAO.userExists(username)) {
                authenticateService.loginAdmin(session, username);
                System.out.println(username);
                // authenticateService.loginUser(session, username);
                // System.out.println(username);
                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully logged in.");
                return "redirect:/loggedin";
            } else if (authenticateService.checkSectionHeadCredentials(username, password)
                    && employeeDAO.userExists(username)) {
                authenticateService.loginsectionAdmin(session, username);
                System.out.println(username);
                System.out.println("here");
                // authenticateService.loginUser(session, username);
                // System.out.println(username);
                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully logged in.");
                return "redirect:/loggedin";
            }
            System.out.println("Global Admin");
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
        if (authenticateService.isGadmin(session)) {
            model.addAttribute("Gadmin", "Yes");
            model.addAttribute("loggedinusername", username);
            return "faltu";
        }

        if (authenticateService.isstudent(session)) {
            model.addAttribute("student", studentDAO.findByUsername(username));
            model.addAttribute("loggedinusername", username);
            return "faltu";
        }
        if (authenticateService.iscustomer(session)) {
            model.addAttribute("customer", customerDAO.findByUsername(username));
            model.addAttribute("loggedinusername", username);
            return "faltu";
        }
        if (authenticateService.isadmin(session)) {
            model.addAttribute("employee", employeeDAO.findByUsername(username));
            model.addAttribute("loggedinusername", username);
            return "faltu";
        }
        if (authenticateService.issectionadmin(session)) {
            System.out.println("check");
            model.addAttribute("section_admin", employeeDAO.findByUsername(username));
            model.addAttribute("loggedinusername", username);
            return "faltu";
        }
        // if (authenticateService.isadmin(session)) {
        // model.addAttribute("admin", customerDAO.findByUsername(username));
        // }

        model.addAttribute("loggedinusername", username);
        if (studentDAO.userExists(username)) {
            model.addAttribute("student", studentDAO.findByUsername(username));
            model.addAttribute("loggedinusername", username);
            return "faltu";
        }
        if (customerDAO.userExists(username)) {
            model.addAttribute("customer", customerDAO.findByUsername(username));
            model.addAttribute("loggedinusername", username);
            return "faltu";
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