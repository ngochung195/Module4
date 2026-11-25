package com.codegym.blogmanagementjpa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codegym.blogmanagementjpa.model.Category;
import com.codegym.blogmanagementjpa.service.ICategoryService;

@SpringJUnitWebConfig(BlogControllerTestConfig.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @AfterEach
    void tearDown() {
        reset(categoryService);
    }

    @Test
    @DisplayName("1. Kiểm thử hiển thị danh sách danh mục (GET /categories)")
    void testListCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Java"),
                new Category(2L, "Spring"));
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/list"))
                .andExpect(model().attribute("categories", categories));

        verify(categoryService, times(1)).findAll();
    }

    @Test
    @DisplayName("2. Kiểm thử hiển thị form tạo danh mục (GET /categories/create)")
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/categories/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/create"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    @DisplayName("3. Kiểm thử lưu danh mục mới (POST /categories/save)")
    void testSaveCategory() throws Exception {
        doNothing().when(categoryService).save(any(Category.class));

        mockMvc.perform(post("/categories/save").param("name", "DevOps"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"))
                .andExpect(flash().attributeExists("message"));

        verify(categoryService, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("4. Kiểm thử hiển thị form sửa danh mục (GET /categories/{id}/edit)")
    void testShowEditForm() throws Exception {
        Category category = new Category(1L, "Java");
        when(categoryService.findById(1L)).thenReturn(Optional.of(category));
        when(categoryService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/categories/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/edit"))
                .andExpect(model().attribute("category", category));

        mockMvc.perform(get("/categories/99/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));
    }

    @Test
    @DisplayName("5. Kiểm thử cập nhật và xóa danh mục")
    void testUpdateAndDeleteCategory() throws Exception {
        Category category = new Category(1L, "Java");
        when(categoryService.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryService).save(any(Category.class));
        doNothing().when(categoryService).remove(1L);

        mockMvc.perform(post("/categories/update").param("id", "1").param("name", "Java 21"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));

        mockMvc.perform(get("/categories/1/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/delete"));

        mockMvc.perform(post("/categories/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));

        verify(categoryService, times(1)).remove(1L);
    }
}
