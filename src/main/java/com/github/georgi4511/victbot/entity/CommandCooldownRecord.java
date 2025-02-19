package com.github.georgi4511.victbot.entity;

import java.time.Instant;

public record CommandCooldownRecord(Instant created, String name) {
}
