package com.codegym.musicplayer.controller;

import com.codegym.musicplayer.configuration.AppConfiguration;
import com.codegym.musicplayer.model.Song;
import com.codegym.musicplayer.model.SongForm;
import com.codegym.musicplayer.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    private final ISongService songService;
    private final String uploadPath = AppConfiguration.UPLOAD_PATH;

    @Autowired
    public SongController(ISongService songService) {
        this.songService = songService;
    }

    /**
     * Hiển thị danh sách tất cả các bài hát
     */
    @GetMapping("")
    public String index(Model model) {
        List<Song> songs = songService.findAll();
        model.addAttribute("songs", songs);
        return "song/index";
    }

    /**
     * Hiển thị form thêm mới bài hát
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("songForm", new SongForm());
        return "song/create";
    }

    /**
     * Xử lý lưu bài hát mới và upload file âm thanh
     */
    @PostMapping(value = {"/create", "/save"})
    public String saveSong(@ModelAttribute("songForm") SongForm songForm,
                           RedirectAttributes redirectAttributes) {
        MultipartFile multipartFile = songForm.getAudioFile();
        String fileName = (multipartFile != null) ? multipartFile.getOriginalFilename() : null;

        if (fileName != null && !fileName.trim().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Đặt tên file duy nhất để tránh trùng lặp
            String savedFileName = System.currentTimeMillis() + "_" + fileName.replaceAll("\\s+", "_");
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadDir, savedFileName));
                Song song = new Song(
                        songForm.getName(),
                        songForm.getArtist(),
                        songForm.getGenre(),
                        savedFileName
                );
                songService.save(song);
                redirectAttributes.addFlashAttribute("message", "Thêm mới bài hát thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu tệp âm thanh!");
            }
        } else {
            Song song = new Song(
                    songForm.getName(),
                    songForm.getArtist(),
                    songForm.getGenre(),
                    ""
            );
            songService.save(song);
            redirectAttributes.addFlashAttribute("message", "Thêm mới bài hát thành công (chưa đính kèm file nhạc)!");
        }

        return "redirect:/songs";
    }

    /**
     * Hiển thị form chỉnh sửa bài hát
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Song song = songService.findById(id);
        if (song == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài hát yêu cầu!");
            return "redirect:/songs";
        }
        SongForm songForm = new SongForm(
                song.getId(),
                song.getName(),
                song.getArtist(),
                song.getGenre(),
                null
        );
        model.addAttribute("songForm", songForm);
        model.addAttribute("currentFile", song.getFilePath());
        return "song/edit";
    }

    /**
     * Xử lý cập nhật bài hát (thay thế file hoặc giữ nguyên file cũ)
     */
    @PostMapping(value = {"/{id}/edit", "/update"})
    public String updateSong(@PathVariable(value = "id", required = false) Long pathId,
                             @ModelAttribute("songForm") SongForm songForm,
                             @RequestParam(value = "currentFile", required = false) String currentFile,
                             RedirectAttributes redirectAttributes) {
        Long songId = (pathId != null) ? pathId : songForm.getId();
        MultipartFile multipartFile = songForm.getAudioFile();
        String fileName = (multipartFile != null) ? multipartFile.getOriginalFilename() : null;
        String finalFileName = currentFile;

        if (fileName != null && !fileName.trim().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Xóa file cũ trên ổ cứng nếu có
            if (currentFile != null && !currentFile.trim().isEmpty()) {
                File oldFile = new File(uploadDir, currentFile);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            // Lưu file mới
            finalFileName = System.currentTimeMillis() + "_" + fileName.replaceAll("\\s+", "_");
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadDir, finalFileName));
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật tệp âm thanh!");
            }
        }

        Song song = new Song(
                songId,
                songForm.getName(),
                songForm.getArtist(),
                songForm.getGenre(),
                finalFileName
        );
        songService.update(songId, song);
        redirectAttributes.addFlashAttribute("message", "Cập nhật bài hát thành công!");
        return "redirect:/songs";
    }

    /**
     * Hiển thị trang xác nhận xóa bài hát
     */
    @GetMapping("/{id}/delete")
    public String showDeleteForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Song song = songService.findById(id);
        if (song == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài hát để xóa!");
            return "redirect:/songs";
        }
        model.addAttribute("song", song);
        return "song/delete";
    }

    /**
     * Xóa bài hát khỏi database kèm xóa file vật lý trên ổ cứng
     */
    @PostMapping(value = {"/{id}/delete", "/delete"})
    public String deleteSong(@PathVariable(value = "id", required = false) Long pathId,
                             @RequestParam(value = "id", required = false) Long paramId,
                             RedirectAttributes redirectAttributes) {
        Long id = (pathId != null) ? pathId : paramId;
        Song song = songService.findById(id);

        if (song != null) {
            // Xóa file vật lý tương ứng trên ổ cứng
            if (song.getFilePath() != null && !song.getFilePath().trim().isEmpty()) {
                File audioFile = new File(uploadPath, song.getFilePath());
                if (audioFile.exists()) {
                    audioFile.delete();
                }
            }
            // Xóa record trong DB
            songService.remove(id);
            redirectAttributes.addFlashAttribute("message", "Đã xóa bài hát và tệp âm thanh thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Bài hát không tồn tại hoặc đã bị xóa!");
        }

        return "redirect:/songs";
    }
}
