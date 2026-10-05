package com.codegym.controller;

import com.codegym.model.MedicalDeclaration;
import com.codegym.service.MedicalDeclarationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class MedicalDeclarationController {

    private final MedicalDeclarationService medicalDeclarationService;

    @Autowired
    public MedicalDeclarationController(MedicalDeclarationService medicalDeclarationService) {
        this.medicalDeclarationService = medicalDeclarationService;
    }

    @ModelAttribute("genderList")
    public List<String> getGenderList() {
        return Arrays.asList("Nam", "Nữ", "Khác");
    }

    @ModelAttribute("symptomList")
    public List<String> getSymptomList() {
        return Arrays.asList("Sốt", "Ho", "Khó thở", "Đau họng", "Mệt mỏi", "Không có triệu chứng");
    }

    @ModelAttribute("medicalHistoryList")
    public List<String> getMedicalHistoryList() {
        return Arrays.asList("Bệnh gan mạn tính", "Bệnh máu mạn tính", "Bệnh phổi mạn tính", "Bệnh thận mạn tính", "Bệnh tim mạch", "Huyết áp cao", "Tiểu đường", "Không có");
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/declaration";
    }

    @GetMapping("/declaration")
    public String showDeclarationForm(Model model) {
        MedicalDeclaration declaration = medicalDeclarationService.getDeclaration();
        if (declaration == null) {
            declaration = new MedicalDeclaration();
        }
        model.addAttribute("medicalDeclaration", declaration);
        return "declaration";
    }

    @PostMapping("/declaration")
    public String submitDeclaration(
            @Valid @ModelAttribute("medicalDeclaration") MedicalDeclaration medicalDeclaration,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "declaration";
        }

        medicalDeclarationService.saveOrUpdate(medicalDeclaration);
        return "redirect:/declaration/view";
    }

    @GetMapping("/declaration/view")
    public String viewDeclaration(Model model) {
        MedicalDeclaration declaration = medicalDeclarationService.getDeclaration();
        if (declaration == null) {
            return "redirect:/declaration";
        }
        model.addAttribute("medicalDeclaration", declaration);
        return "view";
    }
}
