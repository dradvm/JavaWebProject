package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Feedback;
import com.JavaWebProject.JavaWebProject.services.FeedbackService;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("")
    public String showFeedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "Feedback/feedback";
    }

    @PostMapping("/addFeedback")
    public String submitFeedback(@ModelAttribute Feedback feedback, Model model, HttpSession session) {
//        String username = (String) session.getAttribute("username");
//        feedback.setEmail(username);
        Date currentime = new Date();      
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(currentime);
        feedback.setFeedbackDate(currentime);
        feedbackService.save(feedback);
        
        model.addAttribute("email", feedback.getEmail());
        model.addAttribute("feedbackDetails", feedback.getFeedbackDetails());
        model.addAttribute("feedbackDate", formattedDate);
        model.addAttribute("message", "Feedback submitted successfully");
        return "Feedback/feedback";
    }

}
