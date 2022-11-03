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

@Controller
public class LocalAdminController {

    @Autowired
    private AuthenticateService auth_Service;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private InventoryDAO inventoryDAO;
    @Autowired
    private SectionDAO sectionDAO;
    @Autowired
    private MessDAO messDAO;
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

    // STUDENTS

    @GetMapping("/localadmin/allstudents")
    public String studentsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");
        String username = auth_Service.getCurrentUser(session);
        List<Student> students = studentDAO.allStudents();
        System.out.println(students.get(0));

        model.addAttribute("students", students);
        model.addAttribute("loggedinUser", username);

        return "liststudents";
    }

    @GetMapping("/localadmin/manage/student/{id}")
    public String studentDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByid(id);
        model.addAttribute("role", "section admin");

        model.addAttribute("student", student);

        model.addAttribute("loggedinuser", curr_user);

        return "updatestudent";
    }

    @PostMapping("/localadmin/manage/student/edit/{id}")
    public String studentEditDashboardPost(@PathVariable("id") int id,
            @ModelAttribute("student") Student student, Model model, HttpSession session) {
        studentDAO.update(id, student.getRoom_no(), student.getBalance(),
                student.getF_name(), student.getL_name(), student.getHostel_name(), student.getSex(),
                student.getParent(),
                student.getPhone_no(), student.getS_email(), student.getLocalGaurdian(), student.getAadhar_no(),
                student.getS_account_no(), student.getS_ifsc(), student.getMess_id(), student.getSection_id(),
                student.getUsername());
        return "redirect:/localadmin/allstudents";
    }

    @GetMapping("/localadmin/manage/student/delete/{id}")
    public String studentDeleteDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        Student student = studentDAO.findByid(id);
        int roll = student.getRoll_no();

        List<Transaction> transactions = transactionDAO.alltransactions(roll, 1);

        String TranMessage = "Sorry, Student has some transactions!";
        if (transactions.isEmpty()) {
            toastService.redirectWithErrorToast(redirectAttributes, TranMessage);
            return "redirect:/dashboard/manage/students";
        }

        studentDAO.delete(id);

        model.addAttribute("role", "section admin");
        model.addAttribute("loggedinUser", curr_user);

        return "redirect:/dashboard/manage/students";
    }

    @GetMapping("/localadmin/student/add")
    public String studentAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");
        String curr_user = auth_Service.getCurrentUser(session);

        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("loggedinuser", curr_user);

        return "addstudent";
    }
    @PostMapping("/localadmin/student/add")
    public String studentAddDashboardPost(@ModelAttribute("student") Student student, Model model,
            HttpSession session) {

        studentDAO.save(student);

        return "redirect:/localadmin/allstudents";
    }

    // CUSTOMERS


    @GetMapping("/localadmin/allcustomers")
    public String getAllCustomers(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");
        String username = auth_Service.getCurrentUser(session);

        List<Customer> customers = customerDAO.allCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("loggedinUser", username);
        return "listcustomers";
    }

    @GetMapping("/localadmin/manage/customer/{id}")
    public String customerDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByid(id);
        model.addAttribute("role", "section admin");

        model.addAttribute("customer", customer);

        model.addAttribute("loggedinUser", curr_user);

        return "updatecustomer";
    }

    @PostMapping("/localadmin/manage/customer/edit/{id}")
    public String customerEditDashboardPost(@PathVariable("id") int id,
            @ModelAttribute("customer") Customer customer, Model model, HttpSession session) {
        customerDAO.update(customer.getBalance(), customer.getPin(), customer.getPhone_no(),
                customer.getC_aadhar_number(),
                customer.getAccount_no(), customer.getSex(), customer.getIfsc(), customer.getFirst_name(),
                customer.getLast_name(), customer.getEmail(), customer.getCity(), customer.getStreet(),
                customer.getMess_id(), customer.getSection_id(), id);
        return "redirect:/localadmin/allcustomers";
    }

    @GetMapping("/localadmin/manage/customer/delete/{id}")
    public String customerDeleteDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByid(id);
        int roll = customer.getCid();

        List<Transaction> transactions = transactionDAO.alltransactions(roll, 1);

        String TranMessage = "Sorry, Customer has some transactions!";
        if (transactions.isEmpty()) {
            toastService.redirectWithErrorToast(redirectAttributes, TranMessage);
            return "redirect:/dashboard/manage/customers";
        }

        customerDAO.delete(id);

        model.addAttribute("role", "section admin");
        model.addAttribute("loggedinuser", curr_user);

        return "redirect:/localadmin/allcustomers";
    }

   

    @GetMapping("/localadmin/customer/add")
    public String customerAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");
        String curr_user = auth_Service.getCurrentUser(session);


        Customer customer = new Customer();

        model.addAttribute("customer", customer);
        model.addAttribute("loggedinuser", curr_user);

        return "addcustomer";
    }

    @PostMapping("/localadmin/customer/add")
    public String customerAddDashboardPost(@ModelAttribute("customer") Customer customer, Model model,
            HttpSession session) {

        customerDAO.save(customer);

        return "redirect:/localadmin/allcustomers";
    }

    // INVENTORIES

    @GetMapping("/localadmin/allinventories")
    public String inventoriesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");
        String username = auth_Service.getCurrentUser(session);
 
        List<Inventory> inventories = inventoryDAO.allInventories();

        model.addAttribute("inventories", inventories);

        model.addAttribute("UserLoggedIn", username);
        return "listinventories";
    }

    @GetMapping("/localadmin/manage/inventory/{id}")
    public String inventoryDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");

        Inventory inventory = inventoryDAO.findByItem_Id(Item_Id);

        model.addAttribute("inventory", inventory);

        model.addAttribute("curr_user", curr_user);

        return "updateinventory";
    }

    @PostMapping("/dashboard/manage/inventory/edit/{Item_Id}")
    public String inventoryEditDashboardPost(@PathVariable("Item_Id") int Item_Id,
            @ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session) {
        inventoryDAO.update(inventory.getCost(), inventory.getQuantity(),
                inventory.getName(), inventory.getMess_id(), inventory.getSection_id(), Item_Id);
        return "redirect:/localadmin/allemployees";
    }

    @GetMapping("/localadmin/manage/inventory/delete/{Item_Id}")
    public String inventoryDeleteDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");

        inventoryDAO.delete(Item_Id);

        model.addAttribute("curr_user", curr_user);

        return "redirect:/localadmin/allinventories";
    }

    @GetMapping("/localadmin/inventory/add")
    public String inventoryAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");

        Inventory inventory = new Inventory();

        model.addAttribute("employee", inventory);
        model.addAttribute("loggedinUser", curr_user);

        return "addInventory";
    }

    @PostMapping("/localadmin/inventory/add")
    public String inventoryAddDashboardPost(@ModelAttribute("inventory") Inventory inventory, Model model,
            HttpSession session) {

        inventoryDAO.save(inventory);

        return "redirect:/localadmin/allinventories";
    }

    //ANNOUNCEMENTS
    
    @GetMapping("/localadmin/allannouncements")
    public String announcementsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");
        String username = auth_Service.getCurrentUser(session);
 
        List<Announcements> announcements = announcementsDAO.allAnnouncements();

        model.addAttribute("announcements", announcements);

        model.addAttribute("UserLoggedIn", username);
        return "listannouncements";
    }

    @GetMapping("/localadmin/manage/announcement/{id}")
    public String announcementDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");

        Announcements announcement = announcementsDAO.findByItem_Id(Item_Id);

        model.addAttribute("announcement", announcement);

        model.addAttribute("curr_user", curr_user);

        return "updateannouncement";
    }

    @PostMapping("/dashboard/manage/announcement/edit/{Item_Id}")
    public String announcementEditDashboardPost(@PathVariable("Item_Id") int Item_Id,
            @ModelAttribute("announcement") Announcements announcement, Model model, HttpSession session) {
        announcementsDAO.update(announcement.getMess_id(), announcement.getSection_id(), announcement.getAnnounce_text(), announcement.getDate_and_time(), announcement.getA_id());
        return "redirect:/localadmin/allannouncements";
    }

    @GetMapping("/localadmin/manage/announcement/delete/{Item_Id}")
    public String announcementDeleteDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");

        announcementsDAO.delete(Item_Id);

        model.addAttribute("curr_user", curr_user);

        return "redirect:/localadmin/allannouncements";
    }

    @GetMapping("/localadmin/announcement/add")
    public String announcementAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");
        Announcements announcement = new Announcements();

        model.addAttribute("employee", announcement);
        model.addAttribute("loggedinUser", curr_user);

        return "addAnnouncement";
    }

    @PostMapping("/localadmin/announcement/add")
    public String announcementAddDashboardPost(@ModelAttribute("announcement") Announcements announcement, Model model,
            HttpSession session) {

        announcementsDAO.save(announcement);

        return "redirect:/localadmin/allannouncements";
    }

}
