package com.codegym.service;

import com.codegym.exception.BadWordException;
import com.codegym.model.Feedback;
import com.codegym.repository.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    private static final List<String> BAD_WORDS = Collections.unmodifiableList(Arrays.asList(
            "xấu", "tệ", "ngu", "rác", "vô văn hóa", "xúc phạm", "đồ ngốc",
            "fuck", "shit", "damn", "idiot", "stupid", "wtf", "spam"
    ));

    private final IFeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(IFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<String> getBadWords() {
        return BAD_WORDS;
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
    public void save(Feedback feedback) throws BadWordException {
        if (feedback.getDate() == null) {
            feedback.setDate(LocalDate.now());
        }
        validateFeedbackContent(feedback);
        feedbackRepository.save(feedback);
    }

    private void validateFeedbackContent(Feedback feedback) throws BadWordException {
        String content = feedback.getFeedback() != null ? feedback.getFeedback().toLowerCase(Locale.ROOT) : "";
        for (String badWord : BAD_WORDS) {
            String normalizedBadWord = badWord.toLowerCase(Locale.ROOT);
            String regex = "(?iu)(^|[\\s\\p{Punct}])" + Pattern.quote(normalizedBadWord) + "($|[\\s\\p{Punct}])";
            if (Pattern.compile(regex).matcher(content).find() || content.contains(normalizedBadWord)) {
                throw new BadWordException(
                        "Nội dung bình luận có chứa từ ngữ không phù hợp: \"" + badWord + "\"",
                        feedback,
                        badWord
                );
            }
        }
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
