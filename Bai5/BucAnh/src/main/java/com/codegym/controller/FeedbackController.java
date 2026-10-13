package com.codegym.controller;

import com.codegym.model.Feedback;
import com.codegym.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public String index(Model model,
                        @PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Feedback feedback = new Feedback();
        feedback.setRating(5); // default rating
        model.addAttribute("feedback", feedback);

        Page<Feedback> feedbackPage = feedbackService.findAll(pageable);
        model.addAttribute("feedbackPage", feedbackPage);
        model.addAttribute("feedbackList", feedbackPage.getContent());
        return "index";
    }

    @PostMapping("/feedback")
    public String saveFeedback(@ModelAttribute("feedback") Feedback feedback) {
        feedbackService.save(feedback);
        return "redirect:/";
    }

    @GetMapping("/like/{id}")
    public String likeFeedback(@PathVariable("id") Long id,
                               @RequestParam(value = "page", defaultValue = "0") int page) {
        feedbackService.like(id);
        return "redirect:/?page=" + page;
    }
}
