package com.codegym.controller;

import com.codegym.model.EmailConfig;
import com.codegym.model.EmailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmailConfigController {

    private final EmailConfigService emailConfigService;

    @Autowired
    public EmailConfigController(EmailConfigService emailConfigService) {
        this.emailConfigService = emailConfigService;
    }

    @ModelAttribute("languages")
    public List<String> getLanguages() {
        return emailConfigService.getLanguages();
    }

    @ModelAttribute("pageSizes")
    public List<Integer> getPageSizes() {
        return emailConfigService.getPageSizes();
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/config";
    }

    @GetMapping("/config")
    public String showConfigForm(Model model) {
        EmailConfig emailConfig = emailConfigService.getConfig();
        model.addAttribute("emailConfig", emailConfig);
        return "config";
    }

    @PostMapping("/config")
    public String updateConfig(@ModelAttribute("emailConfig") EmailConfig emailConfig, Model model) {
        emailConfigService.updateConfig(emailConfig);
        model.addAttribute("emailConfig", emailConfigService.getConfig());
        model.addAttribute("message", "Cập nhật cấu hình hòm thư thành công!");
        return "result";
    }
}
