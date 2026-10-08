package com.codegym.service;

import com.codegym.model.Feedback;
import com.codegym.repository.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    private final IFeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(IFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> findAllToday() {
        return feedbackRepository.findAllByDate(LocalDate.now());
    }

    @Override
    public List<Feedback> findAllByDate(LocalDate date) {
        return feedbackRepository.findAllByDate(date);
    }

    @Override
    public Feedback findById(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public void save(Feedback feedback) {
        if (feedback.getDate() == null) {
            feedback.setDate(LocalDate.now());
        }
        feedbackRepository.save(feedback);
    }

    @Override
    public void update(Feedback feedback) {
        feedbackRepository.update(feedback);
    }

    @Override
    public void like(Long id) {
        feedbackRepository.like(id);
    }
}
