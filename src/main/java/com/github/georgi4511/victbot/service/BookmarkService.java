package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    final BookmarkRepository bookmarkRepository;

    @Transactional(readOnly = true)
    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Bookmark> getBookmarkByVictGuild(VictGuild victGuild) {
        return bookmarkRepository.findByVictGuild(victGuild);
    }

    @Transactional
    public Bookmark saveBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }
}
