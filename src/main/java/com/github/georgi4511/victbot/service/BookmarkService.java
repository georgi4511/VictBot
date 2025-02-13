package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.Bookmark;
import com.github.georgi4511.victbot.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    public Optional<Bookmark> getBookmarkByGuildId(String guildId) {
        return bookmarkRepository.findByGuildId(guildId);
    }

    public Bookmark saveBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }
}
