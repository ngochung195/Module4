package com.codegym.controller;

import com.codegym.model.PhoneNumber;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhoneController {

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("phoneNumber", new PhoneNumber());
        return "index";
    }

    @PostMapping("/")
    public String checkValidation(@Valid @ModelAttribute("phoneNumber") PhoneNumber phoneNumber,
                                  BindingResult bindingResult,
                                  Model model) {
        phoneNumber.validate(phoneNumber, bindingResult);
        if (bindingResult.hasErrors()) {
            return "index";
        }
        model.addAttribute("phoneNumber", phoneNumber);
        return "result";
    }
}
