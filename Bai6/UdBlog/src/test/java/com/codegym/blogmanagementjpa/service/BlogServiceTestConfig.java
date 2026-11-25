package com.codegym.blogmanagementjpa.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codegym.blogmanagementjpa.repository.IBlogRepository;
import com.codegym.blogmanagementjpa.repository.ICategoryRepository;

@Configuration
public class BlogServiceTestConfig {

    @Bean
    public IBlogRepository blogRepository() {
        return Mockito.mock(IBlogRepository.class);
    }

    @Bean
    public IBlogService blogService() {
        return new BlogService();
    }

    @Bean
    public ICategoryRepository categoryRepository() {
        return Mockito.mock(ICategoryRepository.class);
    }

    @Bean
    public ICategoryService categoryService() {
        return new CategoryService();
    }
}
