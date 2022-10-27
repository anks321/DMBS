package com.example.dbms.controller;

import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({ "/", "/home" })
    public String homepage(Model model) {

        return "home";
    }

    @GetMapping("/about")
    public String aboutpage() {

        return "about";
    }

}
