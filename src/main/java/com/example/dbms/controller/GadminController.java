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
public class GadminController {

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
        
        int mess_id = employee.getMess_id();
        model.addAttribute("loggedinusername", curr_user);
        if (messDAO.getmessbyid(mess_id).getHead_id() != null || employee.getDesignation().equals("mess_head")) {
            return "redirect:/gadmin/allemployees";
        }
        employeeDAO.makemesshead(id);
        messDAO.makeMesshead(employee.getMess_id(), id);
        return "redirect:/gadmin/allmess";
    }

    @GetMapping("/gadmin/addmess")
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

    @PostMapping("/gadmin/addmess")
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
        sectionDAO.insert(1, mess.getMess_id(), 0, "Null", "Null", "Null");
        return "redirect:/gadmin/allmess";
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
        model.addAttribute("employees", employees);

        model.addAttribute("loggedinusername", username);
        return "listemployees";
    }

    @GetMapping("/gadmin/employee/profile/{id}")
    public String employeeprofile(@PathVariable("id") Integer id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Employee employee = employeeDAO.findByid(id);
        model.addAttribute("employee", employee);
        return "employeeprofile";
    }

    @GetMapping("/gadmin/manage/employee/{id}")
    public String employeeDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || (!auth_Service.isGadmin(session)
                && !auth_Service.isadmin(session))) {
            System.out.println("fin");
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        if (auth_Service.isGadmin(session)) {
            model.addAttribute("role", "Gadmin");
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";

        String curr_user = auth_Service.getCurrentUser(session);

        Employee employee = employeeDAO.findByid(id);

        model.addAttribute("employee", employee);

        model.addAttribute("loggedinuser", curr_user);

        return "updateemployee";
    }

    @PostMapping("/gadmin/manage/employee/edit/{id}")
    public String employeeEditDashboardPost(@PathVariable("id") Integer id,
            @ModelAttribute("employee") Employee employee, Model model, HttpSession session) {

        String curr_user = auth_Service.getCurrentUser(session);

        Employee curremployee = employeeDAO.findByid(id);
        Integer mess_id = employee.getMess_id();
        if (curremployee.getMess_id() != null) {
            mess_id = curremployee.getMess_id();
        }
        employeeDAO.update(id, employee.getPassword(), employee.getSalary(), employee.getAge(), employee.getPhone_no(),
                employee.getPin(), employee.getDob(),
                employee.getIfsc(), employee.getAccount_no(), employee.getE_aadhar_number(), employee.getFirst_name(),
                employee.getLast_name(),
                employee.getDesignation(), employee.getEmail(), employee.getCity(), employee.getStreet(),
                mess_id, employee.getSection_id(), employee.getUsername());
        return "redirect:/gadmin/allemployees";
    }

    @GetMapping("/gadmin/manage/employee/delete/{id}")
    public String employeeDeleteDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        employeeDAO.delete(id);

        model.addAttribute("loggedinusername", curr_user);

        return "redirect:/gadmin/allemployees";
    }

    @GetMapping("/gadmin/mess/profile/{id}")
    public String messprofile(@PathVariable("id") Integer id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Mess mess = messDAO.getmessbyid(id);
        model.addAttribute("mess", mess);
        return "messprofile";
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

    @PostMapping("/gadmin/employee/add")
    public String employeeAddDashboardPost(@ModelAttribute("employee") Employee employee, Model model,
            HttpSession session) {

        employeeDAO.save(employee);

        return "redirect:/gadmin/allemployees";
    }

    // STUDENTS

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
        model.addAttribute("loggedinusername", username);

        return "liststudents";
    }

    @GetMapping("/gadmin/manage/student/{id}")
    public String studentDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByid(id);
        model.addAttribute("role", "Gadmin");

        model.addAttribute("student", student);

        model.addAttribute("loggedinuser", curr_user);

        return "updatestudent";
    }

    @PostMapping("/gadmin/manage/student/edit/{id}")
    public String studentEditDashboardPost(@PathVariable("id") int id,
            @ModelAttribute("student") Student student, Model model, HttpSession session) {
        studentDAO.update(id, student.getRoom_no(), student.getBalance(),
                student.getF_name(), student.getL_name(), student.getHostel_name(), student.getSex(),
                student.getParent(),
                student.getPhone_no(), student.getS_email(), student.getLocalGaurdian(), student.getAadhar_no(),
                student.getS_account_no(), student.getS_ifsc(), student.getMess_id(), student.getSection_id(),
                student.getUsername());
        return "redirect:/gadmin/allstudents";
    }

    @GetMapping("/gadmin/manage/student/delete/{id}")
    public String studentDeleteDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
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

        model.addAttribute("role", "Gadmin");
        model.addAttribute("loggedinusername", curr_user);

        return "redirect:/localadmin/allstudents";
    }

    // @GetMapping("/gadmin/student/add")
    // public String studentAddDashboard(Model model, HttpSession session,
    // RedirectAttributes redirectAttributes) {

    // String Message = "Sorry, You are not authorized to view this page!. Please
    // Sign in as admin to proceed .......";
    // if (!auth_Service.isAuthenticated(session) ||
    // !auth_Service.isGadmin(session)) {
    // toastService.redirectWithErrorToast(redirectAttributes, Message);
    // return "redirect:/login";
    // }
    // model.addAttribute("role", "Gadmin");
    // String curr_user = auth_Service.getCurrentUser(session);

    // Student student = new Student();

    // model.addAttribute("student", student);
    // model.addAttribute("loggedinuser", curr_user);

    // return "addstudent";
    // }

    // @PostMapping("/gadmin/student/add")
    // public String studentAddDashboardPost(@ModelAttribute("student") Student
    // student, Model model,
    // HttpSession session) {

    // studentDAO.save(student);

    // return "redirect:/gadmin/allstudents";
    // }

    // CUSTOMERS

    @GetMapping("/gadmin/allcustomers")
    public String getAllCustomers(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String username = auth_Service.getCurrentUser(session);

        List<Customer> customers = customerDAO.allCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("loggedinusername", username);
        return "listcustomers";
    }

    @GetMapping("/gadmin/manage/customer/{id}")
    public String customerDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByid(id);
        model.addAttribute("role", "Gadmin");

        model.addAttribute("customer", customer);

        model.addAttribute("loggedinusername", curr_user);

        return "updatecustomer";
    }

    @PostMapping("/gadmin/manage/customer/edit/{id}")
    public String customerEditDashboardPost(@PathVariable("id") int id,
            @ModelAttribute("customer") Customer customer, Model model, HttpSession session) {
        customerDAO.update(customer.getBalance(), customer.getPin(), customer.getPhone_no(),
                customer.getC_aadhar_number(),
                customer.getAccount_no(), customer.getSex(), customer.getIfsc(), customer.getFirst_name(),
                customer.getLast_name(), customer.getEmail(), customer.getCity(), customer.getStreet(),
                customer.getMess_id(), customer.getSection_id(), id);
        return "redirect:/gadmin/allcustomers";
    }

    @GetMapping("/gadmin/mess/delete/{id}")
    public String deletemess(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);
        messDAO.deletemess(id);
        return "redirect:/gadmin/allmess";

    }

    @GetMapping("/gadmin/manage/customer/delete/{id}")
    public String customerDeleteDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
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

        model.addAttribute("role", "Gadmin");
        model.addAttribute("loggedinuser", curr_user);

        return "redirect:/gadmin/allcustomers";
    }

    @GetMapping("/gadmin/customer/add")
    public String customerAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = new Customer();

        model.addAttribute("customer", customer);
        model.addAttribute("loggedinuser", curr_user);

        return "addcustomer";
    }

    @PostMapping("/gadmin/customer/add")
    public String customerAddDashboardPost(@ModelAttribute("customer") Customer customer, Model model,
            HttpSession session) {

        customerDAO.save(customer);

        return "redirect:/gadmin/allcustomers";
    }

}
