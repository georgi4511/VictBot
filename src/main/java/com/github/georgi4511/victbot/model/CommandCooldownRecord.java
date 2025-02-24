package com.github.georgi4511.victbot.model;

import java.time.Instant;

public record CommandCooldownRecord(Instant created, String name) {
}
