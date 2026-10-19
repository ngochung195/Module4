package com.codegym.blogmanagementjpa.repository;

import java.util.List;

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
}