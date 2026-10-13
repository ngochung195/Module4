package com.codegym.service;

import com.codegym.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IFeedbackService extends IGenerateService<Feedback> {
    Page<Feedback> findAll(Pageable pageable);

    Page<Feedback> findAllByDate(LocalDate date, Pageable pageable);

    List<Feedback> findAllToday();

    List<Feedback> findAllByDate(LocalDate date);

    void like(Long id);
}
