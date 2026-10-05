package com.codegym.service;

import com.codegym.model.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SongService {
    private final List<Song> songs = new ArrayList<>();

    public SongService() {
        // Initial sample data
        songs.add(new Song("Em Cua Ngay Hom Qua", "Son Tung M-TP", Arrays.asList("Pop", "R&B"), "em-cua-ngay-hom-qua.mp3"));
        songs.add(new Song("Rolling in the Deep", "Adele", Arrays.asList("Soul", "Pop", "Blues"), "rolling-in-the-deep.mp3"));
    }

    public List<Song> findAll() {
        return Collections.unmodifiableList(songs);
    }

    public void add(Song song) {
        if (song != null) {
            songs.add(song);
        }
    }
}
