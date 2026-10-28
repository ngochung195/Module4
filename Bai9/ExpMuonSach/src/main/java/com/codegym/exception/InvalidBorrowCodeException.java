package com.codegym.exception;

public class InvalidBorrowCodeException extends Exception {
    public InvalidBorrowCodeException() {
        super("Mã mượn sách không đúng hoặc sách này đã được trả!");
    }

    public InvalidBorrowCodeException(String message) {
        super(message);
    }
}
