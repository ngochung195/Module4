package com.codegym.blogmanagementjpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.codegym.blogmanagementjpa.model.Category;
import com.codegym.blogmanagementjpa.repository.ICategoryRepository;

@SpringJUnitConfig(BlogServiceTestConfig.class)
public class CategoryServiceTest {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICategoryRepository categoryRepository;

    @AfterEach
    void resetMocks() {
        reset(categoryRepository);
    }

    @Test
    @DisplayName("1. Kiểm thử lấy danh sách tất cả danh mục (findAll)")
    void testFindAll() {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Lập trình Java"),
                new Category(2L, "Cơ sở dữ liệu"));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertEquals(2, result.size());
        assertEquals("Lập trình Java", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("2. Kiểm thử tìm danh mục theo ID (findById)")
    void testFindById() {
        Category category = new Category(1L, "Spring Framework");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Category> found = categoryService.findById(1L);
        Optional<Category> notFound = categoryService.findById(99L);

        assertTrue(found.isPresent());
        assertEquals("Spring Framework", found.get().getName());
        assertFalse(notFound.isPresent());
    }

    @Test
    @DisplayName("3. Kiểm thử lưu danh mục (save)")
    void testSave() {
        Category category = new Category(null, "Danh mục mới");
        when(categoryRepository.save(category)).thenReturn(new Category(1L, "Danh mục mới"));

        categoryService.save(category);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("4. Kiểm thử xóa danh mục (remove)")
    void testRemove() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.remove(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
