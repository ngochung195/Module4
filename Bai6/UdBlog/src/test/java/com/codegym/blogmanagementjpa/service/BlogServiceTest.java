package com.codegym.blogmanagementjpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.codegym.blogmanagementjpa.model.Blog;
import com.codegym.blogmanagementjpa.model.Category;
import com.codegym.blogmanagementjpa.repository.IBlogRepository;

@SpringJUnitConfig(BlogServiceTestConfig.class)
public class BlogServiceTest {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private IBlogRepository blogRepository;

    @AfterEach
    void resetMocks() {
        reset(blogRepository);
    }

    @Test
    @DisplayName("1. Kiểm thử lấy toàn bộ danh sách bài viết (findAll)")
    void testFindAll() {
        Blog blog1 = new Blog("Spring MVC", "Tóm tắt 1", "Nội dung 1");
        blog1.setId(1L);
        Blog blog2 = new Blog("Spring Security", "Tóm tắt 2", "Nội dung 2");
        blog2.setId(2L);
        List<Blog> mockBlogs = Arrays.asList(blog1, blog2);

        when(blogRepository.findAll()).thenReturn(mockBlogs);

        List<Blog> result = blogService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Spring MVC", result.get(0).getTitle());
        verify(blogRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("2. Kiểm thử lấy danh sách bài viết có phân trang (findAll với Pageable)")
    void testFindAllPaginated() {
        Blog blog1 = new Blog("Bài viết 1", "Tóm tắt 1", "Nội dung 1");
        blog1.setId(1L);
        Blog blog2 = new Blog("Bài viết 2", "Tóm tắt 2", "Nội dung 2");
        blog2.setId(2L);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Blog> mockPage = new PageImpl<>(Arrays.asList(blog1, blog2), pageable, 2);

        when(blogRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Blog> result = blogService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("Bài viết 1", result.getContent().get(0).getTitle());
        verify(blogRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("3. Kiểm thử tìm kiếm bài viết theo tiêu đề (searchByTitle)")
    void testSearchByTitle() {
        String keyword = "Spring";
        Blog blog = new Blog("Học Spring Boot", "Tóm tắt Spring", "Nội dung chi tiết");
        blog.setId(1L);

        when(blogRepository.searchByTitle(keyword)).thenReturn(Collections.singletonList(blog));

        List<Blog> result = blogService.searchByTitle(keyword);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Học Spring Boot", result.get(0).getTitle());
        verify(blogRepository, times(1)).searchByTitle(keyword);
    }

    @Test
    @DisplayName("4. Kiểm thử tìm kiếm bài viết có phân trang (findAllByTitleContaining)")
    void testFindAllByTitleContainingPaginated() {
        String keyword = "Spring";
        Pageable pageable = PageRequest.of(0, 5);
        Blog blog = new Blog("Spring Data JPA", "Tóm tắt JPA", "Nội dung JPA");
        blog.setId(1L);
        Page<Blog> mockPage = new PageImpl<>(Collections.singletonList(blog), pageable, 1);

        when(blogRepository.findAllByTitleContaining(keyword, pageable)).thenReturn(mockPage);

        Page<Blog> result = blogService.findAllByTitleContaining(keyword, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Spring Data JPA", result.getContent().get(0).getTitle());
        verify(blogRepository, times(1)).findAllByTitleContaining(keyword, pageable);
    }

    @Test
    @DisplayName("5. Kiểm thử tìm bài viết theo ID (findById)")
    void testFindById() {
        Blog blog = new Blog("Chi tiết bài viết", "Tóm tắt", "Nội dung");
        blog.setId(10L);

        when(blogRepository.findById(10L)).thenReturn(blog);
        when(blogRepository.findById(99L)).thenReturn(null);

        Blog found = blogService.findById(10L);
        Blog notFound = blogService.findById(99L);

        assertNotNull(found);
        assertEquals(10L, found.getId());
        assertEquals("Chi tiết bài viết", found.getTitle());
        assertNull(notFound);
        verify(blogRepository, times(1)).findById(10L);
        verify(blogRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("6. Kiểm thử lấy danh sách bài viết theo Category ID (findByCategoryId)")
    void testFindByCategoryId() {
        Category category = new Category(1L, "Java");
        Blog blog = new Blog("Java Core", "Tóm tắt", "Nội dung");
        blog.setId(1L);
        blog.setCategory(category);

        when(blogRepository.findByCategoryId(1L)).thenReturn(Collections.singletonList(blog));

        List<Blog> result = blogService.findByCategoryId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getCategory().getName());
        verify(blogRepository, times(1)).findByCategoryId(1L);
    }

    @Test
    @DisplayName("7. Kiểm thử lưu bài viết mới hoặc cập nhật (save)")
    void testSave() {
        Blog blog = new Blog("Bài viết mới", "Tóm tắt mới", "Nội dung mới");
        doNothing().when(blogRepository).save(blog);

        blogService.save(blog);

        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    @DisplayName("8. Kiểm thử xóa bài viết theo ID (remove)")
    void testRemove() {
        Long id = 1L;
        doNothing().when(blogRepository).remove(id);

        blogService.remove(id);

        verify(blogRepository, times(1)).remove(id);
    }
}
