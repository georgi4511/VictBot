/* (C)2025 */
package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.Bookmark;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.repository.BookmarkRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    public Optional<Bookmark> getBookmarkByVictGuild(VictGuild victGuild) {
        return bookmarkRepository.findByVictGuild(victGuild);
    }

    public Bookmark saveBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }
}
