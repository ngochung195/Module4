package com.codegym.blogmanagementjpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codegym.blogmanagementjpa.model.Blog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Transactional
@Repository
public class BlogRepository implements IBlogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Blog> findAll() {
        TypedQuery<Blog> query = entityManager.createQuery("SELECT b FROM Blog b ORDER BY b.createdAt DESC",
                Blog.class);
        return query.getResultList();
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        TypedQuery<Blog> query = entityManager.createQuery("SELECT b FROM Blog b ORDER BY b.createdAt DESC",
                Blog.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Blog> blogs = query.getResultList();

        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(b) FROM Blog b", Long.class);
        long total = countQuery.getSingleResult();

        return new PageImpl<>(blogs, pageable, total);
    }

    @Override
    public List<Blog> searchByTitle(String keyword) {
        TypedQuery<Blog> query = entityManager.createQuery(
                "SELECT b FROM Blog b WHERE LOWER(b.title) LIKE LOWER(:keyword) ORDER BY b.createdAt DESC",
                Blog.class);
        query.setParameter("keyword", "%" + (keyword == null ? "" : keyword.trim()) + "%");
        return query.getResultList();
    }

    @Override
    public Page<Blog> findAllByTitleContaining(String keyword, Pageable pageable) {
        String pattern = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        TypedQuery<Blog> query = entityManager.createQuery(
                "SELECT b FROM Blog b WHERE LOWER(b.title) LIKE LOWER(:keyword) ORDER BY b.createdAt DESC",
                Blog.class);
        query.setParameter("keyword", pattern);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Blog> blogs = query.getResultList();

        TypedQuery<Long> countQuery = entityManager.createQuery(
                "SELECT COUNT(b) FROM Blog b WHERE LOWER(b.title) LIKE LOWER(:keyword)", Long.class);
        countQuery.setParameter("keyword", pattern);
        long total = countQuery.getSingleResult();

        return new PageImpl<>(blogs, pageable, total);
    }

    @Override
    public Blog findById(Long id) {
        return entityManager.find(Blog.class, id);
    }

    @Override
    public void save(Blog blog) {
        if (blog.getId() != null) {
            entityManager.merge(blog);
        } else {
            entityManager.persist(blog);
        }
    }

    @Override
    public void remove(Long id) {
        Blog blog = findById(id);
        if (blog != null) {
            entityManager.remove(blog);
        }
    }

    @Override
    public List<Blog> findByCategoryId(Long categoryId) {
        TypedQuery<Blog> query = entityManager.createQuery(
                "SELECT b FROM Blog b WHERE b.category.id = :categoryId ORDER BY b.createdAt DESC",
                Blog.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }
}