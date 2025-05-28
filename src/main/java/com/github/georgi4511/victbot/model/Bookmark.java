package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;

@Entity
@Builder
public record Bookmark(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
    @NonNull Instant createdTime,
    @NonNull String alias,
    @NonNull String response,
    @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vict_user_id", nullable = false)
        VictUser victUser,
    @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vict_guild_id", nullable = false)
        VictGuild victGuild) {
  public Bookmark(Instant createdTime, String alias, String response) {
    this(null, createdTime, alias, response, null, null);
  }

  public Bookmark(
      @NonNull Instant createdTime,
      @NonNull String alias,
      @NonNull String response,
      VictUser victUser,
      VictGuild victGuild) {
    this(null, createdTime, alias, response, victUser, victGuild);
  }
}
