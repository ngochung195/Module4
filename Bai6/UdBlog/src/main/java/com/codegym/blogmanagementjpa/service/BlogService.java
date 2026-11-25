package com.codegym.blogmanagementjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codegym.blogmanagementjpa.model.Blog;
import com.codegym.blogmanagementjpa.repository.IBlogRepository;

@Service
public class BlogService implements IBlogService {

    @Autowired
    private IBlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> searchByTitle(String keyword) {
        return blogRepository.searchByTitle(keyword);
    }

    @Override
    public Page<Blog> findAllByTitleContaining(String keyword, Pageable pageable) {
        return blogRepository.findAllByTitleContaining(keyword, pageable);
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

    @Override
    public List<Blog> findByCategoryId(Long categoryId) {
        return blogRepository.findByCategoryId(categoryId);
    }
}
