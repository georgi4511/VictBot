package com.github.georgi4511.victbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String userId;

    @NonNull
    private Instant createdTime;

    @NonNull
    private String message;

    @NonNull
    private Instant targetTime;

    @NonNull
    private String guildId;

    @NonNull
    private String channelSentFrom;

    @NonNull
    private Boolean personal = false;
}
