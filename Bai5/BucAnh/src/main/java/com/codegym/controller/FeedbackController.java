package com.codegym.controller;

import com.codegym.model.Feedback;
import com.codegym.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class FeedbackController {

    private final IFeedbackService feedbackService;

    @Autowired
    public FeedbackController(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        Feedback feedback = new Feedback();
        feedback.setRating(5); // default rating
        model.addAttribute("feedback", feedback);
        model.addAttribute("feedbackList", feedbackService.findAllToday());
        return "index";
    }

    @PostMapping("/feedback")
    public String saveFeedback(@ModelAttribute("feedback") Feedback feedback) {
        feedbackService.save(feedback);
        return "redirect:/";
    }

    @GetMapping("/like/{id}")
    public String likeFeedback(@PathVariable("id") Long id) {
        feedbackService.like(id);
        return "redirect:/";
    }
}
