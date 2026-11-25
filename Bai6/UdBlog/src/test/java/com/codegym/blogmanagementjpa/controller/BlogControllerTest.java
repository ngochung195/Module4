package com.codegym.blogmanagementjpa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codegym.blogmanagementjpa.model.Blog;
import com.codegym.blogmanagementjpa.service.IBlogService;

@SpringJUnitWebConfig(BlogControllerTestConfig.class)
public class BlogControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private BlogController blogController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @AfterEach
    void tearDown() {
        reset(blogService);
    }

    @Test
    @DisplayName("1. Kiểm thử lấy danh sách bài viết (GET /blogs)")
    void testListBlogs() throws Exception {
        Blog blog1 = new Blog("Bài viết 1", "Tóm tắt 1", "Nội dung 1");
        blog1.setId(1L);
        Blog blog2 = new Blog("Bài viết 2", "Tóm tắt 2", "Nội dung 2");
        blog2.setId(2L);
        List<Blog> blogs = Arrays.asList(blog1, blog2);

        when(blogService.findAll()).thenReturn(blogs);

        mockMvc.perform(get("/blogs"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/list"))
                .andExpect(model().attributeExists("blogs"))
                .andExpect(model().attribute("blogs", blogs));

        verify(blogService, times(1)).findAll();
    }

    @Test
    @DisplayName("2. Kiểm thử lấy danh sách bài viết có phân trang (GET /blogs/page)")
    void testListBlogsPaginated() throws Exception {
        Blog blog1 = new Blog("Bài viết phân trang 1", "Tóm tắt 1", "Nội dung 1");
        blog1.setId(1L);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Blog> blogPage = new PageImpl<>(Collections.singletonList(blog1), pageable, 1);

        when(blogService.findAll(any(Pageable.class))).thenReturn(blogPage);

        mockMvc.perform(get("/blogs/page").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/list"))
                .andExpect(model().attributeExists("blogPage"))
                .andExpect(model().attributeExists("blogs"))
                .andExpect(model().attribute("blogPage", blogPage));

        verify(blogService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("3. Kiểm thử tìm kiếm bài viết theo từ khóa (GET /blogs/search?search=Spring)")
    void testSearchBlogs() throws Exception {
        String keyword = "Spring";
        Blog blog = new Blog("Spring Security", "Tóm tắt", "Nội dung");
        blog.setId(1L);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Blog> blogPage = new PageImpl<>(Collections.singletonList(blog), pageable, 1);

        when(blogService.findAllByTitleContaining(eq(keyword), any(Pageable.class))).thenReturn(blogPage);

        mockMvc.perform(get("/blogs/search").param("search", keyword).param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/list"))
                .andExpect(model().attribute("search", keyword))
                .andExpect(model().attribute("blogPage", blogPage))
                .andExpect(model().attribute("blogs", blogPage.getContent()));

        verify(blogService, times(1)).findAllByTitleContaining(eq(keyword), any(Pageable.class));
    }

    @Test
    @DisplayName("4. Kiểm thử xem chi tiết bài viết tồn tại (GET /blogs/{id}/view)")
    void testViewBlogFound() throws Exception {
        Blog blog = new Blog("Chi tiết", "Tóm tắt", "Nội dung");
        blog.setId(1L);
        when(blogService.findById(1L)).thenReturn(blog);

        mockMvc.perform(get("/blogs/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/view"))
                .andExpect(model().attribute("blog", blog));

        verify(blogService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("5. Kiểm thử xem chi tiết bài viết không tồn tại chuyển hướng về /blogs")
    void testViewBlogNotFound() throws Exception {
        when(blogService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/blogs/99/view"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blogs"));

        verify(blogService, times(1)).findById(99L);
    }

    @Test
    @DisplayName("6. Kiểm thử hiển thị form thêm bài viết mới (GET /blogs/create)")
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/blogs/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/create"))
                .andExpect(model().attributeExists("blog"));
    }

    @Test
    @DisplayName("7. Kiểm thử lưu bài viết mới (POST /blogs/save)")
    void testSaveBlog() throws Exception {
        doNothing().when(blogService).save(any(Blog.class));

        mockMvc.perform(post("/blogs/save")
                        .param("title", "Bài viết mới")
                        .param("summary", "Tóm tắt")
                        .param("content", "Nội dung"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blogs"))
                .andExpect(flash().attributeExists("message"));

        verify(blogService, times(1)).save(any(Blog.class));
    }

    @Test
    @DisplayName("8. Kiểm thử hiển thị form chỉnh sửa bài viết (GET /blogs/{id}/edit)")
    void testShowEditForm() throws Exception {
        Blog blog = new Blog("Cần sửa", "Tóm tắt", "Nội dung");
        blog.setId(1L);
        when(blogService.findById(1L)).thenReturn(blog);

        mockMvc.perform(get("/blogs/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/edit"))
                .andExpect(model().attribute("blog", blog));
    }

    @Test
    @DisplayName("9. Kiểm thử cập nhật bài viết (POST /blogs/update)")
    void testUpdateBlog() throws Exception {
        doNothing().when(blogService).save(any(Blog.class));

        mockMvc.perform(post("/blogs/update")
                        .param("id", "1")
                        .param("title", "Đã cập nhật")
                        .param("summary", "Tóm tắt mới")
                        .param("content", "Nội dung mới"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blogs"))
                .andExpect(flash().attributeExists("message"));

        verify(blogService, times(1)).save(any(Blog.class));
    }

    @Test
    @DisplayName("10. Kiểm thử hiển thị xác nhận xóa và thực hiện xóa bài viết")
    void testDeleteBlogFlow() throws Exception {
        Blog blog = new Blog("Sắp xóa", "Tóm tắt", "Nội dung");
        blog.setId(1L);
        when(blogService.findById(1L)).thenReturn(blog);
        doNothing().when(blogService).remove(1L);

        mockMvc.perform(get("/blogs/1/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/delete"))
                .andExpect(model().attribute("blog", blog));

        mockMvc.perform(post("/blogs/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blogs"))
                .andExpect(flash().attributeExists("message"));

        verify(blogService, times(1)).remove(1L);
    }
}
