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
  public Optional<Bookmark> getBookmarkByVictGuild(VictGuild victGuild) {

    return bookmarkRepository.findByVictGuild(victGuild);
  }

  @Transactional
  public Bookmark saveBookmark(Bookmark bookmark) {
    return bookmarkRepository.save(bookmark);
  }

  public void addBookmark(
      String alias, String response, String discordGuildId, String discordUserId) {
    Instant now = Instant.now();
    Bookmark bookmark = new Bookmark(now, alias, response);

    victUserService.getByVictUserDiscordId(discordUserId).ifPresent(bookmark::setVictUser);
    if (discordGuildId != null) {
      victGuildService.getByVictGuildDiscordId(discordGuildId).ifPresent(bookmark::setVictGuild);
    }
    bookmarkRepository.save(bookmark);
  }

  public List<Bookmark> getBookmarksByGuildAndUser(String guildId, String userId) {
    VictUser victUser =
        victUserService.getByVictUserDiscordId(userId).orElseThrow(EntityNotFoundException::new);
    if (guildId == null) {
      return bookmarkRepository.findByVictUserId(victUser.getId());
    }
    VictGuild victGuild =
        victGuildService.getByVictGuildDiscordId(guildId).orElseThrow(EntityNotFoundException::new);
    return bookmarkRepository.findByVictGuildIdAndVictUserId(victGuild.getId(), victUser.getId());
  }

  public Optional<Bookmark> getBookmarkByAlias(String alias) {

    return bookmarkRepository.findByAlias(alias);
  }

  public boolean removeBookmarkByAliasAndVictUserId(String alias, String userDiscordId) {
    VictUser victUser =
        victUserService
            .getByVictUserDiscordId(userDiscordId)
            .orElseThrow(EntityNotFoundException::new);
    return bookmarkRepository.deleteByVictUserIdAndAlias(victUser.getId(), alias) > 0;
  }
}
