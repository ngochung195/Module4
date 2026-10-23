package com.codegym.validsong.service;

import com.codegym.validsong.dto.SongDto;
import com.codegym.validsong.model.Song;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SongService implements ISongService {

    private final Map<Long, Song> songMap = new LinkedHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public SongService() {
        // Khởi tạo các bài hát mẫu
        createInitialSong("Nơi này có anh", "Sơn Tùng MTP", "Pop, R and B");
        createInitialSong("Bao tiền một mớ bình yên", "14 Casper ft Bon Nghiêm", "Indie, Ballad, Acoustic");
        createInitialSong("Chạy về khóc với anh", "Erik", "Pop, Ballad");
        createInitialSong("Đi về nhà", "Đen Vâu ft JustaTee", "Rap, Hip Hop, Pop");
        createInitialSong("See Tình", "Hoàng Thùy Linh", "Dance pop, Disco");
    }

    private void createInitialSong(String name, String artist, String genre) {
        long id = idGenerator.incrementAndGet();
        songMap.put(id, new Song(id, name, artist, genre));
    }

    @Override
    public List<Song> findAll() {
        return new ArrayList<>(songMap.values());
    }

    @Override
    public Song findById(Long id) {
        return songMap.get(id);
    }

    @Override
    public Song save(SongDto songDto) {
        long id = idGenerator.incrementAndGet();
        Song song = new Song(
                id,
                songDto.getName() != null ? songDto.getName().trim() : "",
                songDto.getArtist() != null ? songDto.getArtist().trim() : "",
                songDto.getGenre() != null ? songDto.getGenre().trim() : ""
        );
        songMap.put(id, song);
        return song;
    }

    @Override
    public Song update(Long id, SongDto songDto) {
        Song existing = songMap.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("Không tìm thấy bài hát có id: " + id);
        }
        existing.setName(songDto.getName() != null ? songDto.getName().trim() : "");
        existing.setArtist(songDto.getArtist() != null ? songDto.getArtist().trim() : "");
        existing.setGenre(songDto.getGenre() != null ? songDto.getGenre().trim() : "");
        return existing;
    }

    @Override
    public void deleteById(Long id) {
        songMap.remove(id);
    }

    @Override
    public SongDto convertToDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist(), song.getGenre());
    }
}
