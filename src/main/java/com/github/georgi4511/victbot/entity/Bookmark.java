/* (C)2025 */
package com.github.georgi4511.victbot.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Bookmark {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private Instant createdTime;

  @NonNull private String message;

  @NonNull private String response;

  @ManyToOne
  @JoinColumn(name = "vict_user_id", nullable = false)
  private VictUser victUser;

  @ManyToOne
  @JoinColumn(name = "vict_guild_id", nullable = false)
  private VictGuild victGuild;
}
