package com.codegym.blogmanagementjpa.service;

import java.util.List;
import java.util.Optional;

import com.codegym.blogmanagementjpa.model.Category;

public interface ICategoryService {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    void save(Category category);

    void remove(Long id);
}
