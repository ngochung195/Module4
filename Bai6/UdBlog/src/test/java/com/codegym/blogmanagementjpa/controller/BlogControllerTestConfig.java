package com.codegym.blogmanagementjpa.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codegym.blogmanagementjpa.service.IBlogService;
import com.codegym.blogmanagementjpa.service.ICategoryService;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan("com.codegym.blogmanagementjpa.controller")
public class BlogControllerTestConfig {

    @Bean
    public IBlogService blogService() {
        return Mockito.mock(IBlogService.class);
    }

    @Bean
    public ICategoryService categoryService() {
        return Mockito.mock(ICategoryService.class);
    }
}
