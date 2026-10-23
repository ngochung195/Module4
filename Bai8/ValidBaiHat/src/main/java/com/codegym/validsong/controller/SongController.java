package com.codegym.validsong.controller;

import com.codegym.validsong.dto.SongDto;
import com.codegym.validsong.model.Song;
import com.codegym.validsong.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping({"", "/songs"})
public class SongController {

    private final ISongService songService;

    @Autowired
    public SongController(ISongService songService) {
        this.songService = songService;
    }

    @GetMapping({"", "/"})
    public String listSongs(Model model) {
        List<Song> songs = songService.findAll();
        model.addAttribute("songs", songs);
        return "song/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("songDto")) {
            model.addAttribute("songDto", new SongDto());
        }
        return "song/create";
    }

    @PostMapping("/create")
    public String createSong(@Valid @ModelAttribute("songDto") SongDto songDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Thông tin bài hát chưa hợp lệ. Vui lòng kiểm tra lại các trường!");
            return "song/create";
        }

        songService.save(songDto);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm mới bài hát \"" + songDto.getName() + "\" thành công!");
        return "redirect:/songs";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Song song = songService.findById(id);
        if (song == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy bài hát có mã: " + id);
            return "redirect:/songs";
        }

        if (!model.containsAttribute("songDto")) {
            model.addAttribute("songDto", songService.convertToDto(song));
        }
        model.addAttribute("songId", id);
        return "song/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateSong(@PathVariable Long id,
                             @Valid @ModelAttribute("songDto") SongDto songDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("songId", id);
            model.addAttribute("errorMessage", "Thông tin cập nhật chưa hợp lệ. Vui lòng kiểm tra lại các trường!");
            return "song/edit";
        }

        songService.update(id, songDto);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật bài hát \"" + songDto.getName() + "\" thành công!");
        return "redirect:/songs";
    }

    @PostMapping("/{id}/delete")
    public String deleteSong(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            songService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa bài hát thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa bài hát: " + e.getMessage());
        }
        return "redirect:/songs";
    }
}
