package com.example.dbms.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.dbms.service.AuthenticateService;

@Controller
public class HomeController {

    @Autowired
    private AuthenticateService auth_Service;

    @GetMapping({ "/", "/home" })
    public String homepage(Model model, HttpSession session) {
        if (auth_Service.isAuthenticated(session)) {
            String username = auth_Service.getCurrentUser(session);
            model.addAttribute("loggedinusername", username);

        }
        return "home";
    }

    @GetMapping("/about")
    public String aboutpage(Model model, HttpSession session) {
        if (auth_Service.isAuthenticated(session)) {
            String username = auth_Service.getCurrentUser(session);
            model.addAttribute("loggedinusername", username);

        }
        return "about";
    }

}
