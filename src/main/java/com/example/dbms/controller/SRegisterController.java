package com.example.dbms.controller;


import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.example.dbms.dao.CustomerDAO;
import com.example.dbms.dao.StudentDAO;
//import com.example.dbms.Utils.HostName;
import com.example.dbms.dao.UserDAO;
import com.example.dbms.model.*;
import com.example.dbms.service.AuthenticateService;
// import com.example.dbms.service.Em ailSenderService;
import com.example.dbms.service.ToastService;
import com.example.dbms.validator.UserValidator;

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
public class SRegisterController {

    @Autowired
	 private UserDAO userDAO;
	 @Autowired
	 private CustomerDAO customerDAO;
	 @Autowired
	 private StudentDAO studentDAO;
	 @Autowired
	 private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;
	//  @Autowired
	//  EmailSenderService emailSenderService;

    @GetMapping("/studentregister")
	 public String showForm(Model model,HttpSession session) {

		if (authenticateService.isAuthenticated(session)) {
			return "redirect:/welcome";
		}
		Student student = new Student();		 
	 	model.addAttribute("student", student);
	    return "studentregister";
	 }

	 @PostMapping("/studentregister")
	 public String submitForm(@ModelAttribute("student") Student student, Model model, BindingResult bindingResult,HttpSession session, RedirectAttributes redirectAttributes) {
		
		// userValidator.validate(userForm, bindingResult);
	    
		// if (bindingResult.hasErrors()) {
	    	
	    //     return "register";
	    // }

		String username=student.getUsername();
        String errorMessage = null;
		
		//System.out.println(username);

		try {
			
			if(!(student.getPassword().equals(student.getPasswordConfirm()))) {
				
				errorMessage = "Password and Confirm Password do not match...";

				model.addAttribute("student", student);
       	 		toastService.displayErrorToast(model, errorMessage);
        		return "/studentregister";
			}
            if (!studentDAO.userExists(username)) {
				student.setRole("Student");
				student.setActive(0);
				System.out.println(student.getUsername());
				System.out.println("#4#");
				System.out.println(student.getUsername());


				// ////////////////////////Email-Verification/////////////////////
				String token = UUID.randomUUID().toString();
				student.setToken(token);
				studentDAO.save(student);

				// SimpleMailMessage mailMessage = new SimpleMailMessage();
				// mailMessage.setTo(user.getEmailID());
				// mailMessage.setSubject("Complete Registration!");
				// mailMessage.setFrom("guptacare18@gmail.com");
				// mailMessage.setText("Your account has been registered on Gupta-Care. To confirm your account, please click here : "
				// 		+HostName.getHost()+"confirm-account?token="+token);
				
				// ///////////////////////Email-Verification/////////////////////
				
				// emailSenderService.sendEmail(mailMessage);

                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully Registered...");
                return "redirect:/login";

            }
			System.out.println("hello--------------------");
            errorMessage = "This username already exists.";
        } catch (Exception e) {
            errorMessage = "This username can't be taken. Please change it...";
			System.out.println(e);
        }
	    
		model.addAttribute("student", student);
        toastService.displayErrorToast(model, errorMessage);
        return "studentregister";
		
	}

	// @GetMapping("/confirm-account")
	//  public String confirmAccountRegister(@RequestParam("token")String token,Model model,HttpSession session, RedirectAttributes redirectAttributes) {

	// 	Student user = studentDAO.findByConfirmationToken(token);

	// 	if(user != null)
	//     {
	//     	 studentDAO.updateActivity(user.getUsername(), 1);
	// 		 toastService.redirectWithSuccessToast(redirectAttributes, "Account Confirmed Successfully...");

	//     	 return "redirect:/login";
	//     }
	//     else
	//     {	
	// 		toastService.redirectWithErrorToast(redirectAttributes, "Account Confirmed Not Successfull...");	
	//         return "redirect:/welcome";
	//     }

	// }
    
}
