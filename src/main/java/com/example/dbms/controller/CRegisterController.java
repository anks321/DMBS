package com.example.dbms.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

// import com.example.dbms.dao.CustomerDAO;
import com.example.dbms.dao.CustomerDao;
import com.example.dbms.dao.StudentDAO;
import com.example.dbms.model.*;
import com.example.dbms.service.AuthenticateService;
// import com.example.dbms.service.Em ailSenderService;
import com.example.dbms.service.ToastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CRegisterController {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private ToastService toastService;
    @Autowired
    private AuthenticateService authenticateService;
    // @Autowired
    // EmailSenderService emailSenderService;

    @GetMapping("/customerregister")
    public String showForm(Model model, HttpSession session) {

        if (authenticateService.isAuthenticated(session)) {
            return "redirect:/loggedin";
        }
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customerregister";
    }

    @PostMapping("/customerregister")
    public String submitForm(@ModelAttribute("customer") Customer customer, Model model, BindingResult bindingResult,
            HttpSession session, RedirectAttributes redirectAttributes) {

        // userValidator.validate(userForm, bindingResult);

        // if (bindingResult.hasErrors()) {

        // return "register";
        // }

        String username = customer.getUsername();
        String errorMessage = null;

        // System.out.println(username);

        try {

            if (!(customer.getPassword().equals(customer.getPasswordConfirm()))) {

                errorMessage = "Password and Confirm Password do not match...";

                model.addAttribute("customer", customer);
                toastService.displayErrorToast(model, errorMessage);
                return "/customerregister";
            }
            System.out.println("dxrdexcftrdctr");
            if (!customerDao.userExists(username)) {
                System.out.println("laaaaaaaaaa");
                System.out.println(customer.getUsername());
                System.out.println("#4#");
                System.out.println(customer.getUsername());

               
                customerDao.save(customer);


                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully Registered...");
                return "redirect:/login";

            }
            System.out.println("hello--------------------");
            errorMessage = "This username already exists.";
        } catch (Exception e) {
            errorMessage = "This username can't be taken. Please change it...";
            System.out.println(e);
        }

        model.addAttribute("customer", customer);
        toastService.displayErrorToast(model, errorMessage);
        return "customerregister";

    }

    

}
