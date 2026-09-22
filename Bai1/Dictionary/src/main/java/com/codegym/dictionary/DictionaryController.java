package com.codegym.dictionary;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DictionaryController {

    private static final Map<String, String> dictionary = new HashMap<>();

    static {
        dictionary.put("hello", "Xin chào");
        dictionary.put("banana", "Quả chuối");
        dictionary.put("computer", "Máy tính");
        dictionary.put("book", "Quyển sách");
        dictionary.put("apple", "Quả táo");
        dictionary.put("orange", "Quả cam");
        dictionary.put("dog", "Con chó");
        dictionary.put("cat", "Con mèo");
        dictionary.put("school", "Trường học");
        dictionary.put("student", "Học sinh");
        dictionary.put("teacher", "Giáo viên");
        dictionary.put("spring", "Mùa xuân / Khung làm việc Spring");
        dictionary.put("language", "Ngôn ngữ");
    }

    @GetMapping({"/", "/dictionary"})
    public String showIndex() {
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        String trimmedKeyword = (keyword != null) ? keyword.trim() : "";
        String meaning = null;

        if (!trimmedKeyword.isEmpty()) {
            meaning = dictionary.get(trimmedKeyword.toLowerCase());
        }

        model.addAttribute("keyword", trimmedKeyword);

        if (meaning != null) {
            model.addAttribute("found", true);
            model.addAttribute("meaning", meaning);
        } else {
            model.addAttribute("found", false);
            model.addAttribute("message", "Không tìm thấy từ này trong từ điển");
        }

        return "result";
    }
}
