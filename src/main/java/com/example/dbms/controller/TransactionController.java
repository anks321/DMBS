package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.dbms.service.*;
import com.example.dbms.validator.UserValidator;
import com.example.dbms.dao.*;
import com.example.dbms.model.*;

public class TransactionController {
    @Autowired
    private OptionsDAO optionsDAO;
    @Autowired
    private AuthenticateService auth_Service;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private InventoryDAO inventoryDAO;
    @Autowired
    private SectionDAO sectionDAO;
    @Autowired
    private optstudentDAO optDAO;
    @Autowired
    private MessDAO messDAO;
    @Autowired
    private ForumDAO forumDAO;
    @Autowired
    private CustomerDao customerDAO;
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private AnnouncementsDAO announcementsDAO;
    @Autowired
    private TransactionDAO transactionDAO;
    @Autowired
    private ToastService toastService;
    @Autowired
    private QuestionsDAO questionDAO;

    @GetMapping("/localadmin/customer/transactions/{id}")
    public String customertransactions(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByid(id);
        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("customer", customer);
        model.addAttribute("role", "section_admin");

        int cid = customer.getCid();

        List<Transaction> list = transactionDAO.alltransactions(cid, 0);
        model.addAttribute("transactions", list);

        return "managecustomertransactions";
    }

    @GetMapping("/localadmin/customer/transaction/edit/{cid}/{id}")
    public String customertransactionedit(@PathVariable("cid") int cid, @PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Transaction transaction = transactionDAO.findById(id);

        model.addAttribute("transaction", transaction);

        model.addAttribute("loggedinusername", curr_user);

        return "updatetransaction";
    }

    @PostMapping("/localadmin/customer/transaction/edit/{cid}/{id}")
    public String customertransactioneditPost(@PathVariable("cid") int cid,@PathVariable("id") int id,
            @ModelAttribute("transaction") Transaction transaction, Model model, HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        transactionDAO.update(id, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), -1, cid);
        return "redirect:/localadmin/customer/transactions/{cid}";
    }

    @GetMapping("/localadmin/customer/transaction/delete/{cid}/{id}")
    public String customertransactiondelete(@PathVariable("cid") int cid,@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        transactionDAO.delete(id);

        model.addAttribute("loggedinusername", curr_user);

        return "redirect:/localadmin/customer/transactions/{cid}";
    }

    @GetMapping("/localadmin/customer/transaction/add/{cid}")
    public String customertransactionadd(@PathVariable("cid") int cid,Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Transaction transaction = new Transaction();

        model.addAttribute("transaction", transaction);
        model.addAttribute("loggedinusername", curr_user);

        return "addtransaction";
    }

    @PostMapping("/localadmin/customer/transaction/add/{cid}")
    public String customertransactionaddPost(@PathVariable("cid") int cid,@ModelAttribute("transaction") Transaction transaction, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);
        
        int num_trans = transactionDAO.counttrans();

        transactionDAO.inserttransac(num_trans + 1, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), -1, cid);
        
        return "redirect:/localadmin/customer/transactions/{cid}";
    }

    

    //STUDENTS

    @GetMapping("/localadmin/student/transactions/{id}")
    public String studenttransactions(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByid(id);
        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("student", student);
        model.addAttribute("role", "section_admin");

        int roll = student.getRoll_no();

        List<Transaction> list = transactionDAO.alltransactions(roll, 1);
        model.addAttribute("transactions", list);

        return "managestudenttransactions";
    }

    @GetMapping("/localadmin/student/transaction/edit/{roll}/{id}")
    public String studenttransactionedit(@PathVariable("roll") int roll, @PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Transaction transaction = transactionDAO.findById(id);

        model.addAttribute("transaction", transaction);

        model.addAttribute("loggedinusername", curr_user);

        return "updatetransaction";
    }

    @PostMapping("/localadmin/student/transaction/edit/{roll}/{id}")
    public String studenttransactioneditPost(@PathVariable("roll") int roll,@PathVariable("id") int id,
            @ModelAttribute("transaction") Transaction transaction, Model model, HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        transactionDAO.update(id, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), roll, -1);
        return "redirect:/localadmin/student/transactions/{roll}";
    }

    @GetMapping("/localadmin/student/transaction/delete/{roll}/{id}")
    public String studenttransactiondelete(@PathVariable("roll") int roll,@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        transactionDAO.delete(id);

        model.addAttribute("loggedinusername", curr_user);

        return "redirect:/localadmin/student/transactions/{roll}";
    }

    @GetMapping("/localadmin/student/transaction/add/{roll}")
    public String studenttransactionadd(@PathVariable("roll") int roll,Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Transaction transaction = new Transaction();

        model.addAttribute("transaction", transaction);
        model.addAttribute("loggedinusername", curr_user);

        return "addtransaction";
    }

    @PostMapping("/localadmin/student/transaction/add/{roll}")
    public String studenttransactionaddPost(@PathVariable("roll") int roll,@ModelAttribute("transaction") Transaction transaction, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);
        
        int num_trans = transactionDAO.counttrans();

        transactionDAO.inserttransac(num_trans + 1, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), roll, -1);
        
        return "redirect:/localadmin/student/transactions/{roll}";
    }
    
}
