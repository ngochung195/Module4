package com.codegym.model;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private String name;
    private String artist;
    private List<String> genre = new ArrayList<>();
    private String path;

    public Song() {
    }

    public Song(String name, String artist, List<String> genre, String path) {
        this.name = name;
        this.artist = artist;
        setGenre(genre);
        this.path = path;
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

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        if (genre == null) {
            this.genre = new ArrayList<>();
        } else {
            this.genre = new ArrayList<>();
            for (String g : genre) {
                if (g != null && !g.trim().isEmpty()) {
                    String[] parts = g.split(",");
                    for (String part : parts) {
                        String trimmed = part.trim();
                        if (!trimmed.isEmpty()) {
                            this.genre.add(trimmed);
                        }
                    }
                }
            }
        }
    }

    public String getGenreFormatted() {
        if (genre == null || genre.isEmpty()) {
            return "";
        }
        return String.join(", ", genre);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
