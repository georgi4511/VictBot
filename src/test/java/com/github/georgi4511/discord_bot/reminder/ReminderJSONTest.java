package com.github.georgi4511.discord_bot.reminder;

import com.github.georgi4511.discord_bot.jsonService.ReminderJSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

class ReminderJSONTest {

    @Test
            void testReminder() throws IOException {
            Path basedir = Path.of(".");
            if (!basedir.toFile().isDirectory()) throw new IOException();
            boolean directoryExists = Arrays.stream(Objects.requireNonNull(basedir.toFile().listFiles())).anyMatch(f->f.isDirectory()&& f.getName().equals("jsons"));

            assert directoryExists;
    }
}