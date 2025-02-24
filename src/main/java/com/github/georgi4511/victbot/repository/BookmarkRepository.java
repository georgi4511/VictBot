package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface BookmarkRepository extends ListCrudRepository<Bookmark, Long> {

    Optional<Bookmark> findByVictUser(VictUser victUser);

    Optional<Bookmark> findByVictGuild(VictGuild victGuild);
}
