package com.codegym.blogmanagementjpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegym.blogmanagementjpa.model.Blog;
import com.codegym.blogmanagementjpa.model.Category;
import com.codegym.blogmanagementjpa.service.IBlogService;
import com.codegym.blogmanagementjpa.service.ICategoryService;

@RestController
@RequestMapping("/api/blogs")
public class BlogRestController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    // 2. Xem danh sách các bài viết
    @GetMapping
    public ResponseEntity<List<Blog>> findAllBlogs() {
        List<Blog> blogs = blogService.findAll();
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // 3. Xem danh sách các bài viết của một category (endpoint thay thế)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Blog>> findBlogsByCategory(@PathVariable("categoryId") Long categoryId) {
        Optional<Category> categoryOptional = categoryService.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Blog> blogs = blogService.findByCategoryId(categoryId);
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // 4. Xem chi tiết một bài viết
    @GetMapping("/{id}")
    public ResponseEntity<Blog> findBlogById(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }
}

