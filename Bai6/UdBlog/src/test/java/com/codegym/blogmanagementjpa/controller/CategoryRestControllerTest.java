package com.codegym.blogmanagementjpa.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codegym.blogmanagementjpa.model.Blog;
import com.codegym.blogmanagementjpa.model.Category;
import com.codegym.blogmanagementjpa.service.IBlogService;
import com.codegym.blogmanagementjpa.service.ICategoryService;

@SpringJUnitWebConfig(BlogControllerTestConfig.class)
public class CategoryRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private CategoryRestController categoryRestController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController).build();
    }

    @AfterEach
    void tearDown() {
        reset(categoryService, blogService);
    }

    @Test
    @DisplayName("1. Kiểm thử REST API lấy danh sách category (GET /api/categories)")
    void testFindAllCategories() throws Exception {
        Category c1 = new Category(1L, "Java");
        Category c2 = new Category(2L, "Spring");
        when(categoryService.findAll()).thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/api/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Java")));

        when(categoryService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("2. Kiểm thử REST API lấy danh sách bài viết của một category (GET /api/categories/{id}/blogs)")
    void testFindBlogsByCategory() throws Exception {
        Category c1 = new Category(1L, "Java");
        Blog b1 = new Blog("Java 21", "Tóm tắt", "Nội dung");
        b1.setId(1L);
        b1.setCategory(c1);

        when(categoryService.findById(1L)).thenReturn(Optional.of(c1));
        when(blogService.findByCategoryId(1L)).thenReturn(Collections.singletonList(b1));
        when(categoryService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/1/blogs").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Java 21")));

        mockMvc.perform(get("/api/categories/99/blogs").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
