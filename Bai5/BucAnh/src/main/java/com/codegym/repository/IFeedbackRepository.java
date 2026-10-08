package com.codegym.repository;

import com.codegym.model.Feedback;
import java.time.LocalDate;
import java.util.List;

public interface IFeedbackRepository {
    List<Feedback> findAll();
    List<Feedback> findAllByDate(LocalDate date);
    Feedback findById(Long id);
    void save(Feedback feedback);
    void update(Feedback feedback);
    void like(Long id);
}
