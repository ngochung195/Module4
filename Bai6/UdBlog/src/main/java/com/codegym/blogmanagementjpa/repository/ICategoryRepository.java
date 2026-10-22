package com.codegym.blogmanagementjpa.repository;

import org.springframework.stereotype.Repository;

import com.codegym.blogmanagementjpa.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}