package com.codegym.controller;

import com.codegym.aspect.VisitorCountAspect;
import com.codegym.exception.InvalidBorrowCodeException;
import com.codegym.exception.OutOfStockException;
import com.codegym.model.Book;
import com.codegym.model.BorrowRecord;
import com.codegym.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @ModelAttribute("visitorCount")
    public int getVisitorCount() {
        return VisitorCountAspect.getVisitorCount();
    }

    // Danh sách các đầu sách
    @GetMapping({"", "/"})
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        List<BorrowRecord> activeBorrows = bookService.findActiveBorrowRecords();
        model.addAttribute("books", books);
        model.addAttribute("activeBorrows", activeBorrows);
        return "books/list";
    }

    // Xem chi tiết đầu sách
    @GetMapping("/{id}")
    public String viewBookDetail(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        if (book == null) {
            model.addAttribute("errorMessage", "Không tìm thấy cuốn sách với mã số: " + id);
            return "error/general-error";
        }
        model.addAttribute("book", book);
        return "books/detail";
    }

    // Thực hiện mượn sách
    @PostMapping("/{id}/borrow")
    public String borrowBook(@PathVariable("id") Long id,
                             RedirectAttributes redirectAttributes,
                             Model model) throws OutOfStockException {
        Book book = bookService.findById(id);
        if (book == null) {
            model.addAttribute("errorMessage", "Không tìm thấy cuốn sách với mã số: " + id);
            return "error/general-error";
        }

        // Gọi service mượn sách (nếu hết sách sẽ ném OutOfStockException)
        String borrowCode = bookService.borrowBook(id);

        redirectAttributes.addFlashAttribute("book", book);
        redirectAttributes.addFlashAttribute("borrowCode", borrowCode);
        redirectAttributes.addFlashAttribute("successMessage", "Mượn sách thành công! Vui lòng lưu lại mã mượn để trả sách sau này.");

        return "redirect:/books/borrow-success";
    }

    // Màn hình thông báo mượn sách thành công
    @GetMapping("/borrow-success")
    public String borrowSuccess() {
        return "books/borrow-success";
    }

    // Màn hình trả sách
    @GetMapping("/return")
    public String showReturnForm(Model model) {
        List<BorrowRecord> activeBorrows = bookService.findActiveBorrowRecords();
        model.addAttribute("activeBorrows", activeBorrows);
        return "books/return";
    }

    // Xử lý trả sách
    @PostMapping("/return")
    public String processReturnBook(@RequestParam("borrowCode") String borrowCode,
                                    RedirectAttributes redirectAttributes) throws InvalidBorrowCodeException {
        // Gọi service trả sách (nếu mã sai hoặc đã trả sẽ ném InvalidBorrowCodeException)
        Book book = bookService.returnBook(borrowCode);

        redirectAttributes.addFlashAttribute("book", book);
        redirectAttributes.addFlashAttribute("borrowCode", borrowCode.trim());
        redirectAttributes.addFlashAttribute("successMessage", "Trả sách thành công! Cuốn sách đã được hoàn trả về kho thư viện.");

        return "redirect:/books/return-success";
    }

    // Màn hình thông báo trả sách thành công
    @GetMapping("/return-success")
    public String returnSuccess() {
        return "books/return-success";
    }

    // ==================== XỬ LÝ NGOẠI LỆ (EXCEPTION HANDLING) ====================

    // Xử lý khi sách hết hàng (số lượng = 0)
    @ExceptionHandler(OutOfStockException.class)
    public ModelAndView handleOutOfStockException(OutOfStockException ex) {
        ModelAndView modelAndView = new ModelAndView("error/out-of-stock");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    // Xử lý khi mã mượn sách không đúng hoặc đã được trả
    @ExceptionHandler(InvalidBorrowCodeException.class)
    public ModelAndView handleInvalidBorrowCodeException(InvalidBorrowCodeException ex) {
        ModelAndView modelAndView = new ModelAndView("error/invalid-code");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    // Xử lý các lỗi ngoại lệ khác
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error/general-error");
        modelAndView.addObject("errorMessage", ex.getMessage() != null ? ex.getMessage() : "Đã xảy ra lỗi không xác định!");
        return modelAndView;
    }
}
