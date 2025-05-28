package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vict_guild")
public class VictGuild {

  @Id String id;

  @JsonIgnore
  @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
  Set<Reminder> reminders;

  @JsonIgnore
  @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
  Set<Bookmark> bookmarks;

  @Default @NotNull private Long badBotImpressions = 0L;
  @Default @NotNull private Long goodBotImpressions = 0L;

  public VictGuild(String id) {
    this.id = id;
    this.badBotImpressions = 0L;
    this.goodBotImpressions = 0L;
    this.bookmarks = null;
    this.reminders = null;
  }

  public VictGuild incrementImpressions(Long good, Long bad) {
    if (good == null) {
      good = 0L;
    }
    if (bad == null) {
      bad = 0L;
    }
    this.badBotImpressions += bad;
    this.goodBotImpressions += good;
    return this;
  }

  public VictGuild incrementGoodImpressions(Long good) {
    return incrementImpressions(good, null);
  }

  public VictGuild incrementBadImpressions(Long bad) {
    return incrementImpressions(null, bad);
  }
}
