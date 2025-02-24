package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "vict_user")
public class VictUser {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Long id;

  @Column(unique = true)
  @NonNull
  String discordId;

  @JsonIgnore
  @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
  Set<Reminder> reminders;

  @JsonIgnore
  @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
  Set<Bookmark> bookmarks;
}
