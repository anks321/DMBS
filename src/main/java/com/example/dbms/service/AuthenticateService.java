package com.example.dbms.service;

import javax.servlet.http.HttpSession;

import com.example.dbms.dao.CustomerDao;
import com.example.dbms.dao.EmployeeDAO;
// import com.example.dbms.dao.CustomerDao;
import com.example.dbms.dao.StudentDAO;
import com.example.dbms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    @Autowired
    private StudentDAO students;
    @Autowired
    private CustomerDao customers;
    @Autowired
    private EmployeeDAO employees;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String loggedUser = "AUTH_USER";

    public Boolean checkCredentials(String username, String password) {
        Customer customer = customers.findByUsername(username);
        Student student = students.findByUsername(username);
        if (customer != null && student == null) {
            return (bCryptPasswordEncoder.matches(password, customer.getPassword()));
        } else if (student != null && customer == null)
            return (bCryptPasswordEncoder.matches(password, student.getPassword()));
        else if (student != null && customer != null)
            return ((bCryptPasswordEncoder.matches(password, customer.getPassword()))
                    || (bCryptPasswordEncoder.matches(password, student.getPassword())));
        return false;
    }

    public Boolean checkglobaladmin(String username, String password) {
        System.out.println(username);
        if (username.equals("admin") && password.equals("1234")) {

            return true;
        } else
            return false;

    }
    

    public Boolean checkStudentCredentials(String username, String password) {

        Student student = students.findByUsername(username);
        if (student != null) {
            return (bCryptPasswordEncoder.matches(password, student.getPassword()));
        }
        return false;
    }

    public Boolean checkCustomerCredentials(String username, String password) {
        Customer customer = customers.findByUsername(username);

        if (customer != null) {
            return (bCryptPasswordEncoder.matches(password, customer.getPassword()));
        }
        return false;
    }

    public Boolean checkEmployeeCredentials(String username, String password) {
        Employee employee = employees.findByUsername(username);
        if (employee != null) {
            return (bCryptPasswordEncoder.matches(password, employee.getPassword()))
                    && employee.getDesignation().equals("mess_head");
        }
        return false;
    }

    public Boolean checkSectionHeadCredentials(String username, String password) {
        Employee employee = employees.findByUsername(username);
        System.out.println("Checking2");
        if (employee != null) {
            System.out.println("Checking");
            return (bCryptPasswordEncoder.matches(password, employee.getPassword()))
                    && employee.getDesignation().equals("section_admin");
        }
        return false;
    }

    public void loginUser(HttpSession session, String username) {
        session.setAttribute(loggedUser, username);
    }

    public void loginStudent(HttpSession session, String username) {
        session.setAttribute(loggedUser, username);
        session.setAttribute("role", "student");
    }

    public void loginCustomer(HttpSession session, String username) {
        session.setAttribute(loggedUser, username);
        session.setAttribute("role", "customer");
    }

    public void loginAdmin(HttpSession session, String username) {
        session.setAttribute(loggedUser, username);
        session.setAttribute("role", "mess_head");
    }

    public void loginsectionAdmin(HttpSession session, String username) {
        
        session.setAttribute(loggedUser, username);
        session.setAttribute("role", "section_admin");
    }

    public void loginGlobalAdmin(HttpSession session, String username) {
        session.setAttribute(loggedUser, username);
        session.setAttribute("role", "Gadmin");
    }

    public Boolean isstudent(HttpSession session) {
        if (session.getAttribute("role").equals("student"))
            return true;
        else
            return false;
    }

    public Boolean iscustomer(HttpSession session) {
        if (session.getAttribute("role").equals("customer"))
            return true;
        else
            return false;
    }

    public Boolean isadmin(HttpSession session) {

        if (session.getAttribute("role").equals("mess_head")) {
            System.out.println(session.getAttribute("role"));
            return true;
        } else
            return false;
    }

    public Boolean issectionadmin(HttpSession session) {
        if (session.getAttribute("role").equals("section_admin"))
            return true;
        else
            return false;
    }

    public Boolean isGadmin(HttpSession session) {
        if (session.getAttribute("role").equals("Gadmin"))
            return true;
        else
            return false;
    }

    public void logoutUser(HttpSession session) {
        session.removeAttribute(loggedUser);
    }

    public String getCurrentUser(HttpSession session) {
        try {
            return session.getAttribute(loggedUser).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isAuthenticated(HttpSession session) {
        return getCurrentUser(session) != null;
    }
}
