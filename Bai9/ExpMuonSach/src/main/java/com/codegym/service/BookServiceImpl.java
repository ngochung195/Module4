package com.codegym.service;

import com.codegym.exception.InvalidBorrowCodeException;
import com.codegym.exception.OutOfStockException;
import com.codegym.model.Book;
import com.codegym.model.BorrowRecord;
import com.codegym.repository.IBookRepository;
import com.codegym.repository.IBorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class BookServiceImpl implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IBorrowRecordRepository borrowRecordRepository;

    private final Random random = new Random();

    @PostConstruct
    public void init() {
        initSampleData();
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public String borrowBook(Long bookId) throws OutOfStockException {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Không tìm thấy cuốn sách với mã ID: " + bookId);
        }

        if (book.getQuantity() <= 0) {
            throw new OutOfStockException("Cuốn sách '" + book.getTitle() + "' đã hết (số lượng còn 0), không thể mượn tiếp!");
        }

        // Giảm số lượng sách còn lại xuống 1
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        // Sinh mã mượn sách ngẫu nhiên gồm 5 chữ số (10000 -> 99999)
        String borrowCode;
        do {
            int num = 10000 + random.nextInt(90000);
            borrowCode = String.format("%05d", num);
        } while (borrowRecordRepository.findByBorrowCodeAndReturnedFalse(borrowCode).isPresent());

        // Tạo bản ghi mượn sách
        BorrowRecord record = new BorrowRecord(borrowCode, book, LocalDateTime.now());
        borrowRecordRepository.save(record);

        return borrowCode;
    }

    @Override
    public Book returnBook(String borrowCode) throws InvalidBorrowCodeException {
        if (borrowCode == null || borrowCode.trim().isEmpty()) {
            throw new InvalidBorrowCodeException("Vui lòng nhập mã mượn sách hợp lệ gồm 5 chữ số!");
        }

        String cleanCode = borrowCode.trim();
        Optional<BorrowRecord> optionalRecord = borrowRecordRepository.findByBorrowCodeAndReturnedFalse(cleanCode);

        if (optionalRecord.isEmpty()) {
            throw new InvalidBorrowCodeException("Mã mượn sách '" + cleanCode + "' không tồn tại trong hệ thống hoặc sách đã được trả!");
        }

        BorrowRecord record = optionalRecord.get();
        Book book = record.getBook();

        // Tăng số lượng sách tương ứng lên 1
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // Đánh dấu đã trả sách
        record.setReturned(true);
        record.setReturnDate(LocalDateTime.now());
        borrowRecordRepository.save(record);

        return book;
    }

    @Override
    public List<BorrowRecord> findActiveBorrowRecords() {
        return borrowRecordRepository.findByReturnedFalseOrderByBorrowDateDesc();
    }

    @Override
    public List<BorrowRecord> findAllBorrowRecords() {
        return borrowRecordRepository.findAllByOrderByBorrowDateDesc();
    }

    @Override
    public void initSampleData() {
        try {
            if (bookRepository.count() == 0) {
                bookRepository.save(new Book("Dế Mèn Phiêu Lưu Ký", "Tô Hoài", 3,
                        "Tác phẩm văn học thiếu nhi kinh điển của nhà văn Tô Hoài kể về chuyến phiêu lưu mạo hiểm của Dế Mèn qua nhiều vùng đất mới lạ."));
                bookRepository.save(new Book("Tắt Đèn", "Ngô Tất Tố", 2,
                        "Một trong những tác phẩm tiêu biểu của dòng văn học hiện thực phê phán Việt Nam trước 1945, phản ánh nỗi cùng cực của người nông dân dưới chế độ phong kiến thực dân."));
                bookRepository.save(new Book("Lão Hạc", "Nam Cao", 1,
                        "Truyện ngắn xuất sắc khắc họa số phận bi thảm nhưng giàu lòng tự trọng của người nông dân nghèo trước cách mạng tháng Tám."));
                bookRepository.save(new Book("Số Đỏ", "Vũ Trọng Phụng", 0,
                        "Kiệt tác trào phúng đỉnh cao của văn học Việt Nam hiện đại, chế giễu xã hội tư sản nhố nhăng nửa mùa thời Pháp thuộc. (Sách này có số lượng 0 để kiểm tra trường hợp hết sách)"));
                bookRepository.save(new Book("Cho Tôi Xin Một Vé Đi Tuổi Thơ", "Nguyễn Nhật Ánh", 4,
                        "Tác phẩm đưa độc giả trở về những ký ức tuổi thơ trong trẻo, hồn nhiên đầy ắp tiếng cười và những triết lý nhân sinh nhẹ nhàng."));
            }
        } catch (Exception e) {
            System.err.println("Lưu ý: Không thể khởi tạo dữ liệu mẫu ngay lúc này: " + e.getMessage());
        }
    }
}
