package com.codegym.blogmanagementjpa.repository;

import java.util.List;

import com.codegym.blogmanagementjpa.model.Blog;

public interface IBlogRepository {

    List<Blog> findAll();

    Blog findById(Long id);

    void save(Blog blog);

    void remove(Long id);
}
