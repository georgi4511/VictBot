package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "vict_guild")
public class VictGuild {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Long id;

  @Column(unique = true)
  @NonNull
  String discordId;

  @JsonIgnore
  @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
  Set<Reminder> reminders;

  @JsonIgnore
  @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
  Set<Bookmark> bookmarks;

  @JsonIgnore
  @OneToOne(mappedBy = "victGuild", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  Impressions impressions;
}
