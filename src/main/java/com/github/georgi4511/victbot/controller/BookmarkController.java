package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.entity.Bookmark;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public List<Bookmark> getAllBookmarks() {
        return bookmarkService.getAllBookmarks();
    }


    @PostMapping
    public Bookmark saveBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkService.saveBookmark(bookmark);
    }


    @GetMapping("/guild/{victGuild}")
    public Optional<Bookmark> getBookmarkByGuildId(@PathVariable VictGuild victGuild) {
        return bookmarkService.getBookmarkByVictGuild(victGuild);
    }

}
