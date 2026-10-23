package com.codegym.validsong.service;

import com.codegym.validsong.dto.SongDto;
import com.codegym.validsong.model.Song;

import java.util.List;

public interface ISongService {
    List<Song> findAll();

    Song findById(Long id);

    Song save(SongDto songDto);

    Song update(Long id, SongDto songDto);

    void deleteById(Long id);

    SongDto convertToDto(Song song);
}
