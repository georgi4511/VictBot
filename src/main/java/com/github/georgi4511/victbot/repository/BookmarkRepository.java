/* (C)2025 */
package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.Bookmark;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.entity.VictUser;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface BookmarkRepository extends ListCrudRepository<Bookmark, Long> {

  Optional<Bookmark> findByVictUser(VictUser victUser);

  Optional<Bookmark> findByVictGuild(VictGuild victGuild);
}
