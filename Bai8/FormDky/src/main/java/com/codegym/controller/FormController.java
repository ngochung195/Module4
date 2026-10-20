package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    private final UserService userService;

    @Autowired
    public FormController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/user", "/register"})
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping({"/validateUser", "/user", "/register"})
    public String checkValidation(@Valid @ModelAttribute("user") User user,
                                  BindingResult bindingResult,
                                  Model model) {
        // Thực hiện kiểm tra bổ sung qua validator
        user.validate(user, bindingResult);

        // Kiểm tra xem có lỗi validation hay không trước khi lưu
        if (bindingResult.hasErrors()) {
            return "index";
        }

        // Dữ liệu hợp lệ -> Lưu vào CSDL (thông qua UserService)
        userService.save(user);

        // Chuyển sang màn hình kết quả thông báo thành công
        model.addAttribute("user", user);
        model.addAttribute("message", "Đăng ký thành công!");
        return "result";
    }
}
