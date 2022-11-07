package com.example.dbms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dbms.dao.OptionsDAO;
import com.example.dbms.dao.QuestionsDAO;
import com.example.dbms.dao.StudentDAO;
import com.example.dbms.model.Options;
import com.example.dbms.model.Questions;
import com.example.dbms.model.Student;
import com.example.dbms.model.answered;
import com.example.dbms.service.AuthenticateService;
import com.example.dbms.service.ToastService;

@Controller
public class PollCotroller {
    @Autowired
    private AuthenticateService auth_Service;
    @Autowired
    public StudentDAO studentDAO;
    @Autowired
    private ToastService toastService;
    @Autowired
    public QuestionsDAO questionsDAO;

    @Autowired
    public OptionsDAO optionsDAO;

    @GetMapping("/student/polls")
    public String mypolls(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";

        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Student student = studentDAO.findByUsername(curr_user);

        Integer mess_no = student.getMess_id();
        Integer section_no = student.getSection_id();

        List<Questions> questions = questionsDAO.getnotanswerdquestion(student.getRoll_no(), section_no);

        model.addAttribute("questions", questions);

        return "studentpolls";
    }

    @GetMapping("/student/polls/answered")
    public String myansweredpolls(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);
        Student student = studentDAO.findByUsername(curr_user);

        Integer mess_no = student.getMess_id();
        Integer section_no = student.getSection_id();

        List<answered> questions = questionsDAO.getanswerdquestion(student.getRoll_no(), section_no);
        System.out.println(questions.get(0));
        model.addAttribute("questions", questions);

        return "stansque";
    }

    @GetMapping("/student/polls/{id}")
    public String pollformshow(@PathVariable("id") int questionid, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }

        String curr_user = auth_Service.getCurrentUser(session);
        model.addAttribute("loggedinusername", curr_user);

        // Student student = studentDAO.findByUsername(curr_user);

        // Integer mess_no = student.getMess_id();
        // Integer section_no = student.getSection_id();

        Questions question = questionsDAO.getQuestionbyId(questionid);

        List<Options> options = optionsDAO.getOptions(questionid);

        System.out.println(question.getQuestionid());
        if (options.size() == 0) {
            return "redirect:/polls";
        }
        model.addAttribute("question", question);
        model.addAttribute("options", options);
        Options ans = new Options();
        model.addAttribute("ans", ans);

        return "pollans";

    }

    @PostMapping("/student/polls/answer")
    public String submit(@ModelAttribute("ans") Options ans, @ModelAttribute("qid") int qid, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByUsername(curr_user);

        // Integer mess_no = student.getMess_id();
        // Integer section_no = student.getSection_id();
        // System.out.println(ans.getOptionid());
        // System.out.println(ans.getQ_id());
        // System.out.println(student.getRoll_no());

        optionsDAO.chooseOption(ans.getOptionid(), qid,
                student.getRoll_no());

        return "redirect:/student/polls";

    }
    
    @GetMapping("/polls/create")
    public String createpoll(HttpSession session, RedirectAttributes redirectAttributes) {
        String loginMessage = "Please Sign in to proceed!!!";
        if (!auth_Service.isAuthenticated(session) || !auth_Service.isstudent(session)) {
            toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
            return "redirect:/login";
        }
        String curr_user = auth_Service.getCurrentUser(session);

        Student student = studentDAO.findByUsername(curr_user);
        // model.addAttribute("newpoll",new())
        return "Createpoll";
    }

}
