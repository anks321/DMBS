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
	private SectionDAO sectionDAO;
	@Autowired
	private StudentDAO studentDAO;
	@Autowired
	private CustomerDAO customerDAO;  
	@Autowired
	private AnnouncementsDAO announcementsDAO;
    @Autowired
	private TransactionDAO transactionDAO;
	@Autowired
    private UserValidator userValidator;
    @Autowired
    private ToastService toastService;
	
	@GetMapping("/showAnnouncements/{mess_no}/{section_no}")
    public String addannounce(@PathVariable("mess_no") int mess_no, @PathVariable("section_no") int section_no, HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String username = auth_Service.getCurrentUser(session);

        if(username != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Student student = studentDAO.findByUsername(username);

		List<Announcements> announcements = announcementsDAO.findByAnnouncements(mess_no, section_no);
        
        model.addAttribute("announcements", announcements);
        return "studentprofile";
    }

	// STUDENTS

	@GetMapping("/dashboard/manage/students")
    public String studentsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String username = auth_Service.getCurrentUser(session);

        if(username != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        List<Student> students = studentDAO.allStudents();

        model.addAttribute("students", students);

        model.addAttribute("loggedinUser", username);
        return "dashboard/students";
    }

    @GetMapping("/dashboard/manage/student/{username}")
    public String studentDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

		Student student = studentDAO.findByUsername(username);

        model.addAttribute("student", student);

        model.addAttribute("curr_user", curr_user);

        return "dashboard/student";
    }

    @PostMapping("/dashboard/manage/student/{username}")
    public String studentDashboardPost(@PathVariable("username") String username, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/student/edit/" + username;
    }

    @GetMapping("/dashboard/manage/student/edit/{username}")
    public String studentEditDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
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
		studentDAO.update(student.getRoll_no(), student.getRoom_no(), student.getAge(), student.getBalance(), student.getDOB(),
				student.getF_name(), student.getL_name(), student.getHostel_name(), student.getSex(), student.getParent(),
				student.getPhone_no(),student.getS_email(),student.getLocalGaurdian(),student.getAadhar_no(),
				student.getS_account_no(),student.getS_ifsc(),student.getMess_id(),student.getSection_id(),username);
        return "redirect:/dashboard/manage/student/" + username;
    }

    @GetMapping("/dashboard/student/delete/{username}")
    public String studentDeleteDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
       
		Student student = studentDAO.findByUsername(username);
		int roll = student.getRoll_no();

		List<Transaction> transactions = transactionDAO.alltransactions(roll,1);

        String TranMessage = "Sorry, Student has some transactions!";
		if(transactions.isEmpty())
        {
            toastService.redirectWithErrorToast(redirectAttributes,TranMessage);
            return "redirect:/dashboard/manage/students";
        }

		studentDAO.delete(username);

		model.addAttribute("curr_user", curr_user);

        return "redirect:/dashboard/manage/students";
    }

    @GetMapping("/dashboard/student/add")
    public String studentAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("curr_user", curr_user);

        return "dashboard/addStudent";
    }

    @PostMapping("/dashboard/student/add")
    public String studentAddDashboardPost(@ModelAttribute("student") Student student, Model model, HttpSession session) {

        studentDAO.save(student);

        return "redirect:/dashboard/manage/students";
    }

	// CUSTOMERS
	
	@GetMapping("/dashboard/manage/customers")
    public String customersDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String username = auth_Service.getCurrentUser(session);

        if(username != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        List<Customer> customers = customerDAO.allCustomers();

        model.addAttribute("customers", customers);

        model.addAttribute("loggedinUser", username);
        return "dashboard/customers";
    }

    @GetMapping("/dashboard/manage/customer/{username}")
    public String customerDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
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
    public String customerEditDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
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
		customerDAO.update(customer.getCid(), customer.getBalance(), customer.getPin(), customer.getPhone_no(), customer.getC_aadhar_number(),
		customer.getAccount_no(), customer.getSex(), customer.getIfsc(), customer.getDob(), customer.getFirst_name(),
		customer.getLast_name(),customer.getEmail(),customer.getCity(),customer.getStreet(),customer.getMess_id(),customer.getSection_id(),username);
        return "redirect:/dashboard/manage/customer/" + username;
    }

    @GetMapping("/dashboard/customer/delete/{username}")
    public String customerDeleteDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!!!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
       
		Customer customer = customerDAO.findByUsername(username);
		int roll = customer.getCid();

		List<Transaction> transactions = transactionDAO.alltransactions(roll,1);

        String TranMessage = "Sorry, Customer has some transactions!";
		if(transactions.isEmpty())
        {
            toastService.redirectWithErrorToast(redirectAttributes,TranMessage);
            return "redirect:/dashboard/manage/customers";
        }

		customerDAO.delete(username);

		model.addAttribute("curr_user", curr_user);

        return "redirect:/dashboard/manage/customers";
    }

    @GetMapping("/dashboard/customer/add")
    public String customerAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Sorry, You are not authorized to view this page!. Please Sign in as admin to proceed!";

		String curr_user = auth_Service.getCurrentUser(session);

        if(curr_user != "admin")
        {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        Customer customer = new Customer();

        model.addAttribute("customer", customer);
        model.addAttribute("curr_user", curr_user);

        return "dashboard/addCustomer";
    }

    @PostMapping("/dashboard/customer/add")
    public String customerAddDashboardPost(@ModelAttribute("customer") Customer customer, Model model, HttpSession session) {

        customerDAO.save(customer);

        return "redirect:/dashboard/manage/customers";
    }

    
}
