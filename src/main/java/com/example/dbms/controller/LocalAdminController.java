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

    // STUDENTS

    @GetMapping("/localadmin/allstudents")
    public String studentsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section_admin");
        String username = auth_Service.getCurrentUser(session);
        List<Student> students = studentDAO.allStudents();

        model.addAttribute("students", students);
        model.addAttribute("loggedinusername", username);

        return "viewstudents";
    }

    @GetMapping("/localadmin/student/profile/{id}")
    public String profile(@PathVariable("id") int id, Model model, HttpSession session,
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

        return "studentprofile";
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
        model.addAttribute("role", "section_admin");
        String username = auth_Service.getCurrentUser(session);

        List<Customer> customers = customerDAO.allCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("loggedinusername", username);
        return "viewcustomers";
    }

    @GetMapping("/localadmin/customer/profile/{id}")
    public String custprofile(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByid(id);
        System.out.println(customer);
        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("customer", customer);
        model.addAttribute("role", "section_admin");

        return "customerprofile";

    }

    // INVENTORIES

    @GetMapping("/localadmin/allinventories")
    public String inventoriesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section_admin");
        String username = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        List<Inventory> Inventories = inventoryDAO.findByInventories(mess_no, section_no);

        model.addAttribute("inventories", Inventories);

        model.addAttribute("loggedinusername", username);
        return "listinventories";
    }

    @GetMapping("/localadmin/manage/inventory/{Item_Id}")
    public String inventoryDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Inventory inventory = inventoryDAO.findByItem_Id(Item_Id);

        model.addAttribute("inventory", inventory);

        model.addAttribute("loggedinusername", curr_user);

        return "updateinventory";
    }

    @PostMapping("/localadmin/manage/inventory/edit/{Item_Id}")
    public String inventoryEditDashboardPost(@PathVariable("Item_Id") int Item_Id,
            @ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);
        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        inventory.setMess_id(mess_no);
        inventory.setSection_id(section_no);

        inventoryDAO.update(inventory.getCost(), inventory.getQuantity(), inventory.getName(), Item_Id);
        return "redirect:/localadmin/allinventories";
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
        model.addAttribute("role", "section_admin");

        inventoryDAO.delete(Item_Id);

        model.addAttribute("loggedinusername", curr_user);

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
        model.addAttribute("role", "section_admin");
        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        Inventory inventory = new Inventory();
        inventory.setMess_id(mess_no);
        inventory.setSection_id(section_no);

        model.addAttribute("inventory", inventory);
        model.addAttribute("loggedinusername", curr_user);

        return "addinventory";
    }

    @PostMapping("/localadmin/inventory/add")
    public String inventoryAddDashboardPost(@ModelAttribute("inventory") Inventory inventory, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();
        inventory.setMess_id(mess_no);
        inventory.setSection_id(section_no);
        inventoryDAO.save(inventory);

        return "redirect:/localadmin/allinventories";
    }

    // ANNOUNCEMENTS

    @GetMapping("/localadmin/allannouncements")
    public String announcementsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section_admin");
        String username = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        List<Announcements> announcements = announcementsDAO.findByAnnouncements(mess_no, section_no);

        model.addAttribute("announcements", announcements);

        model.addAttribute("loggedinusername", username);
        return "listannouncements";
    }

    @GetMapping("/localadmin/manage/announcement/{Item_Id}")
    public String announcementDashboard(@PathVariable("Item_Id") int Item_Id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Announcements announcement = announcementsDAO.findByItem_Id(Item_Id);

        model.addAttribute("announcement", announcement);

        model.addAttribute("loggedinusername", curr_user);

        return "updateannouncement";
    }

    @PostMapping("/localadmin/manage/announcement/edit/{Item_Id}")
    public String announcementEditDashboardPost(@PathVariable("Item_Id") int Item_Id,
            @ModelAttribute("announcement") Announcements announcement, Model model, HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        announcement.setMess_id(mess_no);
        announcement.setSection_id(section_no);
        announcementsDAO.update(announcement.getAnnounce_text(), announcement.getDate_and_time(),
                announcement.getA_id());
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
        model.addAttribute("role", "section_admin");

        announcementsDAO.delete(Item_Id);

        model.addAttribute("loggedinusername", curr_user);

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
        model.addAttribute("role", "section_admin");

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        Announcements announcement = new Announcements();

        model.addAttribute("announcement", announcement);
        model.addAttribute("loggedinusername", curr_user);

        return "addannouncement";
    }

    @PostMapping("/localadmin/announcement/add")
    public String announcementAddDashboardPost(@ModelAttribute("announcement") Announcements announcement, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        int num_announce = announcementsDAO.countannounce();

        announcement.setA_id(num_announce + 1);
        announcement.setMess_id(mess_no);
        announcement.setSection_id(section_no);

        announcementsDAO.save(announcement);

        return "redirect:/localadmin/allannouncements";
    }

    // Menus

    @GetMapping("/localadmin/allmenues")
    public String menuesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        model.addAttribute("role", "section_admin");
        String username = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        int num_menu = sectionDAO.countmenu(mess_no, section_no);

        Section section = sectionDAO.findSection(mess_no, section_no);

        model.addAttribute("section", section);
        model.addAttribute("num_menu", num_menu);

        model.addAttribute("loggedinusername", username);
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
        model.addAttribute("role", "section_admin");

        String username = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(username);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        Section section = sectionDAO.findSection(mess_no, section_no);

        model.addAttribute("section", section);

        model.addAttribute("loggedinusername", username);

        return "updatelocalsection";
    }

    @PostMapping("/localadmin/manage/menue/edit")
    public String menueEditDashboardPost(@ModelAttribute("section") Section section, Model model, HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        sectionDAO.updateMenue(section.getBreakfast(), section.getLunch(), section.getDinner(), 
                                section_no, mess_no);
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
        model.addAttribute("role", "section_admin");

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        Section section = new Section();

        section.setMess_id(mess_no);
        section.setSection_id(section_no);

        model.addAttribute("section", section);
        model.addAttribute("loggedinusername", curr_user);

        return "addlocalsection";
    }

    @PostMapping("/localadmin/menue/add")
    public String menueAddDashboardPost(@ModelAttribute("section") Section section, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        section.setMess_id(mess_no);
        section.setSection_id(section_no);
        sectionDAO.save(section);

        return "redirect:/localadmin/allmenues";
    }

    @GetMapping("/localadmin/allpolls")
    public String getallpolls(Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        Employee emp = employeeDAO.findByUsername(curr_user);

        List<Questions> questions = questionDAO.getQuestionbySection(emp.getSection_id());
        model.addAttribute("questions", questions);
        model.addAttribute("loggedinusername", curr_user);

        return "pollquestionlist";

    }

    @GetMapping("/localadmin/addquestion")
    public String getaddpolls(Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        Employee emp = employeeDAO.findByUsername(curr_user);
        model.addAttribute("loggedinusername", curr_user);
        // List<Questions> questions =
        // questionDAO.getQuestionbySection(emp.getSection_id());
        model.addAttribute("question", new Questions());

        return "addquestion";

    }

    @PostMapping("/localadmin/addquestion")
    public String postaddpolls(@ModelAttribute("question") Questions question, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        Employee emp = employeeDAO.findByUsername(curr_user);

        int num_ques = questionDAO.countquestions();

        questionDAO.insertQuestion(num_ques + 1, emp.getMess_id(), emp.getSection_id(), question.getText(), 0);

        return "redirect:/localadmin/allpolls";

    }

    @GetMapping("/localadmin/addoptions/{id}")
    public String getaddoptions(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        Employee emp = employeeDAO.findByUsername(curr_user);
        model.addAttribute("loggedinusername", curr_user);

        model.addAttribute("option", new Options());
        model.addAttribute("questionid", id);

        return "addoption";

    }

    @PostMapping("/localadmin/addoptions/{id}")
    public String postaddoptions(@PathVariable("id") Integer id, @ModelAttribute("option") Options option,
            Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        Employee emp = employeeDAO.findByUsername(curr_user);
        
        int num_opts = optionsDAO.countoptions();

        optionsDAO.addOption(num_opts + 1, id, option.getOptionText());

        return "redirect:/localadmin/allpolls";
    }

    @GetMapping("/localadmin/viewoptions/{id}")
    public String getviewoptions(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        Employee emp = employeeDAO.findByUsername(curr_user);
        model.addAttribute("loggedinusername", curr_user);

        Questions questions = questionDAO.getQuestionbyId(id);
        
        List<Options> options = optionsDAO.getOptions(id);
        model.addAttribute("options", options);
        model.addAttribute("question", questions);

        return "viewoptions";

    }

    @GetMapping("/localadmin/deletequestions/{id}")
    public String getdeletequestion(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        model.addAttribute("loggedinusername", curr_user);

        Questions questions = questionDAO.getQuestionbyId(id);
        questionDAO.delete(id);

        return "redirect:/localadmin/allpolls";
    }

    @GetMapping("/localadmin/startpoll/{id}")
    public String startpoll(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        model.addAttribute("loggedinusername", curr_user);

        questionDAO.updatefinished(1, id);

        return "redirect:/localadmin/allpolls";
    }

    @GetMapping("/localadmin/endpoll/{id}")
    public String endpoll(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        model.addAttribute("loggedinusername", curr_user);

        questionDAO.updatefinished(0, id);

        return "redirect:/localadmin/allpolls";
    }

    @GetMapping("/localadmin/pollresult/{id}")
    public String getpollresult(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || (!auth_Service.issectionadmin(session) && !auth_Service.isstudent(session))) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        model.addAttribute("loggedinusername", curr_user);

        int opt_size = optionsDAO.countoptionsByQuestion(id);
        int[] arr = new int[opt_size];
        int[] narr = new int[opt_size];

        List<Options> opt_ions = optionsDAO.getOptions(id);
        for(int i=0;i<opt_size;i++){
            narr[i] = opt_ions.get(i).getOptionid();
        }

        for(int i=0;i<opt_size;i++){
            arr[i] = optDAO.countstudent(id, narr[i]);
        }

        Questions questions = questionDAO.getQuestionbyId(id);
        
        List<Options> options = optionsDAO.getOptions(id);
        model.addAttribute("options", options);
        model.addAttribute("question", questions);
        model.addAttribute("arr", arr);

        return "viewpollresult";
    }

    @GetMapping("/localadmin/forum")
    public String viewforums(Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        model.addAttribute("loggedinusername", curr_user);
        
        Employee emp = employeeDAO.findByUsername(curr_user);
        int mess_no = emp.getMess_id();
        int section_no = emp.getSection_id();

        List<Forum> forums = forumDAO.findByforums(mess_no, section_no);

        model.addAttribute("forums", forums);

        model.addAttribute("loggedinusername", curr_user);

        return "viewforums";

    }

    @GetMapping("/localadmin/resolveforum/{id}")
    public String resolveforums(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            // toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");
        model.addAttribute("loggedinusername", curr_user);
        
        Employee emp = employeeDAO.findByUsername(curr_user);
        Forum forums = forumDAO.getforumbyid(id);

        model.addAttribute("forums", forums);

        forumDAO.updateresolved(1, id);

        return "redirect:/localadmin/forum";

    }

    //STUDENTS

    @GetMapping("/localadmin/student/transactions/{roll}")
    public String studenttransactions(@PathVariable("roll") int roll, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByid(roll);
        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("student", student);
        model.addAttribute("role", "section_admin");

        List<Transaction> list = transactionDAO.alltransactions(roll, 1);
        model.addAttribute("transactions", list);

        return "managestudenttransactions";
    }

    @GetMapping("/localadmin/student/transaction/edit/{id}")
    public String studenttransactionedit( @PathVariable("id") int id, Model model, HttpSession session,
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
        model.addAttribute("id", id);

        return "updatetransaction";
    }

    @PostMapping("/localadmin/student/transaction/edit/{id}")
    public String studenttransactioneditPost(@PathVariable("id") int id,
            @ModelAttribute("transaction") Transaction transaction, Model model, HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Transaction trans = transactionDAO.findById(id);
        int roll = trans.getRoll_no();

        Student student = studentDAO.findByid(roll);
        int balance = student.getBalance();
        studentDAO.updateBalanceStudent(balance - transaction.getAmount(), roll);

        transactionDAO.update(id, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), roll, -1);
        return "redirect:/localadmin/student/transactions/{roll}";
    }

    @GetMapping("/localadmin/student/transaction/delete/{id}")
    public String studenttransactiondelete(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Transaction trans = transactionDAO.findById(id);
        int roll = trans.getRoll_no();

        Student student = studentDAO.findByid(roll);
        int balance = student.getBalance();
        studentDAO.updateBalanceStudent(balance + trans.getAmount(), roll);

        transactionDAO.delete(id);

        model.addAttribute("loggedinusername", curr_user);

        return "redirect:/localadmin/student/transactions/{roll}";
    }

    @GetMapping("/localadmin/student/transaction/add/{roll}")
    public String studenttransactionadd(@PathVariable("roll") int roll, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

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
        model.addAttribute("roll", roll);

        return "addtransaction";
    }

    @PostMapping("/localadmin/student/transaction/add/{roll}")
    public String studenttransactionaddPost(@PathVariable("roll") int roll, @ModelAttribute("transaction") Transaction transaction, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByid(roll);
        int balance = student.getBalance();
        studentDAO.updateBalanceStudent(balance - transaction.getAmount(), roll);
        
        int num_trans = transactionDAO.counttrans();

        transactionDAO.inserttransac(num_trans + 1, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), roll, -1);
        
        return "redirect:/localadmin/student/transactions/{roll}";
    }


    // CUSTOMER TRANSACTIONS

    @GetMapping("/localadmin/customer/transactions/{cid}")
    public String customertransactions(@PathVariable("cid") int cid, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByid(cid);
        model.addAttribute("loggedinusername", curr_user);
        model.addAttribute("customer", customer);
        model.addAttribute("role", "section_admin");

        List<Transaction> list = transactionDAO.alltransactions(cid, 0);
        model.addAttribute("transactions", list);

        return "managecustomertransactions";
    }

    @GetMapping("/localadmin/customer/transaction/edit/{id}")
    public String customertransactionedit( @PathVariable("id") int id, Model model, HttpSession session,
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
        model.addAttribute("id", id);

        return "updatecustransaction";
    }

    @PostMapping("/localadmin/customer/transaction/edit/{id}")
    public String customertransactioneditPost(@PathVariable("id") int id,
            @ModelAttribute("transaction") Transaction transaction, Model model, HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Transaction trans = transactionDAO.findById(id);
        int cid = trans.getC_id();

        Customer customer = customerDAO.findByid(cid);
        int balance = customer.getBalance();
        customerDAO.updateBalanceCustomer(balance - transaction.getAmount(), cid);

        transactionDAO.update(id, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), -1, cid);
        return "redirect:/localadmin/customer/transactions/{cid}";
    }

    @GetMapping("/localadmin/customer/transaction/delete/{id}")
    public String customertransactiondelete(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        String Message = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.issectionadmin(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, Message);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("role", "section_admin");

        Transaction trans = transactionDAO.findById(id);
        int cid = trans.getC_id();

        Customer customer = customerDAO.findByid(cid);
        int balance = customer.getBalance();
        customerDAO.updateBalanceCustomer(balance + trans.getAmount(), cid);

        transactionDAO.delete(id);

        model.addAttribute("loggedinusername", curr_user);

        return "redirect:/localadmin/customer/transactions/{cid}";
    }

    @GetMapping("/localadmin/customer/transaction/add/{cid}")
    public String customertransactionadd(@PathVariable("cid") int cid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

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
        model.addAttribute("cid", cid);

        return "addcustransaction";
    }

    @PostMapping("/localadmin/customer/transaction/add/{cid}")
    public String customertransactionaddPost(@PathVariable("cid") int cid, @ModelAttribute("transaction") Transaction transaction, Model model,
            HttpSession session) {
        String curr_user = auth_Service.getCurrentUser(session);

        Customer customer = customerDAO.findByid(cid);
        int balance = customer.getBalance();
        customerDAO.updateBalanceCustomer(balance - transaction.getAmount(), cid);
        
        int num_trans = transactionDAO.counttrans();

        transactionDAO.inserttransac(num_trans + 1, transaction.getAmount(), transaction.getType(), transaction.getDate(), transaction.getMode_of_payment(), -1, cid);
        
        return "redirect:/localadmin/customer/transactions/{cid}";
    }

}
