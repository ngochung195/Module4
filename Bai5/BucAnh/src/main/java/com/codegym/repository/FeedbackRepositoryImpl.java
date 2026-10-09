package com.codegym.repository;

import com.codegym.model.Feedback;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class FeedbackRepositoryImpl implements IFeedbackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Feedback> findAll() {
        TypedQuery<Feedback> query = entityManager.createQuery("SELECT f FROM Feedback f ORDER BY f.id DESC", Feedback.class);
        return query.getResultList();
    }

    @Override
    public List<Feedback> findAllByDate(LocalDate date) {
        TypedQuery<Feedback> query = entityManager.createQuery(
                "SELECT f FROM Feedback f WHERE f.date = :date ORDER BY f.id DESC", Feedback.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public Feedback findById(Long id) {
        return entityManager.find(Feedback.class, id);
    }

    @Override
    public void save(Feedback feedback) {
        if (feedback.getId() == null) {
            entityManager.persist(feedback);
        } else {
            entityManager.merge(feedback);
        }
    }

    @Override
    public void update(Feedback feedback) {
        entityManager.merge(feedback);
    }

    @Override
    public void like(Long id) {
        Feedback feedback = findById(id);
        if (feedback != null) {
            feedback.setLikes(feedback.getLikes() + 1);
            entityManager.merge(feedback);
        }
    }

    @Override
    public void remove(Long id) {
        Feedback feedback = findById(id);
        if (feedback != null) {
            entityManager.remove(feedback);
        }
    }
}
