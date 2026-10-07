package com.codegym.musicplayer.model;

import org.springframework.web.multipart.MultipartFile;

public class SongForm {
    private Long id;
    private String name;
    private String artist;
    private String genre;
    private MultipartFile audioFile;

    public SongForm() {
    }

    public SongForm(Long id, String name, String artist, String genre, MultipartFile audioFile) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.audioFile = audioFile;
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

    public MultipartFile getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(MultipartFile audioFile) {
        this.audioFile = audioFile;
    }
}
