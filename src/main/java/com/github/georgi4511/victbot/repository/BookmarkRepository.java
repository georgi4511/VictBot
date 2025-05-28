package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.Bookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface BookmarkRepository extends ListCrudRepository<Bookmark, Long> {

  List<Bookmark> findByVictGuildId(String victGuildId);

  List<Bookmark> findByVictGuildIdAndVictUserId(String victGuildId, String victUserId);

  List<Bookmark> findByVictUserId(String userId);

  Long deleteByVictUserIdAndAlias(String victUserId, String alias);

  Optional<Bookmark> findByAliasAndVictGuildId(String alias, String victGuildId);

  Optional<Bookmark> findByAliasAndVictUserId(String alias, String victUserId);
}
