package com.codegym;

import com.codegym.aspect.BookStateAspect;
import com.codegym.aspect.VisitorCountAspect;
import com.codegym.model.Book;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AspectLoggingTest {

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;

    @Test
    @DisplayName("VisitorCountAspect đếm tăng lượt ghé thăm khi có thao tác")
    void testVisitorCountIncrements() {
        int initialCount = VisitorCountAspect.getVisitorCount();

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("listBooks");

        VisitorCountAspect visitorAspect = new VisitorCountAspect();
        visitorAspect.logVisitorInteraction(joinPoint);

        assertEquals(initialCount + 1, VisitorCountAspect.getVisitorCount());
    }

    @Test
    @DisplayName("BookStateAspect ghi log mượn sách thành công mà không gây lỗi")
    void testBookStateAspectBorrowLogging() {
        BookStateAspect aspect = new BookStateAspect();
        when(joinPoint.getArgs()).thenReturn(new Object[]{1L});

        assertDoesNotThrow(() -> {
            aspect.logAfterBorrowSuccess(joinPoint, "12345");
        });
    }

    @Test
    @DisplayName("BookStateAspect ghi log trả sách thành công mà không gây lỗi")
    void testBookStateAspectReturnLogging() {
        BookStateAspect aspect = new BookStateAspect();
        Book book = new Book("Tắt Đèn", "Ngô Tất Tố", 3, "Mô tả");
        book.setId(2L);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"12345"});

        assertDoesNotThrow(() -> {
            aspect.logAfterReturnSuccess(joinPoint, book);
        });
    }

    @Test
    @DisplayName("BookStateAspect ghi log ngoại lệ khi xảy ra lỗi")
    void testBookStateAspectExceptionLogging() {
        BookStateAspect aspect = new BookStateAspect();
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("borrowBook");

        assertDoesNotThrow(() -> {
            aspect.logAfterStateChangeException(joinPoint, new Exception("Sách đã hết!"));
        });
    }
}
