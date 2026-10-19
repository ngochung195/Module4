package com.codegym.blogmanagementjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.codegym.blogmanagementjpa.model.Blog;
import com.codegym.blogmanagementjpa.repository.IBlogRepository;

public class BlogService implements IBlogService {

    @Autowired
    private IBlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void remove(Long id) {
        blogRepository.remove(id);
    }
}
