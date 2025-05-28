package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.NonNull;

@Entity
public record Reminder(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
    @NonNull Instant createdTime,
    @NonNull String message,
    @NonNull Instant targetTime,
    @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vict_user_id", nullable = false)
        VictUser victUser,
    @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vict_guild_id", nullable = false)
        VictGuild victGuild,
    @NonNull String channelSentFrom,
    @NonNull Boolean personal) {}
