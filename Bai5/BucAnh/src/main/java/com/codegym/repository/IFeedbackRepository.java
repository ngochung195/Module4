package com.codegym.repository;

import com.codegym.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, Long>, PagingAndSortingRepository<Feedback, Long> {
    Page<Feedback> findAll(Pageable pageable);

    Page<Feedback> findAllByDate(LocalDate date, Pageable pageable);

    List<Feedback> findAllByDate(LocalDate date);
}
