package com.example.dbms.service;

import javax.servlet.http.HttpSession;

import com.example.dbms.dao.CustomerDao;
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
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    private String loggedUser = "AUTH_USER";

    public Boolean checkCredentials(String username, String password) {
        Customer customer = customers.findByUsername(username);
        Student  student= students.findByUsername(username);
        if(customer!=null&&student==null){return (bCryptPasswordEncoder.matches(password,customer.getPassword()));}
        else if(student!=null&&customer==null)return (bCryptPasswordEncoder.matches(password,student.getPassword()));
        else if(student!=null&&customer!=null) return ((bCryptPasswordEncoder.matches(password,customer.getPassword()))||(bCryptPasswordEncoder.matches(password,student.getPassword())));
        return false;
    }

    public void loginUser(HttpSession session, String username) {
        session.setAttribute(loggedUser, username);
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
