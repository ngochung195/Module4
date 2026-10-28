package com.codegym;

import com.codegym.exception.InvalidBorrowCodeException;
import com.codegym.exception.OutOfStockException;
import com.codegym.model.Book;
import com.codegym.model.BorrowRecord;
import com.codegym.repository.IBookRepository;
import com.codegym.repository.IBorrowRecordRepository;
import com.codegym.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private IBookRepository bookRepository;

    @Mock
    private IBorrowRecordRepository borrowRecordRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book inStockBook;
    private Book outOfStockBook;

    @BeforeEach
    void setUp() {
        inStockBook = new Book("Dế Mèn Phiêu Lưu Ký", "Tô Hoài", 3, "Tác phẩm thiếu nhi");
        inStockBook.setId(1L);

        outOfStockBook = new Book("Số Đỏ", "Vũ Trọng Phụng", 0, "Tiểu thuyết trào phúng");
        outOfStockBook.setId(2L);
    }

    @Test
    @DisplayName("Mượn sách thành công: số lượng giảm 1 và trả về mã mượn gồm 5 chữ số")
    void testBorrowBookSuccess() throws OutOfStockException {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(inStockBook));
        when(borrowRecordRepository.findByBorrowCodeAndReturnedFalse(anyString())).thenReturn(Optional.empty());

        String borrowCode = bookService.borrowBook(1L);

        assertNotNull(borrowCode);
        assertEquals(5, borrowCode.length());
        assertTrue(borrowCode.matches("\\d{5}"));
        assertEquals(2, inStockBook.getQuantity());

        verify(bookRepository, times(1)).save(inStockBook);
        verify(borrowRecordRepository, times(1)).save(any(BorrowRecord.class));
    }

    @Test
    @DisplayName("Mượn sách khi số lượng bằng 0 sẽ ném OutOfStockException")
    void testBorrowBookOutOfStockThrowsException() {
        when(bookRepository.findById(2L)).thenReturn(Optional.of(outOfStockBook));

        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> {
            bookService.borrowBook(2L);
        });

        assertTrue(exception.getMessage().contains("đã hết"));
        assertEquals(0, outOfStockBook.getQuantity());
        verify(bookRepository, never()).save(outOfStockBook);
        verify(borrowRecordRepository, never()).save(any(BorrowRecord.class));
    }

    @Test
    @DisplayName("Trả sách thành công: số lượng tăng 1 và đánh dấu đã trả")
    void testReturnBookSuccess() throws InvalidBorrowCodeException {
        String code = "12345";
        BorrowRecord record = new BorrowRecord(code, inStockBook, LocalDateTime.now().minusDays(1));
        when(borrowRecordRepository.findByBorrowCodeAndReturnedFalse(code)).thenReturn(Optional.of(record));

        int oldQuantity = inStockBook.getQuantity();
        Book returnedBook = bookService.returnBook(code);

        assertEquals(oldQuantity + 1, returnedBook.getQuantity());
        assertTrue(record.isReturned());
        assertNotNull(record.getReturnDate());

        verify(bookRepository, times(1)).save(inStockBook);
        verify(borrowRecordRepository, times(1)).save(record);
    }

    @Test
    @DisplayName("Trả sách với mã không tồn tại sẽ ném InvalidBorrowCodeException")
    void testReturnBookInvalidCodeThrowsException() {
        String invalidCode = "99999";
        when(borrowRecordRepository.findByBorrowCodeAndReturnedFalse(invalidCode)).thenReturn(Optional.empty());

        InvalidBorrowCodeException exception = assertThrows(InvalidBorrowCodeException.class, () -> {
            bookService.returnBook(invalidCode);
        });

        assertTrue(exception.getMessage().contains("không tồn tại"));
        verify(bookRepository, never()).save(any(Book.class));
    }
}
