package com.codegym.validsong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SongDto {

    private Long id;

    @NotBlank(message = "Tên bài hát không được để trống")
    @Size(max = 800, message = "Tên bài hát không được vượt quá 800 ký tự")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s]+$", message = "Tên bài hát không được chứa các ký tự đặc biệt (@, ;, ,, ., =, -, +, ...)")
    private String name;

    @NotBlank(message = "Nghệ sĩ thể hiện không được để trống")
    @Size(max = 300, message = "Nghệ sĩ thể hiện không được vượt quá 300 ký tự")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s]+$", message = "Nghệ sĩ thể hiện không được chứa các ký tự đặc biệt (@, ;, ,, ., =, -, +, ...)")
    private String artist;

    @NotBlank(message = "Thể loại nhạc không được để trống")
    @Size(max = 1000, message = "Thể loại nhạc không được vượt quá 1000 ký tự")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s,]+$", message = "Thể loại nhạc không được chứa các ký tự đặc biệt ngoại trừ dấu phẩy (\",\")")
    private String genre;

    public SongDto() {
    }

    public SongDto(Long id, String name, String artist, String genre) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
