package com.codegym.blogmanagementjpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codegym.blogmanagementjpa.model.Blog;

public interface IBlogRepository {

    List<Blog> findAll();

    Page<Blog> findAll(Pageable pageable);

    List<Blog> searchByTitle(String keyword);

    Page<Blog> findAllByTitleContaining(String keyword, Pageable pageable);

    Blog findById(Long id);

    void save(Blog blog);

    void remove(Long id);

    List<Blog> findByCategoryId(Long categoryId);
}
