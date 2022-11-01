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

    // STUDENTS

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String username = auth_Service.getCurrentUser(session);

        if (!(username.equals("admin"))) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        model.addAttribute("loggedinUser", username);

        return "dashboard/starting";

    }

    @GetMapping("/gadmin/allstudents")
    public String studentsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String username = auth_Service.getCurrentUser(session);
        List<Student> students = studentDAO.allStudents();

        model.addAttribute("students", students);
        model.addAttribute("loggedinUser", username);

        return "liststudents";
    }

    @GetMapping("/gadmin/allmess")
    public String messDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String username = auth_Service.getCurrentUser(session);
        List<Mess> messes = messDAO.getallMess();

        model.addAttribute("mess", messes);
        model.addAttribute("loggedinusername", username);

        return "listmess";
    }

    @GetMapping("/gadmin/makemesshead/{id}")
    public String makemesshead(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");

        String curr_user = auth_Service.getCurrentUser(session);
        Employee employee = employeeDAO.findByid(id);
        int mess_id=employee.getMess_id();
        if(messDAO.getmess(mess_id).get(0).getHead_id()!=null || !employee.getDesignation().equals("mess_head")){
            return "redirect:/gadmin/allemployees";
        }
        messDAO.makeMesshead(employee.getMess_id(), id);
        return "redirect:/gadmin/allmess";

    }

    @GetMapping("/dashboard/manage/student/{username}")
    public String studentDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Student student = studentDAO.findByUsername(username);

        model.addAttribute("student", student);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/student";
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

        model.addAttribute("loggedinusername", curr_user);

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

    @PostMapping("/dashboard/manage/student/{username}")
    public String studentDashboardPost(@PathVariable("username") String username, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/student/edit/" + username;
    }

    @GetMapping("/dashboard/manage/student/edit/{username}")
    public String studentEditDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Student student = studentDAO.findByUsername(username);

        model.addAttribute("student", student);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/updateStudent";
    }

    @PostMapping("/dashboard/manage/student/edit/{username}")
    public String studentEditDashboardPost(@PathVariable("username") String username,
            @ModelAttribute("student") Student student, Model model, HttpSession session) {
        studentDAO.update(student.getRoll_no(), student.getRoom_no(), student.getAge(), student.getBalance(),
                student.getDOB(),
                student.getF_name(), student.getL_name(), student.getHostel_name(), student.getSex(),
                student.getParent(),
                student.getPhone_no(), student.getS_email(), student.getLocalGaurdian(), student.getAadhar_no(),
                student.getS_account_no(), student.getS_ifsc(), student.getMess_id(), student.getSection_id(),
                username);
        return "redirect:/dashboard/manage/student/" + username;
    }

    @GetMapping("/dashboard/student/delete/{username}")
    public String studentDeleteDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Student student = studentDAO.findByUsername(username);
        int roll = student.getRoll_no();

        List<Transaction> transactions = transactionDAO.alltransactions(roll, 1);

        String TranMessage = "Sorry, Student has some transactions!";
        if (transactions.isEmpty()) {
            toastService.redirectWithErrorToast(redirectAttributes, TranMessage);
            return "redirect:/dashboard/manage/students";
        }

        studentDAO.delete(username);

        model.addAttribute("curr_user", curr_user);

        return "redirect:/dashboard/manage/students";
    }

    @GetMapping("/dashboard/student/add")
    public String studentAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("curr_user", curr_user);

        return "dashboard/addStudent";
    }

    @PostMapping("/dashboard/student/add")
    public String studentAddDashboardPost(@ModelAttribute("student") Student student, Model model,
            HttpSession session) {

        studentDAO.save(student);

        return "redirect:/dashboard/manage/students";
    }

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

        List<Section> sections = messDAO.getSectionsbyMess(section_id);
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
        model.addAttribute("students", students);
        model.addAttribute("sections", sections);
        model.addAttribute("loggedinUser", username);
        return "liststudents";
    }

    @GetMapping("/gadmin/allcustomers")
    public String getAllCustomers(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        List<Customer> customers = customerDAO.allCustomers();
        model.addAttribute("customers", customers);
        return "listcustomers";
    }

    @GetMapping("/dashboard/manage/customer/{username}")
    public String customerDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Customer customer = customerDAO.findByUsername(username);

        model.addAttribute("customer", customer);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/customer";
    }

    @PostMapping("/dashboard/manage/customer/{username}")
    public String customerDashboardPost(@PathVariable("username") String username, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/customer/edit/" + username;
    }

    @GetMapping("/dashboard/manage/customer/edit/{username}")
    public String customerEditDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Customer customer = customerDAO.findByUsername(username);

        model.addAttribute("customer", customer);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/updateCustomer";
    }

    @PostMapping("/dashboard/manage/customer/edit/{username}")
    public String customerEditDashboardPost(@PathVariable("username") String username,
            @ModelAttribute("customer") Customer customer, Model model, HttpSession session) {
        customerDAO.update(customer.getCid(), customer.getBalance(), customer.getPin(), customer.getPhone_no(),
                customer.getC_aadhar_number(),
                customer.getAccount_no(), customer.getSex(), customer.getIfsc(), customer.getDob(),
                customer.getFirst_name(),
                customer.getLast_name(), customer.getEmail(), customer.getCity(), customer.getStreet(),
                customer.getMess_id(), customer.getSection_id(), username);
        return "redirect:/dashboard/manage/customer/" + username;
    }

    @GetMapping("/dashboard/customer/delete/{username}")
    public String customerDeleteDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Customer customer = customerDAO.findByUsername(username);
        int roll = customer.getCid();

        List<Transaction> transactions = transactionDAO.alltransactions(roll, 1);

        String TranMessage = "Sorry, Customer has some transactions!";
        if (transactions.isEmpty()) {
            toastService.redirectWithErrorToast(redirectAttributes, TranMessage);
            return "redirect:/dashboard/manage/customers";
        }

        customerDAO.delete(username);

        model.addAttribute("curr_user", curr_user);

        return "redirect:/dashboard/manage/customers";
    }

    @GetMapping("/dashboard/customer/add")
    public String customerAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Customer customer = new Customer();

        model.addAttribute("customer", customer);
        model.addAttribute("curr_user", curr_user);

        return "dashboard/addCustomer";
    }

    @PostMapping("/dashboard/customer/add")
    public String customerAddDashboardPost(@ModelAttribute("customer") Customer customer, Model model,
            HttpSession session) {

        customerDAO.save(customer);

        return "redirect:/dashboard/manage/customers";
    }

    // EMPLOYEES

    @GetMapping("/gadmin/allemployees")
    public String employeesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String username = auth_Service.getCurrentUser(session);
        List<Employee> employees = employeeDAO.allEmployees();
        System.out.println(employees.get(0));
        model.addAttribute("employees", employees);

        model.addAttribute("loggedinUser", username);
        return "listemployees";
    }

    @GetMapping("/dashboard/manage/employee/{username}")
    public String employeeDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Employee employee = employeeDAO.findByUsername(username);

        model.addAttribute("employee", employee);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/employee";
    }

    @PostMapping("/dashboard/manage/employee/{username}")
    public String employeeDashboardPost(@PathVariable("username") String username, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/employee/edit/" + username;
    }

    @GetMapping("/dashboard/manage/employee/edit/{username}")
    public String employeeEditDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Employee employee = employeeDAO.findByUsername(username);

        model.addAttribute("employee", employee);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/updateEmployee";
    }

    @PostMapping("/dashboard/manage/employee/edit/{username}")
    public String employeeEditDashboardPost(@PathVariable("username") String username,
            @ModelAttribute("employee") Employee employee, Model model, HttpSession session) {
        employeeDAO.update(employee.getEid(), employee.getSalary(), employee.getAge(), employee.getPin(),
                employee.getDob(),
                employee.getIfsc(), employee.getAccount_no(), employee.getE_aadhar_number(), employee.getFirst_name(),
                employee.getLast_name(),
                employee.getDesignation(), employee.getEmail(), employee.getCity(), employee.getStreet(),
                employee.getMess_id(), employee.getSection_id(), username);
        return "redirect:/dashboard/manage/employee/" + username;
    }

    @GetMapping("/dashboard/employee/delete/{username}")
    public String employeeDeleteDashboard(@PathVariable("username") String username, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        employeeDAO.delete(username);

        model.addAttribute("curr_user", curr_user);

        return "redirect:/dashboard/manage/employees";
    }

    @GetMapping("/gadmin/employee/add")
    public String employeeAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");

        String username = auth_Service.getCurrentUser(session);

        String curr_user = auth_Service.getCurrentUser(session);

        Employee employee = new Employee();

        model.addAttribute("employee", employee);
        model.addAttribute("loggedinusername", curr_user);

        return "addemployee";
    }

    @PostMapping("/dashboard/employee/add")
    public String employeeAddDashboardPost(@ModelAttribute("employee") Employee employee, Model model,
            HttpSession session) {

        employeeDAO.save(employee);

        return "redirect:/gadmin/allemployees";
    }

}
