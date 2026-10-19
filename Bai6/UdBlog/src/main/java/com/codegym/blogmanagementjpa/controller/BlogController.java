package com.codegym.blogmanagementjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codegym.blogmanagementjpa.model.Blog;
import com.codegym.blogmanagementjpa.service.IBlogService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "blog/create";
    }

    @GetMapping("")
    public String listBlogs(Model model) {
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "blog/list";
    }

    @GetMapping("/{id}/view")
    public String viewBlog(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.findById(id);
        if (blog == null) {
            return "redirect:/blogs";
        }
        model.addAttribute("blog", blog);
        return "blog/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.findById(id);
        if (blog == null) {
            return "redirect:/blogs";
        }
        model.addAttribute("blog", blog);
        return "blog/edit";
    }

    @PostMapping("/save")
    public String saveBlog(@ModelAttribute("blog") Blog blog, RedirectAttributes redirectAttributes) {
        blogService.save(blog);
        redirectAttributes.addFlashAttribute("message", "Thêm bài viết mới thành công!");
        return "redirect:/blogs";
    }

    @PostMapping("/update")
    public String updateBlog(@ModelAttribute("blog") Blog blog, RedirectAttributes redirectAttributes) {
        blogService.save(blog);
        redirectAttributes.addFlashAttribute("message", "Cập nhật bài viết thành công!");
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteConfirm(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.findById(id);
        if (blog == null) {
            return "redirect:/blogs";
        }
        model.addAttribute("blog", blog);
        return "blog/delete";
    }

    @PostMapping("/delete")
    public String deleteBlog(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        blogService.remove(id);
        redirectAttributes.addFlashAttribute("message", "Đã xóa bài viết thành công!");
        return "redirect:/blogs";
    }

}