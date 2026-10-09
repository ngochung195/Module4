package com.codegym.repository;

import com.codegym.model.Feedback;
import java.time.LocalDate;
import java.util.List;

public interface IFeedbackRepository extends IGenerateRepository<Feedback> {
    List<Feedback> findAllByDate(LocalDate date);
    void update(Feedback feedback);
    void like(Long id);
}
