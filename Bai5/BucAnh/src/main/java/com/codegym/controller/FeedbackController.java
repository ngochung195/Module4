package com.codegym.controller;

import com.codegym.exception.BadWordException;
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
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("")
public class FeedbackController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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
        model.addAttribute("badWords", feedbackService.getBadWords());
        return "index";
    }

    @PostMapping("/feedback")
    public String saveFeedback(@ModelAttribute("feedbackForm") Feedback feedback) throws BadWordException {
        feedbackService.save(feedback);
        return "redirect:/";
    }

    @GetMapping("/like/{id}")
    public String likeFeedback(@PathVariable("id") Long id,
                               @RequestParam(value = "page", defaultValue = "0") int page) {
        feedbackService.like(id);
        return "redirect:/?page=" + page;
    }

    @ExceptionHandler(BadWordException.class)
    public ModelAndView handleBadWordException(BadWordException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("badWord", ex.getBadWord());
        if (ex.getFeedback() != null) {
            modelAndView.addObject("author", ex.getFeedback().getAuthor());
            modelAndView.addObject("feedbackContent", ex.getFeedback().getFeedback());
        }
        if (ex.getTimestamp() != null) {
            modelAndView.addObject("timestamp", ex.getTimestamp().format(FORMATTER));
        }
        modelAndView.addObject("badWords", feedbackService.getBadWords());
        return modelAndView;
    }
}
