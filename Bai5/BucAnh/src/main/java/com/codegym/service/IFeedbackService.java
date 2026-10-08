package com.codegym.service;

import com.codegym.model.Feedback;
import java.time.LocalDate;
import java.util.List;

public interface IFeedbackService {
    List<Feedback> findAll();
    List<Feedback> findAllToday();
    List<Feedback> findAllByDate(LocalDate date);
    Feedback findById(Long id);
    void save(Feedback feedback);
    void update(Feedback feedback);
    void like(Long id);
}
