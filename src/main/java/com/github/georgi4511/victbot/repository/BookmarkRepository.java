package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.Bookmark;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.entity.VictUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByVictUser(VictUser victUser);

    Optional<Bookmark> findByVictGuild(VictGuild victGuild);

}
