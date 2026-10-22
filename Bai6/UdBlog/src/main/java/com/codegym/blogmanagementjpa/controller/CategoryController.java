package com.codegym.blogmanagementjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codegym.blogmanagementjpa.model.Category;
import com.codegym.blogmanagementjpa.service.ICategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @PostMapping ("/save")
    public String saveCategory(@ModelAttribute ("category") Category category, RedirectAttributes redirectAttributes){
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Lưu danh mục thành công!");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        return categoryService.findById(id).map(category -> {
            model.addAttribute("category", category);
            return "category/edit";
        }).orElse("redirect:/categories");
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("category") Category category, RedirectAttributes ra) {
        categoryService.save(category);
        ra.addFlashAttribute("message", "Cập nhật danh mục thành công!");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        return categoryService.findById(id).map(category -> {
            model.addAttribute("category", category);
            return "category/delete";
        }).orElse("redirect:/categories");
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam("id") Long id, RedirectAttributes ra) {
        categoryService.remove(id);
        ra.addFlashAttribute("message", "Đã xoá danh mục thành công!");
        return "redirect:/categories";
    }
}
