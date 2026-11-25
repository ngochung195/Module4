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
public class BlogRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private BlogRestController blogRestController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogRestController).build();
    }

    @AfterEach
    void tearDown() {
        reset(blogService, categoryService);
    }

    @Test
    @DisplayName("1. Kiểm thử REST API lấy danh sách bài viết (GET /api/blogs - 200 OK & 204 NO_CONTENT)")
    void testFindAllBlogs() throws Exception {
        Blog b1 = new Blog("REST Blog 1", "Tóm tắt 1", "Nội dung 1");
        b1.setId(1L);
        Blog b2 = new Blog("REST Blog 2", "Tóm tắt 2", "Nội dung 2");
        b2.setId(2L);

        when(blogService.findAll()).thenReturn(Arrays.asList(b1, b2));

        mockMvc.perform(get("/api/blogs").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("REST Blog 1")));

        when(blogService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/blogs").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("2. Kiểm thử REST API xem chi tiết bài viết (GET /api/blogs/{id} - 200 OK & 404 NOT_FOUND)")
    void testFindBlogById() throws Exception {
        Blog blog = new Blog("Chi tiết REST", "Tóm tắt", "Nội dung");
        blog.setId(1L);

        when(blogService.findById(1L)).thenReturn(blog);
        when(blogService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/blogs/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Chi tiết REST")));

        mockMvc.perform(get("/api/blogs/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("3. Kiểm thử REST API lấy bài viết theo category (GET /api/blogs/category/{id})")
    void testFindBlogsByCategory() throws Exception {
        Category category = new Category(1L, "Java");
        Blog blog = new Blog("Java Blog", "Tóm tắt", "Nội dung");
        blog.setId(1L);
        blog.setCategory(category);

        when(categoryService.findById(1L)).thenReturn(Optional.of(category));
        when(blogService.findByCategoryId(1L)).thenReturn(Collections.singletonList(blog));
        when(categoryService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/blogs/category/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Java Blog")));

        mockMvc.perform(get("/api/blogs/category/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
