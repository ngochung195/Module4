package com.codegym.musicplayer.service;

import com.codegym.musicplayer.model.Song;

import java.util.List;

public interface ISongService {
    List<Song> findAll();

    Song findById(Long id);

    void save(Song song);

    void update(Long id, Song song);

    void remove(Long id);
}
