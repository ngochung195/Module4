package com.codegym.aspect;

import com.codegym.model.Book;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Aspect
@Component
public class BookStateAspect {

    private static final Logger logger = Logger.getLogger(BookStateAspect.class.getName());
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Pointcut cho các phương thức làm thay đổi trạng thái sách
    @Pointcut("execution(* com.codegym.service.IBookService.borrowBook(..))")
    public void borrowBookPointcut() {}

    @Pointcut("execution(* com.codegym.service.IBookService.returnBook(..))")
    public void returnBookPointcut() {}

    // Ghi log sau khi mượn sách thành công (trạng thái kho sách giảm 1)
    @AfterReturning(pointcut = "borrowBookPointcut()", returning = "borrowCode")
    public void logAfterBorrowSuccess(JoinPoint joinPoint, String borrowCode) {
        Object[] args = joinPoint.getArgs();
        Long bookId = (Long) args[0];
        String time = LocalDateTime.now().format(dtf);
        String logMessage = String.format(
                "===> [LOG TRẠNG THÁI SÁCH] MƯỢN SÁCH THÀNH CÔNG: Book ID: %d | Cấp mã mượn: %s | Thời gian: %s | Kết quả: Số lượng cuốn sách này đã giảm đi 1.",
                bookId, borrowCode, time
        );
        System.out.println(logMessage);
        logger.info(logMessage);
    }

    // Ghi log sau khi trả sách thành công (trạng thái kho sách tăng 1)
    @AfterReturning(pointcut = "returnBookPointcut()", returning = "book")
    public void logAfterReturnSuccess(JoinPoint joinPoint, Book book) {
        Object[] args = joinPoint.getArgs();
        String borrowCode = (String) args[0];
        String time = LocalDateTime.now().format(dtf);
        String logMessage = String.format(
                "===> [LOG TRẠNG THÁI SÁCH] TRẢ SÁCH THÀNH CÔNG: Mã mượn: %s | Tên sách: '%s' (ID: %d) | Số lượng hiện có: %d | Thời gian: %s | Kết quả: Số lượng cuốn sách này đã tăng lên 1.",
                borrowCode, book.getTitle(), book.getId(), book.getQuantity(), time
        );
        System.out.println(logMessage);
        logger.info(logMessage);
    }

    // Ghi log khi hành động làm thay đổi trạng thái thất bại do ngoại lệ (hết sách, sai mã mượn,...)
    @AfterThrowing(pointcut = "borrowBookPointcut() || returnBookPointcut()", throwing = "ex")
    public void logAfterStateChangeException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String time = LocalDateTime.now().format(dtf);
        String logMessage = String.format(
                "===> [LOG NGOẠI LỆ THƯ VIỆN] THAO TÁC THẤT BẠI: Thao tác '%s' gặp ngoại lệ: '%s' | Thời gian: %s",
                methodName, ex.getMessage(), time
        );
        System.err.println(logMessage);
        logger.warning(logMessage);
    }
}
