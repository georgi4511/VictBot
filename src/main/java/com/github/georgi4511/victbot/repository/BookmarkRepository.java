package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface BookmarkRepository extends ListCrudRepository<Bookmark, Long> {

  Optional<Bookmark> findByVictUser(VictUser victUser);

  Optional<Bookmark> findByVictGuild(VictGuild victGuild);

  List<Bookmark> findByVictGuildIdAndVictUserId(Long guildId, Long userId);

  List<Bookmark> findByVictUserId(Long userId);
}
