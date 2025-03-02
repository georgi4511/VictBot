package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.BookmarkRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
  final BookmarkRepository bookmarkRepository;
  final VictGuildService victGuildService;
  final VictUserService victUserService;

  @Transactional(readOnly = true)
  public List<Bookmark> getAllBookmarks() {
    return bookmarkRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Bookmark> getBookmarksByVictGuildId(String victGuildId) {

    return bookmarkRepository.findByVictGuildId(victGuildId);
  }

  @Transactional
  public Bookmark saveBookmark(Bookmark bookmark) {
    return bookmarkRepository.save(bookmark);
  }

  public void addBookmark(String alias, String response, String victGuildId, String victUserId) {
    Instant now = Instant.now();
    Bookmark bookmark = new Bookmark(now, alias, response);

    victUserService.findById(victUserId).ifPresent(bookmark::setVictUser);
    if (victGuildId != null) {
      victGuildService.findById(victGuildId).ifPresent(bookmark::setVictGuild);
    }
    bookmarkRepository.save(bookmark);
  }

  public List<Bookmark> getBookmarksByGuildAndUser(String guildId, String userId) {
    if (guildId == null) {
      return bookmarkRepository.findByVictUserId(userId);
    }
    return bookmarkRepository.findByVictGuildIdAndVictUserId(guildId, userId);
  }

  public Optional<Bookmark> getBookmarkByAlias(String alias) {
    return bookmarkRepository.findByAlias(alias);
  }

  public boolean removeBookmarkByAliasAndVictUserId(String alias, String userDiscordId) {
    VictUser victUser = victUserService.findById(userDiscordId).orElseThrow(EntityNotFoundException::new);
    return bookmarkRepository.deleteByVictUserIdAndAlias(victUser.getId(), alias) > 0;
  }
}
