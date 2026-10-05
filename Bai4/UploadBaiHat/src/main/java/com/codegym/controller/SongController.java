package com.codegym.controller;

import com.codegym.model.Song;
import com.codegym.service.SongService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
public class SongController {

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(
            Arrays.asList(".mp3", ".wav", ".ogg", ".m4p")
    );

    private final SongService songService;

    @Autowired
    private ServletContext servletContext;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/songs";
    }

    @GetMapping("/songs")
    public String listSongs(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "list";
    }

    @GetMapping("/songs/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("song", new Song());
        return "upload";
    }

    @PostMapping("/songs/upload")
    public String handleSongUpload(@ModelAttribute("song") Song song,
                                   @RequestParam("file") MultipartFile file,
                                   Model model) {
        if (file == null || file.isEmpty()) {
            model.addAttribute("errorMessage", "Vui lòng chọn file bài hát để upload.");
            return "upload";
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isValidAudioExtension(originalFilename)) {
            model.addAttribute("errorMessage", "File không hợp lệ. Chỉ chấp nhận các định dạng: .mp3, .wav, .ogg, .m4p");
            return "upload";
        }

        try {
            // Determine storage directory within webapp uploads or system fallback
            String uploadRealPath = servletContext.getRealPath("/uploads/");
            if (uploadRealPath == null) {
                uploadRealPath = System.getProperty("user.dir") + File.separator + "uploads";
            }

            File uploadDir = new File(uploadRealPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generate unique filename to prevent overwrite collisions
            String sanitizedFilename = originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
            String uniqueFileName = UUID.randomUUID().toString() + "_" + sanitizedFilename;

            File destinationFile = new File(uploadDir, uniqueFileName);
            file.transferTo(destinationFile);

            // Store relative web path or unique file name
            song.setPath("uploads/" + uniqueFileName);
            songService.add(song);

            return "redirect:/songs";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi lưu file: " + e.getMessage());
            return "upload";
        }
    }

    private boolean isValidAudioExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0) {
            return false;
        }
        String extension = filename.substring(dotIndex).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }
}
