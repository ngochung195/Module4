package com.codegym.exception;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super("Số lượng sách đã hết (bằng 0), không thể mượn thêm!");
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
