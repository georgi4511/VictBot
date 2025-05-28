package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.NonNull;

@Entity
@Table(name = "vict_user")
public record VictUser(
    @Id @NonNull String id,
    @JsonIgnore @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
        Set<Reminder> reminders,
    @JsonIgnore @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
        Set<Bookmark> bookmarks) {
  public VictUser(String id) {
    this(id, null, null);
  }
}
