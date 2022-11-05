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

        return "viewstudents";
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
        return "viewcustomers";
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
 
        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();
 
        List<Inventory> Inventories = inventoryDAO.findByInventories(mess_no, section_no);

        model.addAttribute("inventories", Inventories);

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

    @PostMapping("/localadmin/manage/inventory/edit/{Item_Id}")
    public String inventoryEditDashboardPost(@PathVariable("Item_Id") int Item_Id,
            @ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session) {
        inventoryDAO.update(inventory.getCost(), inventory.getQuantity(),
                inventory.getName(), Item_Id);
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
        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        Inventory inventory = new Inventory();
        inventory.setMess_id(mess_no);
        inventory.setSection_id(section_no);

        model.addAttribute("inventory", inventory);
        model.addAttribute("loggedinUser", curr_user);

        return "addinventory";
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

        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();
 
        List<Announcements> announcements = announcementsDAO.findByAnnouncements(mess_no, section_no);

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

    @PostMapping("/localadmin/manage/announcement/edit/{Item_Id}")
    public String announcementEditDashboardPost(@PathVariable("Item_Id") int Item_Id,
            @ModelAttribute("announcement") Announcements announcement, Model model, HttpSession session) {
        announcementsDAO.update( announcement.getAnnounce_text(), announcement.getDate_and_time(), announcement.getA_id());
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
        
        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        Announcements announcement = new Announcements();

        announcement.setMess_id(mess_no);
        announcement.setSection_id(section_no);

        model.addAttribute("announcement", announcement);
        model.addAttribute("loggedinUser", curr_user);

        return "addannouncement";
    }

    @PostMapping("/localadmin/announcement/add")
    public String announcementAddDashboardPost(@ModelAttribute("announcement") Announcements announcement, Model model,
            HttpSession session) {

        announcementsDAO.save(announcement);

        return "redirect:/localadmin/allannouncements";
    }

    //Menus
    
    @GetMapping("/localadmin/allmenues")
    public String menuesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");
        String username = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();
 
        Section section = sectionDAO.findSection(mess_no, section_no);

        model.addAttribute("section", section);

        model.addAttribute("UserLoggedIn", username);
        return "listlocalsections";
    }

    @GetMapping("/localadmin/manage/menue")
    public String menueDashboard(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section admin");

        String username = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();
 
        Section section = sectionDAO.findSection(mess_no, section_no);

        model.addAttribute("section", section);

        model.addAttribute("curr_user", username);

        return "updatelocalsection";
    }

    @PostMapping("/localadmin/manage/menue/edit")
    public String menueEditDashboardPost(@ModelAttribute("section") Section section, Model model, HttpSession session) {
        sectionDAO.updateMenue(section.getSection_id(), section.getMess_id(), section.getBreakfast(), section.getLunch(), section.getDinner());
        return "redirect:/localadmin/allmenues";
    }

    @GetMapping("/localadmin/manage/menue/delete")
    public String menueDeleteDashboard(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        sectionDAO.delete(mess_no, section_no);

        model.addAttribute("curr_user", curr_user);

        return "redirect:/localadmin/allmenues";
    }

    @GetMapping("/localadmin/menue/add")
    public String menueAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section admin");
        
        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        Section section = new Section();

        section.setMess_id(mess_no);
        section.setSection_id(section_no);

        model.addAttribute("section", section);
        model.addAttribute("loggedinUser", curr_user);

        return "addlocalsection";
    }

    @PostMapping("/localadmin/menue/add")
    public String menueAddDashboardPost(@ModelAttribute("section") Section section, Model model,
            HttpSession session) {

        sectionDAO.save(section);

        return "redirect:/localadmin/allmenues";
    }
    

}
