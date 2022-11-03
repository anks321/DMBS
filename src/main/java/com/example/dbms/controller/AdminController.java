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
        if (messDAO.getmess(mess_id).get(0).getHead_id() != null || !employee.getDesignation().equals("mess_head")) {
            return "redirect:/gadmin/allemployees";
        }
        messDAO.makeMesshead(employee.getMess_id(), id);
        return "redirect:/gadmin/allmess";

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

    @GetMapping("/admin/deletesection/{id}")
    public String deletesectionDashboard(@PathVariable("id") Integer id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String username = auth_Service.getCurrentUser(session);

        model.addAttribute("role", "mess_head");
        sectionDAO.delete(id);
        return "redirect:/admin/allsections";
    }


    @GetMapping("/admin/allemployees")
    public String secemployeesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");
        String username = auth_Service.getCurrentUser(session);
        Employee employee =employeeDAO.findByUsername(username);

        List<Employee> employees = employeeDAO.findBymess(employee.getMess_id());
        System.out.println(employees.get(0));
        model.addAttribute("employees", employees);

        model.addAttribute("loggedinUser", username);
        return "listemployees";
    }
    @GetMapping("/admin/employee/{id}")
    public String employeebysecDashboard(@PathVariable("id") Integer id,Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "mess_head");
        String username = auth_Service.getCurrentUser(session);
        Employee employee =employeeDAO.findByUsername(username);

        List<Employee> employees = employeeDAO.find(employee.getMess_id());

        // yaha ana haii section wise employee banan hai

        System.out.println(employees.get(0));
        model.addAttribute("employees", employees);
        model.addAttribute("loggedinUser", username);
        return "listemployees";
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

    @GetMapping("/gadmin/manage/employee/{id}")
    public String employeeDashboard(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";

        String curr_user = auth_Service.getCurrentUser(session);

        Employee employee = employeeDAO.findByid(id);

        model.addAttribute("employee", employee);

        model.addAttribute("loggedinuser", curr_user);

        return "updateemployee";
    }

    @PostMapping("/gadmin/manage/employee/edit/{id}")
    public String employeeEditDashboardPost(@PathVariable("id") int id,
            @ModelAttribute("employee") Employee employee, Model model, HttpSession session) {
        employeeDAO.update(id, employee.getSalary(), employee.getAge(), employee.getPhone_no(),
                employee.getPin(), employee.getDob(),
                employee.getIfsc(), employee.getAccount_no(), employee.getE_aadhar_number(), employee.getFirst_name(),
                employee.getLast_name(),
                employee.getDesignation(), employee.getEmail(), employee.getCity(), employee.getStreet(),
                employee.getMess_id(), employee.getSection_id(), employee.getUsername());
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

        model.addAttribute("loggedinUser", curr_user);

        return "redirect:/gadmin/allemployees";
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
        model.addAttribute("loggedinUser", curr_user);

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
        System.out.println(students.get(0));

        model.addAttribute("students", students);
        model.addAttribute("loggedinUser", username);

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

    @GetMapping("/dashboard/student/delete/{id}")
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
        model.addAttribute("loggedinUser", curr_user);

        return "redirect:/dashboard/manage/students";
    }

    @GetMapping("/gadmin/student/add")
    public String studentAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");

        String curr_user = auth_Service.getCurrentUser(session);

        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("loggedinUser", curr_user);

        return "addstudent";
    }

    @PostMapping("/gadmin/student/add")
    public String studentAddDashboardPost(@ModelAttribute("student") Student student, Model model,
            HttpSession session) {

        studentDAO.save(student);

        return "redirect:/gadmin/allstudents";
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
        model.addAttribute("role", "mess_head");

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
        String username = auth_Service.getCurrentUser(session);

        List<Customer> customers = customerDAO.allCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("loggedinUser", username);
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

        model.addAttribute("loggedinUser", curr_user);

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


    @GetMapping("/gadmin/student/add")
    public String customerAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isGadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "Gadmin");
        String curr_user = auth_Service.getCurrentUser(session);


        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("loggedinuser", curr_user);

        return "addstudent";
    }   

    @PostMapping("/gadmin/customer/add")
    public String customerAddDashboardPost(@ModelAttribute("customer") Customer customer, Model model,
            HttpSession session) {

        customerDAO.save(customer);

        return "redirect:/gadmin/allcustomers";
    }

    // INVENTORIES
    // -------------------------------------------------------------------------------------
    @GetMapping("/dashboard/manage/inventories")
    public String inventoriesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed .......";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";

        String username = auth_Service.getCurrentUser(session);

        if (username != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        List<Inventory> inventories = inventoryDAO.allInventories();

        model.addAttribute("inventories", inventories);

        model.addAttribute("UserLoggedIn", username);
        return "dashboard/inventories";
    }

    @GetMapping("/dashboard/manage/inventory/{item_id}")
    public String inventoryDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed .......";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Inventory inventory = inventoryDAO.findByItem_Id(Item_Id);

        model.addAttribute("inventory", inventory);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/inventory";
    }

    @PostMapping("/dashboard/manage/inventory/{Item_Id}")
    public String inventoryDashboardPost(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/inventory/edit/" + Item_Id;
    }

    @GetMapping("/dashboard/manage/inventory/edit/{Item_Id}")
    public String inventoryEditDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed .......";
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

        Inventory inventory = inventoryDAO.findByItem_Id(Item_Id);

        model.addAttribute("inventory", inventory);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/updateInventory";
    }

    @PostMapping("/dashboard/manage/inventory/edit/{Item_Id}")
    public String inventoryEditDashboardPost(@PathVariable("Item_Id") int Item_Id,
            @ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session) {
        inventoryDAO.update(inventory.getCost(), inventory.getQuantity(), inventory.getExpiry_date(),
                inventory.getName(), inventory.getMess_id(), inventory.getSection_id(), Item_Id);
        return "redirect:/dashboard/manage/inventory/" + Item_Id;
    }

    @GetMapping("/dashboard/inventory/delete/{Item_Id}")
    public String inventoryDeleteDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed .......";
        if (!auth_Service.isAuthenticated(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed .......";

        String curr_user = auth_Service.getCurrentUser(session);

        if (curr_user != "admin") {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        inventoryDAO.delete(Item_Id);

        model.addAttribute("curr_user", curr_user);

        return "redirect:/dashboard/manage/inventories";
    }

    @GetMapping("/dashboard/inventory/add")
    public String inventoryAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed .......";
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

        Inventory inventory = new Inventory();

        model.addAttribute("employee", inventory);
        model.addAttribute("loggedinUser", curr_user);

        return "dashboard/addInventory";
    }

    @PostMapping("/dashboard/inventory/add")
    public String inventoryAddDashboardPost(@ModelAttribute("inventory") Inventory inventory, Model model,
            HttpSession session) {

        inventoryDAO.save(inventory);

        return "redirect:/dashboard/manage/inventories";
    }

}
