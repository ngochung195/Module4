package com.codegym.repository;

import com.codegym.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByBorrowCodeAndReturnedFalse(String borrowCode);

    Optional<BorrowRecord> findByBorrowCode(String borrowCode);

    List<BorrowRecord> findAllByOrderByBorrowDateDesc();

    List<BorrowRecord> findByReturnedFalseOrderByBorrowDateDesc();
}
