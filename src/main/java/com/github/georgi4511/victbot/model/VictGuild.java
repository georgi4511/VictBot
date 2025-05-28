package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "vict_guild")
public record VictGuild(
    @Id String id,
    @JsonIgnore @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
        Set<Reminder> reminders,
    @JsonIgnore @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
        Set<Bookmark> bookmarks,
    @NotNull Long badBotImpressions,
    @NotNull Long goodBotImpressions) {

  public VictGuild(String id) {
    this(id, null, null, 0L, 0L);
  }

  public VictGuild incrementImpressions(Integer good, Integer bad) {
    if (good == null) {
      good = 0;
    }
    if (bad == null) {
      bad = 0;
    }
    return new VictGuild(
        id, reminders, bookmarks, badBotImpressions + bad, goodBotImpressions + good);
  }

  public VictGuild incrementGoodImpressions(Integer good) {
    return incrementImpressions(good, null);
  }

  public VictGuild incrementBadImpressions(Integer bad) {
    return incrementImpressions(null, bad);
  }
}
