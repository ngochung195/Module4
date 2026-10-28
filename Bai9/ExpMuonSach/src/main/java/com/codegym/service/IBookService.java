package com.codegym.service;

import com.codegym.exception.InvalidBorrowCodeException;
import com.codegym.exception.OutOfStockException;
import com.codegym.model.Book;
import com.codegym.model.BorrowRecord;

import java.util.List;

public interface IBookService {
    List<Book> findAll();

    Book findById(Long id);

    void save(Book book);

    String borrowBook(Long bookId) throws OutOfStockException;

    Book returnBook(String borrowCode) throws InvalidBorrowCodeException;

    List<BorrowRecord> findActiveBorrowRecords();

    List<BorrowRecord> findAllBorrowRecords();

    void initSampleData();
}
