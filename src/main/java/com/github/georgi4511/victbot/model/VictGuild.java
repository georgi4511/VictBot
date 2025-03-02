package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.*;
import lombok.Builder.Default;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vict_guild")
public class VictGuild {

  public VictGuild(String id) {
    this.id = id;
    this.badBotImpressions = 0L;
    this.goodBotImpressions = 0L;
    this.bookmarks = null;
    this.reminders = null;
  }

  @Id
  String id;

  @JsonIgnore
  @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
  Set<Reminder> reminders;

  @JsonIgnore
  @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
  Set<Bookmark> bookmarks;

  @Default
  @NotNull
  private Long badBotImpressions = 0L;

  @Default
  @NotNull
  private Long goodBotImpressions = 0L;
}
