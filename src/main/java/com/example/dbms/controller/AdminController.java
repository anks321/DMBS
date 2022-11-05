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
public class AdminController {

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

    // LINE 846-----------------------------

    @GetMapping("/showAnnouncements/{mess_no}/{section_no}")
    public String addannounce(@PathVariable("mess_no") int mess_no, @PathVariable("section_no") int section_no,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) && !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String username = auth_Service.getCurrentUser(session);

        if (username != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        List<Announcements> announcements = announcementsDAO.findByAnnouncements(mess_no, section_no);

        model.addAttribute("announcements", announcements);
        return "studentprofile";
    }

    @GetMapping("/admin/addmess")
    public String saddmess(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");

        String curr_user = auth_Service.getCurrentUser(session);

        model.addAttribute("mess", new Mess());

        model.addAttribute("loggedinUser", curr_user);

        return "createmess";
    }

    @PostMapping("/admin/addmess")
    public String addmess(@ModelAttribute("mess") Mess mess, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");

        String curr_user = auth_Service.getCurrentUser(session);
        System.out.println(mess);
        messDAO.insertMess(mess.getMess_id(), mess.getM_name(), mess.getHead_id(),
                mess.getHostel_name());

        return "redirect:/gadmin/allmess";
    }

    @GetMapping("/admin/allsections")
    public String sectionDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");
        String username = auth_Service.getCurrentUser(session);
        Integer mess_id = employeeDAO.findByUsername(username).getMess_id();
        List<Section> sections = messDAO.getSectionsbyMess(mess_id);
        System.out.println(sections.get(0));
        model.addAttribute("sections", sections);
        model.addAttribute("loggedinusername", username);

        return "listsections";
    }

    @GetMapping("/admin/addsection")
    public String addsection(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");
        String username = auth_Service.getCurrentUser(session);

        model.addAttribute("section", new Section());
        model.addAttribute("loggedinusername", username);

        return "addsection";
    }

    @PostMapping("/admin/addsection")
    public String addsectionDashboard(@ModelAttribute("section") Section section, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String username = auth_Service.getCurrentUser(session);
        Integer mess_id = employeeDAO.findByUsername(username).getMess_id();
        model.addAttribute("role", "mess_head");
        sectionDAO.insert(section.getSection_id(), mess_id, section.getHall_no(), section.getBreakfast(),
                section.getLunch(), section.getDinner());

        return "redirect:/admin/allsections";
    }

    // @GetMapping("/admin/deletesection/{id}")
    // public String deletesectionDashboard(@PathVariable("id") Integer id, Model model, HttpSession session,
    //         RedirectAttributes redirectAttributes) {

    //     String Message = "Please Sign in to proceed!!!";
    //     if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
    //         toastService.redirectWithErrorToast(redirectAttributes, Message);
    //         return "redirect:/login";
    //     }
    //     String username = auth_Service.getCurrentUser(session);

    //     model.addAttribute("role", "mess_head");
    //     sectionDAO.delete(id);
    //     return "redirect:/admin/allsections";
    // }

    @GetMapping("/admin/allemployees")
    public String secemployeesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");
        String username = auth_Service.getCurrentUser(session);
        Employee employee = employeeDAO.findByUsername(username);

        List<Employee> employees = employeeDAO.findBymess(employee.getMess_id());
        System.out.println(employees.get(0));
        model.addAttribute("employees", employees);
        List<Section> sections = messDAO.getSectionsbyMess(employee.getMess_id());
        model.addAttribute("loggedinusername", username);

        model.addAttribute("sections", sections);
        return "listemployees";
    }

    @GetMapping("/admin/employee/{id}")
    public String employeebysecDashboard(@PathVariable("id") Integer id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");
        String username = auth_Service.getCurrentUser(session);
        Employee employee = employeeDAO.findByUsername(username);

        List<Employee> employees = employeeDAO.findBymessandsection(employee.getMess_id(), id);

        // yaha ana haii section wise employee banan hai

        // System.out.println(employees.get(0));
        List<Section> sections = messDAO.getSectionsbyMess(employee.getMess_id());
        model.addAttribute("employees", employees);
        model.addAttribute("loggedinUser", username);
        model.addAttribute("sections", sections);

        return "listemployees";
    }

    @GetMapping("/admin/employee/add")
    public String adminemployeeAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");

        String username = auth_Service.getCurrentUser(session);

        String curr_user = auth_Service.getCurrentUser(session);

        Employee employee = new Employee();

        model.addAttribute("employee", employee);
        model.addAttribute("loggedinUser", curr_user);

        return "addemployee";
    }

    @PostMapping("/admin/employee/add")
    public String gadminemployeeAddDashboardPost(@ModelAttribute("employee") Employee employee, Model model,
            HttpSession session) {
        String username = auth_Service.getCurrentUser(session);
        Employee admin = employeeDAO.findByUsername(username);

        employee.setMess_id(admin.getMess_id());
        employeeDAO.save(employee);

        return "redirect:/admin/allemployees";
    }

    @GetMapping("/admin/employee/makesectionhead/{id}")
    public String makesectionhead(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes,
            HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");
        String username = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", username);
        employeeDAO.makesectionhead(id);

        return "redirect:/admin/allemployees";
    }

    // EMPLOYEES

    // STUDENTS

    // @GetMapping("/dashboard/student/delete/{id}")
    // public String studentDeleteDashboard(@PathVariable("id") int id, Model model,
    // HttpSession session,
    // RedirectAttributes redirectAttributes) {

    // String Message = "Sorry, You are not authorized to view this page!. Please
    // Sign in as admin to proceed .......";
    // if (!auth_Service.isAuthenticated(session) ||
    // !auth_Service.isGadmin(session)) {
    // toastService.redirectWithErrorToast(redirectAttributes, Message);
    // return "redirect:/login";
    // }
    // String curr_user = auth_Service.getCurrentUser(session);
    // Student student = studentDAO.findByid(id);
    // int roll = student.getRoll_no();

    // List<Transaction> transactions = transactionDAO.alltransactions(roll, 1);

    // String TranMessage = "Sorry, Student has some transactions!";
    // if (transactions.isEmpty()) {
    // toastService.redirectWithErrorToast(redirectAttributes, TranMessage);
    // return "redirect:/dashboard/manage/students";
    // }

    // studentDAO.delete(id);

    // model.addAttribute("role", "Gadmin");
    // model.addAttribute("loggedinUser", curr_user);

    // return "redirect:/dashboard/manage/students";
    // }

    // CUSTOMERS

    @GetMapping("/admin/customers/{id}")
    public String customersDashboardsection(@PathVariable("id") int section_id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String username = auth_Service.getCurrentUser(session);
        Integer mess_id = employeeDAO.findByUsername(username).getMess_id();
        List<Customer> customers = customerDAO.allCustomersbySection(mess_id, section_id);

        List<Section> sections = messDAO.getSectionsbyMess(section_id);
        model.addAttribute("customers", customers);
        model.addAttribute("sections", sections);

        model.addAttribute("loggedinUser", username);
        return "listcustomers";
    }

    @GetMapping("/admin/customers")
    public String customersDashboard(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String username = auth_Service.getCurrentUser(session);
        Integer mess_id = employeeDAO.findByUsername(username).getMess_id();
        List<Customer> customers = customerDAO.findbymess(mess_id);
        List<Section> sections = messDAO.getSectionsbyMess(mess_id);
        model.addAttribute("role", "mess_head");
        model.addAttribute("customers", customers);
        model.addAttribute("sections", sections);
        model.addAttribute("loggedinUser", username);
        return "listcustomers";
    }

    @GetMapping("/admin/students/{id}")
    public String studentsDashboardsection(@PathVariable("id") int section_id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String username = auth_Service.getCurrentUser(session);
        Integer mess_id = employeeDAO.findByUsername(username).getMess_id();
        List<Student> students = studentDAO.findstudents(mess_id, section_id);

        List<Section> sections = messDAO.getSectionsbyMess(mess_id);
        model.addAttribute("students", students);
        model.addAttribute("sections", sections);

        model.addAttribute("loggedinUser", username);
        return "liststudents";
    }

    @GetMapping("/admin/students")
    public String studentDashboard(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String username = auth_Service.getCurrentUser(session);
        Integer mess_id = employeeDAO.findByUsername(username).getMess_id();
        List<Student> students = studentDAO.findstudentsbymess(mess_id);
        List<Section> sections = messDAO.getSectionsbyMess(mess_id);
        model.addAttribute("role", "mess_head");

        model.addAttribute("students", students);
        model.addAttribute("sections", sections);
        model.addAttribute("loggedinUser", username);
        return "liststudents";
    }

    @GetMapping("/admin/student/add")
    public String studentAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "admin");
        String curr_user = auth_Service.getCurrentUser(session);

        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("loggedinuser", curr_user);

        return "addstudent";
    }

    @PostMapping("/admin/student/add")
    public String studentAddDashboardPost(@ModelAttribute("student") Student student, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);
        Employee employee = employeeDAO.findByUsername(curr_user);

        student.setMess_id(employee.getMess_id());

        studentDAO.save(student);

        return "redirect:/admin/students";
    }

    @GetMapping("/admin/manage/student/delete/{id}")
    public String studentDeleteDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);

        studentDAO.delete(id);

        model.addAttribute("role", "section admin");
        model.addAttribute("loggedinusername", curr_user);

        return "redirect:/admin/students";
    }
    @GetMapping("/admin/manage/student/profile/{id}")
    public String studentprofileDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);

        Student student=studentDAO.findByid(id);
        
        model.addAttribute("student", student);
        model.addAttribute("role", "section admin");
        model.addAttribute("loggedinusername", curr_user);

        return "studentprofile";
    }

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
            return "redirect:/loggedin";
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


}
