package com.codegym.service;

import com.codegym.model.Feedback;
import java.time.LocalDate;
import java.util.List;

public interface IFeedbackService extends IGenerateService<Feedback> {
    List<Feedback> findAllToday();
    List<Feedback> findAllByDate(LocalDate date);
    void update(Feedback feedback);
    void like(Long id);
}
