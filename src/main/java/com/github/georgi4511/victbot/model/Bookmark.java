package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Instant createdTime;

    @NonNull
    private String message;

    @NonNull
    private String response;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vict_user_id", nullable = false)
    private VictUser victUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vict_guild_id", nullable = false)
    private VictGuild victGuild;
}
