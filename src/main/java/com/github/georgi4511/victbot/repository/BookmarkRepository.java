package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.Bookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface BookmarkRepository extends ListCrudRepository<Bookmark, Long> {

  List<Bookmark> findByVictGuildId(String victGuildId);

  List<Bookmark> findByVictGuildIdAndVictUserId(String guildId, String victUserId);

  List<Bookmark> findByVictUserId(String userId);

  Optional<Bookmark> findByAlias(String alias);

  Long deleteByVictUserIdAndAlias(String victUserId, String alias);
}
