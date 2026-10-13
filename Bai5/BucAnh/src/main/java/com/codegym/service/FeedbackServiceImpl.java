package com.codegym.service;

import com.codegym.model.Feedback;
import com.codegym.repository.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    private final IFeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(IFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Iterable<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Page<Feedback> findAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    @Override
    public Page<Feedback> findAllByDate(LocalDate date, Pageable pageable) {
        return feedbackRepository.findAllByDate(date, pageable);
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
    public Optional<Feedback> findById(Long id) {
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
    public void like(Long id) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);
        if (optionalFeedback.isPresent()) {
            Feedback feedback = optionalFeedback.get();
            feedback.setLikes(feedback.getLikes() + 1);
            feedbackRepository.save(feedback);
        }
    }

    @Override
    public void remove(Long id) {
        feedbackRepository.deleteById(id);
    }
}
