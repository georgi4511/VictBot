package com.github.georgi4511.victbot.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

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

  @ManyToOne
  @JoinColumn(name = "vict_user_id", nullable = false)
  private VictUser victUser;

  @ManyToOne
  @JoinColumn(name = "vict_guild_id", nullable = false)
  private VictGuild victGuild;

  @NonNull private String channelSentFrom;

  @NonNull private Boolean personal = false;
}
