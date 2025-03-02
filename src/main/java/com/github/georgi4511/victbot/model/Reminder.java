package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import lombok.Builder.Default;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Reminder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private Instant createdTime;

  @NonNull private String message;

  @NonNull private Instant targetTime;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vict_user_id", nullable = false)
  private VictUser victUser;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vict_guild_id", nullable = false)
  private VictGuild victGuild;

  @NonNull private String channelSentFrom;

  @NonNull @Default private Boolean personal = false;
}
