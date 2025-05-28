package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "vict_user")
public class VictUser {

  @Id @NonNull String id;

  @JsonIgnore
  @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
  Set<Reminder> reminders;

  @JsonIgnore
  @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
  Set<Bookmark> bookmarks;
}
