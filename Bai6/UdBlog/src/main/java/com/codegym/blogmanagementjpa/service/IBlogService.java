package com.codegym.blogmanagementjpa.service;

import java.util.List;

import com.codegym.blogmanagementjpa.model.Blog;

public interface IBlogService {
    List<Blog> findAll();

    Blog findById(Long id);

    void save(Blog blog);

    void remove(Long id);
}