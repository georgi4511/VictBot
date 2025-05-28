package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.BookmarkRepository;
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

  private final BookmarkRepository bookmarkRepository;
  private final VictGuildService victGuildService;
  private final VictUserService victUserService;

  @Transactional(readOnly = true)
  public List<Bookmark> getAll() {
    return bookmarkRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Bookmark> getByVictGuildId(String victGuildId) {
    return bookmarkRepository.findByVictGuildId(victGuildId);
  }

  public Bookmark create(Bookmark bookmark) {
    return bookmarkRepository.save(bookmark);
  }

  public void create(String alias, String response, String victGuildId, String victUserId) {
    Instant now = Instant.now();

    VictUser victUser = victUserService.findById(victUserId).orElse(null);

    VictGuild victGuild =
        victGuildId == null ? null : victGuildService.findById(victGuildId).orElse(null);

    Bookmark bookmark = new Bookmark(now, alias, response, victUser, victGuild);

    bookmarkRepository.save(bookmark);
  }

  @Transactional(readOnly = true)
  public List<Bookmark> getByGuildAndUser(String guildId, String userId) {
    if (guildId == null) {
      return bookmarkRepository.findByVictUserId(userId);
    }
    return bookmarkRepository.findByVictGuildIdAndVictUserId(guildId, userId);
  }

  @Transactional(readOnly = true)
  public Optional<Bookmark> getByAlias(String alias) {
    return bookmarkRepository.findByAlias(alias);
  }

  public boolean removeByAliasAndVictUserId(String alias, String userId) {
    return bookmarkRepository.deleteByVictUserIdAndAlias(userId, alias) > 0;
  }
}
